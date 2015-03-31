// load the location
window.CustomerLocation = {
	loaded: false;
	latitude: 0.0;
	longitude: 0.0;
};
$(document).ready(function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			CustomerLocation.latitude = position.coords.latitude;
			CustomerLocation.longitude = position.coords.longitude;
		});
	}
});

// remove the suffix for parameters with multiple values
jQuery.ajaxSettings.traditional = true;

window.showroom = window.showroom || {};

showroom.Template = new EJS({url: document.location.href.replace(/\/showroom\/.*/, "/skin/showroom/ejs_tpl/showroom.html")});

showroom.Showrooms = {
	store: {},
	initialize: function() {
		var references = [];
		for (var reference in showroom.Showrooms.store) {
			if (reference != "initialize") {
				references.push(reference);
			}
		}
		var url = document.location.href.replace(/\/showroom\/.*/, "/showroom/entries/forProducts/");
		$.getJSON(url, {references: references}, function(picturesByProduct) {
			for (var reference in picturesByProduct) {
				var pictures = picturesByProduct[reference];
				showroom.Showrooms.store[reference].setPictures(pictures);
			}
		});
	}
};

showroom.Showroom = function(target, reference, pictures) {
	
	var self = this;

	self.target = target;
	
	self.reference = reference;
	
	if (pictures) {
		self.pictures = pictures;
	} else {
		self.pictures = {entries:[]};
	}
	
	self.refreshView = function() {
		$('.showroom', self.target).remove();
		self.target.append(showroom.Template.render($.extend({product:self.reference}, self.pictures)));
	};

	self.setPictures = function(pictures) {
		if (pictures) {
			self.pictures = pictures;
		} else {
			self.pictures = {entries:[]};
		}
		self.refreshView();
	};

	self.refreshPictures = function() {
		var url = document.location.href.replace(/\/showroom\/.*/, "/showroom/entries/forProduct/") + self.reference;
		$.getJSON(url, function(pictures) {
			self.pictures = pictures;
			self.refreshView();
		});
	};

	self.postPicture = function(file) {
		var filename = file.name;
		var batchId = "batch_" + self.reference + '_' + Math.random();
		var formData = new FormData();
		formData.append("file", file);
		var url = document.location.href.replace(/\/showroom\/.*/, "/api/v1/automation/batch/upload");
		$.ajax({
	        url: url, type: 'POST',
	        headers: {
	        	"X-Batch-Id": batchId,
	        	"X-File-Idx": "0",
	        	"X-File-Name": filename
	        },
	        data: formData,
	        cache: false, contentType: false, processData: false,
	        success: function(data) {
	        	var url = document.location.href.replace(/\/showroom\/.*/, "/api/v1/path/default-domain/workspaces/showrooms/");
				$.ajax({
					url: url, type: 'POST',
					contentType: "application/json", dataType: 'json',
			        headers: { "X-NXDocumentJsonLegacy": true }, // due to 7.2 bug about batch upload, fixed for 7.3
					data: JSON.stringify({  
					  "entity-type":"document",
					  "name":self.reference.toString(),
					  "type":"ShowroomEntry",
					  "properties": {
					    "dc:title":"Feedback for product #" + self.reference,
					    "dc:nature":"article",
					    "dc:subjects":"[\"art/photography\"]",
					    "loc:latitude":CustomerLocation.latitude,
					    "loc:longitude":CustomerLocation.longitude,
					    "pdt:product":self.reference,
					    "file:content":{
					    	"upload-batch":batchId,
							"upload-fileId":"0"
					    }
					  }
					}),
					success: function (data) {
						self.refreshPictures();
					}
				});
	        }
	    });
	}

	self.refreshView();

	showroom.Showrooms.store[self.reference] = self;
	
	return self;
};