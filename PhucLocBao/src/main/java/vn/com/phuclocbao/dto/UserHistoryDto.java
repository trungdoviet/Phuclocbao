package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.UserActionHistoryType;

public class UserHistoryDto implements IBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7712411339287741588L;
	private java.lang.Integer id;
	private java.lang.String actionType;
	private java.lang.String accountName;
	private java.lang.String companyName;
	private java.util.Date happenTime;
	private java.lang.String detail;
	public String getHistoryName(){
		return UserActionHistoryType.getByType(actionType).getName();
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
	 * Gets the field actionType.
	 * 
	 * @return the value of the field actionType; may be null.
	 */
	public java.lang.String getActionType() {
		return actionType;
	}

	/**
	 * Sets the field actionType.
	 * 
	 * @param _actionType
	 *            the new value of the field actionType.
	 */
	public void setActionType(java.lang.String _actionType) {
		actionType = _actionType;
	}

	/**
	 * Gets the field accountName.
	 * 
	 * @return the value of the field accountName; may be null.
	 */
	public java.lang.String getAccountName() {
		return accountName;
	}

	/**
	 * Sets the field accountName.
	 * 
	 * @param _accountName
	 *            the new value of the field accountName.
	 */
	public void setAccountName(java.lang.String _accountName) {
		accountName = _accountName;
	}

	/**
	 * Gets the field companyName.
	 * 
	 * @return the value of the field companyName; may be null.
	 */
	public java.lang.String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the field companyName.
	 * 
	 * @param _companyName
	 *            the new value of the field companyName.
	 */
	public void setCompanyName(java.lang.String _companyName) {
		companyName = _companyName;
	}

	/**
	 * Gets the field happenTime.
	 * 
	 * @return the value of the field happenTime; may be null.
	 */
	public java.util.Date getHappenTime() {
		return happenTime;
	}

	/**
	 * Sets the field happenTime.
	 * 
	 * @param _happenTime
	 *            the new value of the field happenTime.
	 */
	public void setHappenTime(java.util.Date _happenTime) {
		happenTime = _happenTime;
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

}
