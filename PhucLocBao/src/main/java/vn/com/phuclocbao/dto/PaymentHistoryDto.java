package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;
import vn.com.phuclocbao.enums.PaymentHistoryType;

public class PaymentHistoryDto implements IBaseDTO {

	private static final long serialVersionUID = 8922646573215438964L;
	private java.lang.Integer id;
	private java.lang.Double fee;
	private java.lang.Double rentingAmount;
	private java.lang.Double payoff;
	private java.util.Date logDate;
	private java.lang.String detail;
	private java.lang.String historyType;
	private Integer companyId;
	private Integer contractId;
	
	public String getHistoryName(){
		return PaymentHistoryType.getByType(historyType).getName();
	}
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * Gets the field detail.
	 * 
	 * @return the value of the field detail; may be null.
	 */
	public java.lang.String getDetail() {
		return detail;
	}

	/**
	 * Sets the field detail.
	 * 
	 * @param _detail
	 *            the new value of the field detail.
	 */
	public void setDetail(java.lang.String _detail) {
		detail = _detail;
	}
	/**
	 * Gets the field id.
	 * 
	 * @return the value of the field id; may be null.
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * Sets the field id.
	 * 
	 * @param _id
	 *            the new value of the field id.
	 */
	public void setId(java.lang.Integer _id) {
		id = _id;
	}

	/**
	 * Gets the field fee.
	 * 
	 * @return the value of the field fee; may be null.
	 */
	public java.lang.Double getFee() {
		return fee;
	}

	/**
	 * Sets the field fee.
	 * 
	 * @param _fee
	 *            the new value of the field fee.
	 */
	public void setFee(java.lang.Double _fee) {
		fee = _fee;
	}

	/**
	 * Gets the field rentingAmount.
	 * 
	 * @return the value of the field rentingAmount; may be null.
	 */
	public java.lang.Double getRentingAmount() {
		return rentingAmount;
	}

	/**
	 * Sets the field rentingAmount.
	 * 
	 * @param _rentingAmount
	 *            the new value of the field rentingAmount.
	 */
	public void setRentingAmount(java.lang.Double _rentingAmount) {
		rentingAmount = _rentingAmount;
	}

	/**
	 * Gets the field payoff.
	 * 
	 * @return the value of the field payoff; may be null.
	 */
	public java.lang.Double getPayoff() {
		return payoff;
	}

	/**
	 * Sets the field payoff.
	 * 
	 * @param _payoff
	 *            the new value of the field payoff.
	 */
	public void setPayoff(java.lang.Double _payoff) {
		payoff = _payoff;
	}

	/**
	 * Gets the field logDate.
	 * 
	 * @return the value of the field logDate; may be null.
	 */
	public java.util.Date getLogDate() {
		return logDate;
	}

	/**
	 * Sets the field logDate.
	 * 
	 * @param _logDate
	 *            the new value of the field logDate.
	 */
	public void setLogDate(java.util.Date _logDate) {
		logDate = _logDate;
	}

	private java.lang.String note;

	/**
	 * Gets the field note.
	 * 
	 * @return the value of the field note; may be null.
	 */
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * Sets the field note.
	 * 
	 * @param _note
	 *            the new value of the field note.
	 */
	public void setNote(java.lang.String _note) {
		note = _note;
	}

	public java.lang.String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(java.lang.String historyType) {
		this.historyType = historyType;
	}
}
