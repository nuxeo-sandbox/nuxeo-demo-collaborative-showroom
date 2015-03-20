<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="${skinPath}/css/style.css">
<script type="text/javascript" src="${skinPath}/js/jquery.js" ></script>
<script type="text/javascript" src="${skinPath}/js/ejs.js" ></script>
<script type="text/javascript" src="${skinPath}/js/showroom.js" ></script>
<script type="text/javascript">
  var ROOT = '${skinPath}';
  $(document).ready(function() {
  	loadShowrooms('.product');
  });
</script>