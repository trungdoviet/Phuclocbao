package vn.com.phuclocbao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import vn.com.phuclocbao.entity.base.IBaseEntity;
@Entity
@javax.persistence.Table(name="tblCompany")
@NamedQueries({
	@NamedQuery(name = "companyEntity_deleteById", query = "DELETE FROM CompanyEntity ce WHERE ce.id = :companyId"),
})
public class CompanyEntity implements IBaseEntity
{
	/** SerialVersionUID */
	  private static final long serialVersionUID = -9114003315500207042L;

	  /**
	   * Identifier
	   */
	  @javax.persistence.Id
	  @javax.persistence.GeneratedValue
	  private java.lang.Integer id;
	  @javax.persistence.Column(nullable=false, length=255)
	  private java.lang.String name;
	  @javax.persistence.Column(length=255)
	  private java.lang.String address;
	  @javax.persistence.Column(length=255)
	  private java.lang.String description;
	  @javax.persistence.OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE, CascadeType.REMOVE}, fetch=javax.persistence.FetchType.EAGER, mappedBy="companyEntity", orphanRemoval=true)
	  private java.util.Set<vn.com.phuclocbao.entity.UserAccount> userAccounts;
	  @javax.persistence.ManyToOne(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER)
	  @javax.persistence.JoinColumn(name="companytype")
	  private vn.com.phuclocbao.entity.CompanyType type;
	  @javax.persistence.Column(length=100)
	  private java.lang.String city;
	  @javax.persistence.Column(length=100)
	  private java.lang.String phoneNumber;
	  @javax.persistence.Column(length=45)
	  private java.lang.String state;
	  private java.lang.String motobikeRentingFund;
	  private java.lang.String fax;
	  private java.lang.Double totalFund;
	  private java.lang.Double revenueBeforeStartDate;
	  private java.lang.Double costBeforeStartDate;
	  @javax.persistence.OneToMany(fetch=javax.persistence.FetchType.EAGER, mappedBy="company", orphanRemoval=false)
	  private java.util.Set<vn.com.phuclocbao.entity.Contract> contracts;
	  private java.util.Date startDate;
	  private java.lang.Double originalFund;
	  private java.lang.Double investBeforeStartDate;
	  /**
	   * Gets the field id.
	   * @return the value of the field id; may be null.
	   */
	  public java.lang.Integer getId()
	  {
	    return id;
	  }

	  /**
	   * Sets the field id.
	   * @param _id the new value of the field id.
	   */
	  public void setId(java.lang.Integer _id)
	  {
	    id = _id;
	  }

	  
	  public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	/**
	   * Gets the field name.
	   * @return the value of the field name; may be null.
	   */
	  public java.lang.String getName()
	  {
	    return name;
	  }

	  /**
	   * Sets the field name.
	   * @param _name the new value of the field name.
	   */
	  public void setName(java.lang.String _name)
	  {
	    name = _name;
	  }

	 

	  /**
	   * Gets the field address.
	   * @return the value of the field address; may be null.
	   */
	  public java.lang.String getAddress()
	  {
	    return address;
	  }

	  /**
	   * Sets the field address.
	   * @param _address the new value of the field address.
	   */
	  public void setAddress(java.lang.String _address)
	  {
	    address = _address;
	  }

	  

	  /**
	   * Gets the field description.
	   * @return the value of the field description; may be null.
	   */
	  public java.lang.String getDescription()
	  {
	    return description;
	  }

	  /**
	   * Sets the field description.
	   * @param _description the new value of the field description.
	   */
	  public void setDescription(java.lang.String _description)
	  {
	    description = _description;
	  }

	 
	  /**
	   * Gets the field userAccounts.
	   * @return the value of the field userAccounts; may be null.
	   */
	  public java.util.Set<vn.com.phuclocbao.entity.UserAccount> getUserAccounts()
	  {
	    return userAccounts;
	  }

	  /**
	   * Sets the field userAccounts.
	   * @param _userAccounts the new value of the field userAccounts.
	   */
	  public void setUserAccounts(java.util.Set<vn.com.phuclocbao.entity.UserAccount> _userAccounts)
	  {
	    userAccounts = _userAccounts;
	  }

	  

	  /**
	   * Gets the field type.
	   * @return the value of the field type; may be null.
	   */
	  public vn.com.phuclocbao.entity.CompanyType getType()
	  {
	    return type;
	  }

	  /**
	   * Sets the field type.
	   * @param _type the new value of the field type.
	   */
	  public void setType(vn.com.phuclocbao.entity.CompanyType _type)
	  {
	    type = _type;
	  }

	 

	  /**
	   * Gets the field city.
	   * @return the value of the field city; may be null.
	   */
	  public java.lang.String getCity()
	  {
	    return city;
	  }

	  /**
	   * Sets the field city.
	   * @param _city the new value of the field city.
	   */
	  public void setCity(java.lang.String _city)
	  {
	    city = _city;
	  }

	 

	  /**
	   * Gets the field phoneNumber.
	   * @return the value of the field phoneNumber; may be null.
	   */
	  public java.lang.String getPhoneNumber()
	  {
	    return phoneNumber;
	  }

	  /**
	   * Sets the field phoneNumber.
	   * @param _phoneNumber the new value of the field phoneNumber.
	   */
	  public void setPhoneNumber(java.lang.String _phoneNumber)
	  {
	    phoneNumber = _phoneNumber;
	  }

	  	  /**
	   * Gets the field fax.
	   * @return the value of the field fax; may be null.
	   */
	  public java.lang.String getFax()
	  {
	    return fax;
	  }

	  /**
	   * Sets the field fax.
	   * @param _fax the new value of the field fax.
	   */
	  public void setFax(java.lang.String _fax)
	  {
	    fax = _fax;
	  }

	 

	  /**
	   * Gets the field originalFund.
	   * @return the value of the field originalFund; may be null.
	   */
	  public java.lang.Double getOriginalFund()
	  {
	    return originalFund;
	  }

	  /**
	   * Sets the field originalFund.
	   * @param _originalFund the new value of the field originalFund.
	   */
	  public void setOriginalFund(java.lang.Double _originalFund)
	  {
	    originalFund = _originalFund;
	  }

	 
	  /**
	   * Gets the field totalFund.
	   * @return the value of the field totalFund; may be null.
	   */
	  public java.lang.Double getTotalFund()
	  {
	    return totalFund;
	  }

	  /**
	   * Sets the field totalFund.
	   * @param _totalFund the new value of the field totalFund.
	   */
	  public void setTotalFund(java.lang.Double _totalFund)
	  {
	    totalFund = _totalFund;
	  }

	  

	  /**
	   * Gets the field motobikeRentingFund.
	   * @return the value of the field motobikeRentingFund; may be null.
	   */
	  public java.lang.String getMotobikeRentingFund()
	  {
	    return motobikeRentingFund;
	  }

	  /**
	   * Sets the field motobikeRentingFund.
	   * @param _motobikeRentingFund the new value of the field motobikeRentingFund.
	   */
	  public void setMotobikeRentingFund(java.lang.String _motobikeRentingFund)
	  {
	    motobikeRentingFund = _motobikeRentingFund;
	  }

	  

	  /**
	   * Gets the field startDate.
	   * @return the value of the field startDate; may be null.
	   */
	  public java.util.Date getStartDate()
	  {
	    return startDate;
	  }

	  /**
	   * Sets the field startDate.
	   * @param _startDate the new value of the field startDate.
	   */
	  public void setStartDate(java.util.Date _startDate)
	  {
	    startDate = _startDate;
	  }

	 

	  /**
	   * Gets the field revenueBeforeStartDate.
	   * @return the value of the field revenueBeforeStartDate; may be null.
	   */
	  public java.lang.Double getRevenueBeforeStartDate()
	  {
	    return revenueBeforeStartDate;
	  }

	  /**
	   * Sets the field revenueBeforeStartDate.
	   * @param _revenueBeforeStartDate the new value of the field revenueBeforeStartDate.
	   */
	  public void setRevenueBeforeStartDate(java.lang.Double _revenueBeforeStartDate)
	  {
	    revenueBeforeStartDate = _revenueBeforeStartDate;
	  }

	 

	  /**
	   * Gets the field costBeforeStartDate.
	   * @return the value of the field costBeforeStartDate; may be null.
	   */
	  public java.lang.Double getCostBeforeStartDate()
	  {
	    return costBeforeStartDate;
	  }

	  /**
	   * Sets the field costBeforeStartDate.
	   * @param _costBeforeStartDate the new value of the field costBeforeStartDate.
	   */
	  public void setCostBeforeStartDate(java.lang.Double _costBeforeStartDate)
	  {
	    costBeforeStartDate = _costBeforeStartDate;
	  }

	  

	  /**
	   * Gets the field investBeforeStartDate.
	   * @return the value of the field investBeforeStartDate; may be null.
	   */
	  public java.lang.Double getInvestBeforeStartDate()
	  {
	    return investBeforeStartDate;
	  }

	  /**
	   * Sets the field investBeforeStartDate.
	   * @param _investBeforeStartDate the new value of the field investBeforeStartDate.
	   */
	  public void setInvestBeforeStartDate(java.lang.Double _investBeforeStartDate)
	  {
	    investBeforeStartDate = _investBeforeStartDate;
	  }
	  

	  /**
	   * Gets the field contracts.
	   * @return the value of the field contracts; may be null.
	   */
	  public java.util.Set<vn.com.phuclocbao.entity.Contract> getContracts()
	  {
	    return contracts;
	  }

	  /**
	   * Sets the field contracts.
	   * @param _contracts the new value of the field contracts.
	   */
	  public void setContracts(java.util.Set<vn.com.phuclocbao.entity.Contract> _contracts)
	  {
	    contracts = _contracts;
	  }
}
