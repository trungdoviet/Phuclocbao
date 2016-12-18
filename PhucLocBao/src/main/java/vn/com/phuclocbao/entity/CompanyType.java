package vn.com.phuclocbao.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.Table(name="tblCompanyType")
public class CompanyType implements Serializable
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 4464142490188581885L;

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

  @javax.persistence.Column(length=100)
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

  @javax.persistence.OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER, mappedBy="type", orphanRemoval=false)
  private java.util.Set<vn.com.phuclocbao.entity.CompanyEntity> companies;

  /**
   * Gets the field companies.
   * @return the value of the field companies; may be null.
   */
  public java.util.Set<vn.com.phuclocbao.entity.CompanyEntity> getCompanies()
  {
    return companies;
  }

  /**
   * Sets the field companies.
   * @param _companies the new value of the field companies.
   */
  public void setCompanies(java.util.Set<vn.com.phuclocbao.entity.CompanyEntity> _companies)
  {
    companies = _companies;
  }

}
