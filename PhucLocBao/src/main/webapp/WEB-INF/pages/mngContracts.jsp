<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý hợp đồng</li>
		</ol>
	</div><!--/.row-->
	<form:form role="form" id="mngContractForm" method="post" action="availableContracts" modelAttribute="contractBean">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>Quản lý thông tin hợp đồng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Hợp đồng cho thuê xe:</strong></div><div id="mcTotalContract" class="col-md-4 text-left">16</div>
							</div>
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số hợp đồng chưa thanh lý:</strong></div><div id="mcInProgressContract" class="col-md-4 text-left">16</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng phí hợp đồng/ngày:</strong></div><div id="mcTotalFeeADay" class="col-md-4 text-right">414.000 VNĐ</div>
							</div>
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số tiền hợp đồng chưa thanh lý::</strong></div><div id="mcTotalUnpaidCost" class="col-md-4 text-right">138.000.000 VNĐ</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Table -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<table data-toggle="table" data-pagination="true" >
						    <thead>
						    <tr>
						        <th data-field="customerName" data-sortable="true">Tên khách hàng</th>
						        <th data-field="contractType" data-sortable="true">Loại hình thuê</th>
						        <th data-field="totalAmount" data-sortable="true">Số tiền</th>
						        <th data-field="feeAday" data-sortable="true">Phí/ngày</th>
						        <th data-field="startDate" data-sortable="true">Ngày thuê</th>
						        <th data-field="expireDate" data-sortable="true">Ngày hết hạn</th>	
						        <th data-field="action" >Thao tác</th>	 		
						    </tr>
						    </thead>
						    <tbody>
						    	<tr>
						    		<td>Col 1</td>
									<td>Col 2</td>
									<td>Col 3</td>
									<td>Col 4</td>
									<td>Col 5</td>
									<td>Col 6</td>
									<td>Col 7</td>
						    	</tr>
						    	<tr>
						    		<td>Col 1</td>
									<td>Col 2</td>
									<td>Col 3</td>
									<td>Col 4</td>
									<td>Col 5</td>
									<td>Col 6</td>
									<td>Col 7</td>
						    	</tr>
						    	<tr>
						    		<td>Col 1</td>
									<td>Col 2</td>
									<td>Col 3</td>
									<td>Col 4</td>
									<td>Col 5</td>
									<td>Col 6</td>
									<td>Col 7</td>
						    	</tr>
						    	<tr>
						    		<td>Col 1</td>
									<td>Col 2</td>
									<td>Col 3</td>
									<td>Col 4</td>
									<td>Col 5</td>
									<td>Col 6</td>
									<td>Col 7</td>
						    	</tr>
						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form:form>

</div>