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
		<div class="row">
			<div class="col-lg-8">
				<div class="panel panel-default">
					<div class="panel-heading">Bar Chart</div>
					<div class="panel-body">
						<div class="canvas-wrapper">
							<canvas class="main-chart" id="bar-chart" height="200" width="600"></canvas>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<iframe id="fr31" style="border: none;" src="//www.tygia.com/api.php?cols=1&amp;title=0&amp;chart=1&amp;gold=1&amp;rate=0&amp;expand=0&amp;color=FFFFFF&amp;titlecolor=333333&amp;nganhang=VIETCOM&amp;fontsize=80&amp;change=1&amp;css=%23SJC_N_ng{display:%20table-row%20!important;}" width="100%" height="370"></iframe>
			</div>
			
		</div>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("homeAlert");
	onLoadChart();
});
var profitByMonth = ${generalView.statistic.profitByMonth};
var costByMonth = ${generalView.statistic.rentingCostByMonth}
</script>

</div>
<script src="<c:url value='/resources/js/chart.min.js' />"><!-- comment --></script>
<script src="<c:url value='/resources/js/chart-data.js' />"><!-- comment --></script>
<script src="<c:url value='/resources/js/easypiechart.js' />"><!-- comment --></script>
<script src="<c:url value='/resources/js/easypiechart-data.js' />"><!-- comment --></script>