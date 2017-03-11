<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade" id="payOffModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Xác nhận</h4>
      </div>
      <div class="modal-body">
        <p>Xác nhận thanh lý hợp đồng. </p>
         <p>Tiền thanh lý hợp đồng:<strong><fmt:formatNumber  currencySymbol=" "  value="${contractBean.contractDto.totalAmount}" type="currency" maxFractionDigits="0" var="mcTotalCost"/>
											${fn:replace(mcTotalCost, ",", ".")}
											VNĐ</strong></p>
        <p><span id="payOffCustomerMsg">Số tiền thiếu phí đã nhận của khách </span><span id="payOffCompanyMsg">Số tiền thừa đã trả khách </span><b><span id="totalRefundAmount"></span>&nbsp;VNĐ</b></p>
         <p>Thao tác này không thể thay đổi.Vui lòng kiểm tra kỹ thông tin.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="payOffClose">Thoát</button>
        <button type="button" class="btn btn-primary" id="payOffOk">Đã kiểm tra</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->