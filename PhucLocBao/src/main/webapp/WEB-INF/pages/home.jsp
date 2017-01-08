<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">	
<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tổng quan</li>
		</ol>
	</div><!--/.row-->
<c:if test="${not empty msg}">
	<div id="homeAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<strong>${msg}</strong>
	</div>
</c:if>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("homeAlert");
});
</script>
</div>