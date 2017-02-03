<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý chi nhánh</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="companyAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<!-- Table -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="pull-right">
								<button id="btnNewBranch" type="button" class="btn btn-primary" name="new">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Tạo công ty
								</button>
								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<table data-toggle="table" data-pagination="true" >
							    <thead>
							    <tr>
							        <th data-field="name" data-sortable="true">Tên chi nhánh</th>
							        <th data-field="type" data-sortable="true">Loại hình</th>
							        <th data-field="phoneNumber" data-sortable="true">ĐT</th>
							        <th data-field="address" data-sortable="true">Địa chỉ</th>
							        <th data-field="city" data-sortable="true">TP/Tỉnh</th>
							        <th data-field="userAccounts" >Tài khoản</th>
							        <th >Thao tác</th>
							    </tr>
							    </thead>
							    <tbody>
							    	<c:forEach var="branch" items="${cbBean.companies}">
								    	<tr>
								    		<td>
								    			${branch.name}
								    		</td>
											<td class="text-right">
												${branch.type.name}
											</td>
											<td class="text-right">
												${branch.phoneNumber}
											</td>
											<td class="text-right">
												${branch.address}
											</td>
											<td class="text-center">
												${branch.cityInString}
											</td>
											<td class="text-left" >
												${branch.userAccountsInString}
											</td>
											<td>
												
											</td>
								    	</tr>
							    	</c:forEach>
							    </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/addCompanyBranch.jsp"></jsp:include>
</div>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("companyAlert");
	cb_init();
});
</script>
	