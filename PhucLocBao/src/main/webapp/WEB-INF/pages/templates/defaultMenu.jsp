<%@ page import="vn.com.phuclocbao.bean.PLBSession" %>
<%@include file="../includes/jstl.jsp"%>
<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar noprint">
		<form role="search">
			<div class="form-group">
				<!-- <input type="text" class="form-control" placeholder="Search"> -->
				 <strong class="as-block">Số dư hiện tại</strong> 
				<span class="as-block <%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getCurrentCompany().getTotalRefundingSeverity()%>"><strong id="companyTotalAmount" class="big-money-size"><%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getCurrentCompany().returnTotalFundAsPlainString()%> </strong></span> 
			</div>
		</form>
		<ul class="nav menu">
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("home")%>'><a href="${pageContext.request.contextPath}/home"><svg class="glyph stroked dashboard-dial"><use xlink:href="#stroked-dashboard-dial"></use></svg> Tổng Quan</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("newContract")%>'><a href="${pageContext.request.contextPath}/newContract"><svg class="glyph stroked pencil"><use xlink:href="#stroked-pencil"></use></svg>Tạo hợp đồng</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("mngContracts")%>'><a href="${pageContext.request.contextPath}/mngContracts"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>Quản lý hợp đồng</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("oldContracts")%>'><a href="${pageContext.request.contextPath}/oldContracts"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>Hợp đồng cũ</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("dailyWorks")%>'><a href="${pageContext.request.contextPath}/dailyWorks"><svg class="glyph stroked app-window"><use xlink:href="#stroked-app-window"></use></svg>Chi tiết hàng ngày</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("notificationContract")%>'><a href="${pageContext.request.contextPath}/notificationContract"><svg class="glyph stroked star"><use xlink:href="#stroked-star"></use></svg>Thông báo</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("history")%>'><a href="${pageContext.request.contextPath}/history"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg>Nhật ký hoạt động</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("badContracts")%>'><a href="${pageContext.request.contextPath}/badContracts"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg>Báo cáo nợ xấu</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("customerSearch")%>'><a href="${pageContext.request.contextPath}/customerSearch"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>Tìm kiếm khách hàng</a></li>
			<li role="presentation" class="divider"></li>
			<!-- <li><a href="login.html"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li> -->
			<li class="parent <%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyFinancial")%> 
			<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyBranch")%>
			<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyProfit")%>
			<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("mngUser")%> ">
				<a href="#">
					<span data-toggle="collapse" href="#sub-item-1"><svg id="adminMenu" class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg></span> <span id="companyNameMenu">Công ty <%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getCurrentCompany().getName() %></span> 
				</a>
				<c:if test="${sessionScope.isUserAdmin == 'Y'}">
					<ul class="children collapse <%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getCompanyMenuCollapseState()%>" id="sub-item-1">
						<li class="<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyFinancial")%>">
							<a  href="${pageContext.request.contextPath}/companyFinancial">
								<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Quản lý Tài chính
							</a>
						</li>
						<c:if test="${sessionScope.isHeadOffice == 'Y'}">
							<li class="<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyBranch")%>">
								<a  href="${pageContext.request.contextPath}/companyBranch">
									<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Quản lý chi nhánh
								</a>
							</li>
							<li class="<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("mngUser")%>">
								<a  href="${pageContext.request.contextPath}/mngUser">
									<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Quản lý Tài khoản
								</a>
							</li>
							<li class="<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("companyProfit")%>">
								<a  href="${pageContext.request.contextPath}/companyProfit">
									<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Báo cáo doanh thu
								</a>
							</li>
						</c:if>
					</ul>
				</c:if>
			</li>
		</ul>
		<div class="divider"></div>
		<div class="text-center"><label class="normal-text-weight">Phúc Lộc Bảo &copy;&nbsp;<script>document.write(new Date().getFullYear())</script></label></div>
	</div><!--/.sidebar-->
	
	<script>
	$( document ).ready(function() {
		$("#companyNameMenu").on("click", function(){
			$("#adminMenu").trigger("click");
		})
	});
	</script>