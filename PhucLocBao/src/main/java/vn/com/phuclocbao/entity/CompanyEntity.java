package vn.com.phuclocbao.entity;

import java.io.Serializable;

import javax.persistence.Entity;
@Entity
@javax.persistence.Table(name="tblCompany")
public class CompanyEntity implements Serializable
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 4928075932970402493L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue
  private java.lang.Integer id;

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

  @javax.persistence.Column(nullable=false, length=255)
  private java.lang.String name;

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

  @javax.persistence.Column(length=255)
  private java.lang.String address;

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

  @javax.persistence.Column(length=255)
  private java.lang.String description;

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

  @javax.persistence.OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER, mappedBy="companyEntity", orphanRemoval=false)
  private java.util.Set<vn.com.phuclocbao.entity.UserAccount> userAccounts;

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

  @javax.persistence.ManyToOne(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER)
  @javax.persistence.JoinColumn(name="companytype")
  private vn.com.phuclocbao.entity.CompanyType type;

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

}
