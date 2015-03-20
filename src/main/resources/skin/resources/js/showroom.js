var TPL;

function loadShowrooms(selector) {
	TPL  = new EJS({url: ROOT + '/ejs_tpl/showroom.html'});
	$(selector).each(function() {
		var product = $(this);
		var ref = product.attr('id');
		loadPicturesShared(product, ref);
	});
}

function loadPicturesShared(area, ref) {
    var url = document.location.href.replace(/\/showroom\/.*/, "/showroom/entries/forProduct/") + ref;
	$.getJSON(url, function(data) {
		$('.showroom', area).remove();
		area.append(TPL.render($.extend({product:ref}, data)));
	});
}

function sendImageFor(product, file) {
	var filename = file.name;
	var batchId = "batch_" + product + '_' + Math.random();
	var formData = new FormData();
	formData.append("file", file);
	navigator.geolocation.getCurrentPosition(function(position) {
		var url = document.location.href.replace(/\/showroom\/.*/, "/automation/batch/upload");
		$.ajax({
	        url: url,
	        type: 'POST',
	        headers: {
	        	"X-Batch-Id": batchId,
	        	"X-File-Idx": "0",
	        	"X-File-Name": filename
	        },
	        data: formData,
	        cache: false,
	        contentType: false,
	        processData: false,
	        success: function(data) {
	        	var url = document.location.href.replace(/\/showroom\/.*/, "/api/v1/path/default-domain/workspaces/showrooms/");
				$.ajax({
					url: url,
					type: 'POST',
					contentType: "application/json",
					dataType: 'json',
			        headers: {
			        	"X-NXDocumentJsonLegacy": true
			        },
					data: JSON.stringify({  
					  "entity-type":"document",
					  "name":product.toString(),
					  "type":"ShowroomEntry",
					  "properties": {
					    "dc:title":"Feedback for product #" + product,
					    "dc:nature":"article",
					    "dc:subjects":"[\"art/photography\"]",
					    "loc:latitude":position.coords.latitude,
					    "loc:longitude":position.coords.longitude,
					    "pdt:product":product,
					    "file:content":{
					    	"upload-batch":batchId,
							"upload-fileId":"0"
					    }
					  }
					}),
					success: function (data) {
						loadPicturesShared($('.product#' + product), product);
					}
				});
	        }
	    });
	});
}