<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý Tài khoản</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="muAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
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
								<button id="btnNewUser" type="button" class="btn btn-primary" name="new">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Tạo tài khoản
								</button>
								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<table data-toggle="table" data-pagination="true" >
							    <thead>
							    <tr>
							        <th data-field="username" data-sortable="true">Tài khoản</th>
							        <th data-field="fullname" data-sortable="true">Họ tên</th>
							        <th data-field="email" data-sortable="true">Email</th>
							        <th data-field="isAdmin" >Quyền hạn</th>
							        <th data-field="companyEntity">Công ty</th>
							        <th >Thao tác</th>
							    </tr>
							    </thead>
							    <tbody>
							    	<c:forEach var="user" items="${muBean.users}">
								    	<tr>
								    		<td>
								    			${user.username}
								    		</td>
											<td>
												${user.fullname}
											</td>
											<td>
												${user.email}
											</td>
											<td >
												<c:if test="${user.isAdmin == 'Y' }">
													<span class="label label-success">Admin</span>
												</c:if>
												<c:if test="${user.isAdmin != 'Y' }">
													<span class="label label-info">Thường</span>
												</c:if>
											</td>
											<td  >
												${user.companyEntity.name}
											</td>
											<td>
												<button class="btn btn-danger btn-xs" onclick="cp_openDialog('${user.username}','${user.id}' )">Đổi mật khẩu</button>
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
	<jsp:include page="includes/addUser.jsp"></jsp:include>
	<jsp:include page="includes/changeUserPassword.jsp"></jsp:include>
</div>
<script src="<c:url value='/resources/js/jquery.validate.min.js' />"><!-- comment --></script>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("muAlert");
	mu_init();
	cp_init();
});
</script>