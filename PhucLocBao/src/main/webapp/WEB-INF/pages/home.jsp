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
		<div class="row">
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-orange panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<a href="${pageContext.request.contextPath}/notificationContract" class="mute-link-style">
							<svg class="glyph stroked empty-message"><use xlink:href="#stroked-empty-message"></use></svg>
							</a>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="large">${generalView.totalNotification}</div>
							<div class="text-muted">Thông báo mới</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-blue panel-widget ">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<a href="${pageContext.request.contextPath}/oldContracts" class="mute-link-style">
								<svg class="glyph stroked bag"><use xlink:href="#stroked-bag"></use></svg>
							</a>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="large">${generalView.totalContractOfCompany}</div>
							<div class="text-muted">Hợp đồng</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-teal panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<a href="${pageContext.request.contextPath}/mngContracts" class="mute-link-style">
								<svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>
							</a>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="large">${generalView.totalInProgressContract}</div>
							<div class="text-muted">Hợp đồng mới</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-red panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<a href="${pageContext.request.contextPath}/badContracts" class="mute-link-style">
								<svg class="glyph stroked app-window-with-content"><use xlink:href="#stroked-app-window-with-content"></use></svg>
							</a>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="large">${generalView.totalBadContract}</div>
							<div class="text-muted">HĐ quá hạn</div>
						</div>
					</div>
				</div>
			</div>
		</div><!--/.row-->
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("homeAlert");
});
</script>
</div>