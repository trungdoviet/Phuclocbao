package vn.com.phuclocbao.dto;

import java.util.List;

import vn.com.phuclocbao.dto.base.IBaseDTO;

public class CompanyDto implements IBaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7660820456273994327L;
	/**
	 * Identifier
	 */
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String address;
	private java.lang.String description;
	private java.util.List<UserAccountDto> userAccounts;
	private java.lang.String fax;
	private CompanyTypeDto type;
	private java.lang.String city;
	private java.lang.String phoneNumber;
	private java.lang.Double originalFund;
	private java.lang.String motobikeRentingFund;
	private java.util.Date startDate;
	private java.lang.Double revenueBeforeStartDate;
	private java.lang.Double costBeforeStartDate;
	private java.lang.String investBeforeStartDate;
	private java.util.List<ContractDto> contracts;
	private java.util.List<ContractHistoryDto> histories;

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
	 * Gets the field name.
	 * 
	 * @return the value of the field name; may be null.
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the field name.
	 * 
	 * @param _name
	 *            the new value of the field name.
	 */
	public void setName(java.lang.String _name) {
		name = _name;
	}

	/**
	 * Gets the field address.
	 * 
	 * @return the value of the field address; may be null.
	 */
	public java.lang.String getAddress() {
		return address;
	}

	/**
	 * Sets the field address.
	 * 
	 * @param _address
	 *            the new value of the field address.
	 */
	public void setAddress(java.lang.String _address) {
		address = _address;
	}

	/**
	 * Gets the field description.
	 * 
	 * @return the value of the field description; may be null.
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Sets the field description.
	 * 
	 * @param _description
	 *            the new value of the field description.
	 */
	public void setDescription(java.lang.String _description) {
		description = _description;
	}

	/**
	 * Gets the field userAccounts.
	 * 
	 * @return the value of the field userAccounts; may be null.
	 */
	public java.util.List<UserAccountDto> getUserAccounts() {
		return userAccounts;
	}

	/**
	 * Sets the field userAccounts.
	 * 
	 * @param _userAccounts
	 *            the new value of the field userAccounts.
	 */
	public void setUserAccounts(List<UserAccountDto> _userAccounts) {
		userAccounts = _userAccounts;
	}

	/**
	 * Gets the field city.
	 * 
	 * @return the value of the field city; may be null.
	 */
	public java.lang.String getCity() {
		return city;
	}

	/**
	 * Sets the field city.
	 * 
	 * @param _city
	 *            the new value of the field city.
	 */
	public void setCity(java.lang.String _city) {
		city = _city;
	}

	/**
	 * Gets the field phoneNumber.
	 * 
	 * @return the value of the field phoneNumber; may be null.
	 */
	public java.lang.String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the field phoneNumber.
	 * 
	 * @param _phoneNumber
	 *            the new value of the field phoneNumber.
	 */
	public void setPhoneNumber(java.lang.String _phoneNumber) {
		phoneNumber = _phoneNumber;
	}

	/**
	 * Gets the field fax.
	 * 
	 * @return the value of the field fax; may be null.
	 */
	public java.lang.String getFax() {
		return fax;
	}

	/**
	 * Sets the field fax.
	 * 
	 * @param _fax
	 *            the new value of the field fax.
	 */
	public void setFax(java.lang.String _fax) {
		fax = _fax;
	}

	/**
	 * Gets the field originalFund.
	 * 
	 * @return the value of the field originalFund; may be null.
	 */
	public java.lang.Double getOriginalFund() {
		return originalFund;
	}

	/**
	 * Sets the field originalFund.
	 * 
	 * @param _originalFund
	 *            the new value of the field originalFund.
	 */
	public void setOriginalFund(java.lang.Double _originalFund) {
		originalFund = _originalFund;
	}

	private java.lang.String totalFund;

	/**
	 * Gets the field totalFund.
	 * 
	 * @return the value of the field totalFund; may be null.
	 */
	public java.lang.String getTotalFund() {
		return totalFund;
	}

	/**
	 * Sets the field totalFund.
	 * 
	 * @param _totalFund
	 *            the new value of the field totalFund.
	 */
	public void setTotalFund(java.lang.String _totalFund) {
		totalFund = _totalFund;
	}

	/**
	 * Gets the field motobikeRentingFund.
	 * 
	 * @return the value of the field motobikeRentingFund; may be null.
	 */
	public java.lang.String getMotobikeRentingFund() {
		return motobikeRentingFund;
	}

	/**
	 * Sets the field motobikeRentingFund.
	 * 
	 * @param _motobikeRentingFund
	 *            the new value of the field motobikeRentingFund.
	 */
	public void setMotobikeRentingFund(java.lang.String _motobikeRentingFund) {
		motobikeRentingFund = _motobikeRentingFund;
	}

	/**
	 * Gets the field startDate.
	 * 
	 * @return the value of the field startDate; may be null.
	 */
	public java.util.Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the field startDate.
	 * 
	 * @param _startDate
	 *            the new value of the field startDate.
	 */
	public void setStartDate(java.util.Date _startDate) {
		startDate = _startDate;
	}

	/**
	 * Gets the field revenueBeforeStartDate.
	 * 
	 * @return the value of the field revenueBeforeStartDate; may be null.
	 */
	public java.lang.Double getRevenueBeforeStartDate() {
		return revenueBeforeStartDate;
	}

	/**
	 * Sets the field revenueBeforeStartDate.
	 * 
	 * @param _revenueBeforeStartDate
	 *            the new value of the field revenueBeforeStartDate.
	 */
	public void setRevenueBeforeStartDate(java.lang.Double _revenueBeforeStartDate) {
		revenueBeforeStartDate = _revenueBeforeStartDate;
	}

	/**
	 * Gets the field costBeforeStartDate.
	 * 
	 * @return the value of the field costBeforeStartDate; may be null.
	 */
	public java.lang.Double getCostBeforeStartDate() {
		return costBeforeStartDate;
	}

	/**
	 * Sets the field costBeforeStartDate.
	 * 
	 * @param _costBeforeStartDate
	 *            the new value of the field costBeforeStartDate.
	 */
	public void setCostBeforeStartDate(java.lang.Double _costBeforeStartDate) {
		costBeforeStartDate = _costBeforeStartDate;
	}

	/**
	 * Gets the field investBeforeStartDate.
	 * 
	 * @return the value of the field investBeforeStartDate; may be null.
	 */
	public java.lang.String getInvestBeforeStartDate() {
		return investBeforeStartDate;
	}

	/**
	 * Sets the field investBeforeStartDate.
	 * 
	 * @param _investBeforeStartDate
	 *            the new value of the field investBeforeStartDate.
	 */
	public void setInvestBeforeStartDate(java.lang.String _investBeforeStartDate) {
		investBeforeStartDate = _investBeforeStartDate;
	}

	public CompanyTypeDto getType() {
		return type;
	}

	public void setType(CompanyTypeDto type) {
		this.type = type;
	}

	public java.util.List<ContractDto> getContracts() {
		return contracts;
	}

	public void setContracts(java.util.List<ContractDto> contracts) {
		this.contracts = contracts;
	}

	public java.util.List<ContractHistoryDto> getHistories() {
		return histories;
	}

	public void setHistories(java.util.List<ContractHistoryDto> histories) {
		this.histories = histories;
	}

}
