<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade" id="monthlyProfit" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close noprint" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       	<div style="height:24px">
			     <h4 class="modal-title">Tổng hợp Thu chi <span id="profitTime"></span> </h4>
			</div>
	      </div>
	      <div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					Tên công ty: <span class="bold-text" id="profitCompanyName"></span>
					<table data-toggle="table">
					    <thead>
						    <tr>
						        <th data-align="left" >Mục</th>
						        <th data-align="right" >Số tiền</th>
						    </tr>
					    </thead>
					    <tbody>
					    	<tr>
					    		<td class="valid">Tổng giá trị hợp đồng chưa thanh lý:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị tất cả hợp đồng chưa thanh lý hiện có" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalRunningContractAmount"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng giá trị hợp đồng đã thanh lý:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị tất cả hợp đồng đã thanh lý hiện có" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalFinishContractAmount"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng giá trị hợp đồng xấu:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị tất cả hợp đồng quá hạn hiện có!" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalBadContractAmount"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng giá trị hợp đồng thuê xe:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị tất cả hợp đồng công ty hiện có" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalContractAmount"></span></td>
					    	</tr>
					    	<tr class="bg-white">
					    		<td class="valid text-center bold-text bg-white" colspan="2">Chi tiết</td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng đầu tư:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng đầu tư = Tổng nhập vốn - Tổng xuất vốn (trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalInvest"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng tiền cho thuê xe:
					    		<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị hợp đồng đã cho thuê (trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalRentingNew"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng tiền thanh lý xe:<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng giá trị hợp đồng đã thanh lý không bao gồm phí (trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalPayoff"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Doanh thu (tổng phí đã thu):<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Doanh thu = [Tổng phí thuê xe đã thu + Tổng khách trả nợ + Tổng cty nợ + Tổng phí khách thiếu khi thanh lý] - [Tổng khách nợ + Tổng cty trả nợ + Tổng thừa phí của khách khi thanh lý] (trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalRevenueContractAmount"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng chi phí khác:<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng chi khác(trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalOtherCost"></span></td>
					    	</tr>
					    	<tr>
					    		<td class="valid">Tổng thu khác:<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Tổng thu khác(trong khoảng thời gian bạn chọn)" aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text" id="totalOtherIncome"></span></td>	 	 
					    	</tr>
					    	<tr>
					    		<td class="valid">Kết quả kinh doanh:<span class="glyphicon glyphicon-question-sign pull-right noprint" data-toggle="tooltip" title="Lợi nhuận = Tổng phí đã thu + Tổng thu khác - Tổng chi khác (trong khoảng thời gian bạn chọn)"  aria-hidden="true"></span></td> 
					    		<td class="valid"> <span class="bold-text state_alert" id="totalProfit"></span></td>				
					    	</tr>
					    </tbody>
					</table>
				</div>
			</div><!--/.row-->
	      </div>
	      <div class="modal-footer">
		      <div class="row">
			      <div class="col-md-3">
			      	<input type="hidden" id="profitMonth"/>
			      	<input type="hidden" id="profitYear"/>
			      	<input type="hidden" id="profitCompany"/>
			      	<a href="javascript:window.print();" class="noprint pull-left" style="padding-top: 5px;">
						<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
						In trang</a>
			      </div>
			       <div class="col-md-3">
			       	 <button type="button" class="btn btn-info pull-right noprint" id="monthlyProfitBack"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span><span id="btnProfitBack">Tháng trước</span></button>
			      </div>
			       <div class="col-md-3">
			       	 <button type="button" class="btn btn-info pull-left noprint" id="monthlyProfitNext"> <span id="btnProfitNext">Tháng tới</span><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></button>
			      </div>
			       <div class="col-md-3">
			       	 <button type="button" class="btn btn-default noprint" data-dismiss="modal" id="monthlyProfitClose">Đóng</button>
			      </div>
		       </div>
	      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->