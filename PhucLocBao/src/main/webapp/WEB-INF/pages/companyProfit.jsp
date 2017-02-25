<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Báo cáo doanh thu</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="generalAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="companyProfitForm" method="post" action="filterProfit" modelAttribute="cpBean">
		<div class="row">
			<div class="col-lg-12 header-print">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>Thu/Chi trong năm<span class="year-profit printable">&nbsp;${cpBean.year}</span></div>
					<div class="panel-body noprint">
						<div class="col-md-6 ">
							<div class="row">
								<div class="col-md-12 text-left">
									<div class="pull-left">
									<strong style="line-height: 32px;">Năm</strong>
									</div>
									<form:select class="form-control pull-left stay-left noprint" id="profitByYear" style="width:85px;display: inline-block;"    
										name="profitByYear" path="year">
										<form:options items="${cpBean.years}"/>
									</form:select>
									<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left noprint" name="search">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										Xem
									</button>
								</div>
								
							</div>
						</div>
						 <div class="col-md-6">
								<a href="javascript:window.print();" class="noprint pull-right">
								<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
								In trang</a>
						</div> 
					</div>
				</div>
			</div>
		</div>
		</form:form>
	<!-- Table -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-body">
						<div >
								Thống kê thu chi <small>(triệu đồng)</small> <div class="profit-by-month"></div> <small>thu</small> <div class="cost-by-month"></div> <small>chi</small>
						</div> 
					<table data-toggle="table" data-pagination="true" >
					    <thead>
					    <tr>
					        <th data-field="companyName"><span class="month-header-screen">Công ty</span><span class="month-header-print">Cty</span></th>
					        <th></th>
					        <th ><span class="month-header-screen">Tháng 1</span><span class="month-header-print">Tg1</span></th>
					        <th ><span class="month-header-screen">Tháng 2</span><span class="month-header-print">Tg2</span></th>
					        <th ><span class="month-header-screen">Tháng 3</span><span class="month-header-print">Tg3</span></th>
					        <th ><span class="month-header-screen">Tháng 4</span><span class="month-header-print">Tg4</span></th>
					        <th ><span class="month-header-screen">Tháng 5</span><span class="month-header-print">Tg5</span></th>
					        <th ><span class="month-header-screen">Tháng 6</span><span class="month-header-print">Tg6</span></th>
					        <th ><span class="month-header-screen">Tháng 7</span><span class="month-header-print">Tg7</span></th>
					        <th ><span class="month-header-screen">Tháng 8</span><span class="month-header-print">Tg8</span></th>
					        <th ><span class="month-header-screen">Tháng 9</span><span class="month-header-print">Tg9</span></th>
					        <th ><span class="month-header-screen">Tháng 10</span><span class="month-header-print">Tg10</span></th>
					        <th ><span class="month-header-screen">Tháng 11</span><span class="month-header-print">Tg11</span></th>
					        <th ><span class="month-header-screen">Tháng 12</span><span class="month-header-print">Tg12</span></th>
					    </tr>
					    </thead>
					    <tbody>
					    	<c:forEach var="stat" items="${cpBean.statistics}">
						    	<tr>
						    		<td rowspan="2" class="valid">
						    			${stat.companyName}
						    		</td>
						    		<td class="valid">
						    			<div class="profit-by-month"></div>
						    		</td>
						    		<c:forEach var="profit" items="${stat.profitByMonth}" >
										<td  class="valid text-right">
											<c:if test="${profit > 0}"><span class="state_good bold-text">${profit}</span></c:if>
											<c:if test="${profit < 0}"><span class="state_alert bold-text">${profit}</span></c:if>
											<c:if test="${profit == 0}"><span class="state_normal">${profit}</span></c:if>
										</td>
									</c:forEach>
									
						    	</tr>
						    	<tr>
						    		<td class="valid"><div class="cost-by-month cost-by-month-normal"></div></td>
						    		<c:forEach var="cost" items="${stat.rentingCostByMonth}" >
										<td class="valid text-right">
											<c:if test="${cost < 0}"><span class="state_good bold-text">${cost}</span></c:if>
											<c:if test="${cost > 0}"><span class="state_alert bold-text">${cost}</span></c:if>
											<c:if test="${cost == 0}"><span class="state_normal">${cost}</span></c:if>
										</td>
									</c:forEach>
						    	</tr>
					    	</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
<script src="<c:url value='/resources/js/bootstrap-complextable.js' />"><!-- comment --></script>
	<script type="text/javascript">
		$( document ).ready(function() {
			hideAlert("generalAlert");
			cp_init();
		});
	</script>
</div>