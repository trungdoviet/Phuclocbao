package vn.com.phuclocbao.dto;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.Views;
import vn.com.phuclocbao.dto.base.IBaseDTO;

/**
 */
public class CustomerDto implements IBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2876378178645246224L;
	/**
	 * Identifier
	 */
	@JsonView(Views.Public.class)
	private java.lang.Integer id;
	@JsonView(Views.Public.class)
	private java.lang.String idNo;
	@JsonView(Views.Public.class)
	private java.lang.String name;
	@JsonView(Views.Public.class)
	private java.lang.String phone;
	@JsonView(Views.Public.class)
	private java.lang.String address;
	@JsonView(Views.Public.class)
	private java.lang.String province;
	private ContractDto contract;
	@JsonView(Views.Public.class)
	private Integer birthYear;

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
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
	 * Gets the field idNo.
	 * 
	 * @return the value of the field idNo; may be null.
	 */
	public java.lang.String getIdNo() {
		return idNo;
	}

	/**
	 * Sets the field idNo.
	 * 
	 * @param _idNo
	 *            the new value of the field idNo.
	 */
	public void setIdNo(java.lang.String _idNo) {
		idNo = _idNo;
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
	 * Gets the field phone.
	 * 
	 * @return the value of the field phone; may be null.
	 */
	public java.lang.String getPhone() {
		return phone;
	}

	/**
	 * Sets the field phone.
	 * 
	 * @param _phone
	 *            the new value of the field phone.
	 */
	public void setPhone(java.lang.String _phone) {
		phone = _phone;
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
	 * Gets the field province.
	 * 
	 * @return the value of the field province; may be null.
	 */
	public java.lang.String getProvince() {
		return province;
	}

	/**
	 * Sets the field province.
	 * 
	 * @param _province
	 *            the new value of the field province.
	 */
	public void setProvince(java.lang.String _province) {
		province = _province;
	}

	public ContractDto getContract() {
		return contract;
	}

	public void setContract(ContractDto contract) {
		this.contract = contract;
	}

}
