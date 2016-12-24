<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tạo hợp đồng mới</li>
		</ol>
	</div><!--/.row-->
	
	<!-- <div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Tạo hợp đồng mới</h1>
		</div>
	</div> --><!--/.row-->
<form:form role="form" id="newContractForm" method="post" action="newContractAction" modelAttribute="contractBean">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>Thông tin khách hàng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.idNo">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số CMND/Số GPLX:</label>
									<form:input class="form-control" placeholder="BA-012345678" name="customerIdNo" path="contractDto.customer.idNo"/>
									<form:errors path="contractDto.customer.idNo" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.name">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tên khách hàng:</label>
									<form:input class="form-control" placeholder="Nguyễn Văn A" name="customerName" path="contractDto.customer.name"/>
									<form:errors path="contractDto.customer.name" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.phone">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số điện thoại:</label>
									<form:input class="form-control" placeholder="0909 123456" name="customerPhone" path="contractDto.customer.phone"/>
									<form:errors path="contractDto.customer.phone" cssClass="error" />
								</div>		
							</spring:bind>						
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.address">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Địa chỉ:</label>
									<form:textarea class="form-control" placeholder="B64 Bạch Đằng" rows="6" name="customerAddress" path="contractDto.customer.address"/>
									<form:errors path="contractDto.customer.address"  cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.province">		
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-top: -6px;">
									<label>Tỉnh/Thành phố:</label>
									<form:select class="form-control" name="customerProvince" path="contractDto.customer.province">
										<option value="">--Chọn Tỉnh/TP--</option>
										<option value="HCM">TP.Hồ Chí Minh</option>
										<option value="HN">Hà Nội</option>
										<option value="CB">Cao Bằng</option>
										<option value="LC">Lai Châu</option>
									</form:select>
									<form:errors path="contractDto.customer.province"  cssClass="error" />
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<spring:url value="/newContractAction/cancel" var="cancelUrl" />
				<button type="submit" class="btn btn-primary" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					Tạo hợp đồng
				</button>
				<button type="button" class="btn btn-slave" name="cancel" onclick="location.href='${cancelUrl}'">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					Đóng
				</button>
			</div>
		</div>
	</div>
</form:form>
</div>