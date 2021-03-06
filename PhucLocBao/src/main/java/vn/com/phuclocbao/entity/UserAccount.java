package vn.com.phuclocbao.entity;

import javax.persistence.CascadeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import vn.com.phuclocbao.entity.base.IBaseEntity;

@javax.persistence.Entity
@javax.persistence.Table(name="tblUser")
@NamedQueries({
	@NamedQuery(name = "getUserByUsername", query = "SELECT user FROM UserAccount user WHERE user.username = :username"),
	@NamedQuery(name = "userAccount_deleteByCompanyId", query = "DELETE FROM UserAccount ua WHERE ua.companyEntity.id = :companyId"),
})

public class UserAccount implements IBaseEntity {
  /** SerialVersionUID */
  private static final long serialVersionUID = 8710777840096998292L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue
  private java.lang.Integer id;
  @javax.persistence.Column(length=1)
  private String isAdmin;
  @javax.persistence.Column(length=30)
  private java.lang.String username;
  @javax.persistence.Column(length=100)
  private java.lang.String password;
  @javax.persistence.Column(length=255)
  private java.lang.String fullname;
  @javax.persistence.Column(length=255)
  private java.lang.String email;
  @javax.persistence.ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER)
  private vn.com.phuclocbao.entity.CompanyEntity companyEntity;
  @javax.persistence.Column(length=45)
  private java.lang.String state;
  
  public String getIsAdmin() {
	return isAdmin;
  }
	
  public java.lang.String getState() {
	return state;
}


public void setState(java.lang.String state) {
	this.state = state;
}


public void setIsAdmin(String isAdmin) {
	this.isAdmin = isAdmin;
  }

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

  

  /**
   * Gets the field username.
   * @return the value of the field username; may be null.
   */
  public java.lang.String getUsername()
  {
    return username;
  }

  /**
   * Sets the field username.
   * @param _username the new value of the field username.
   */
  public void setUsername(java.lang.String _username)
  {
    username = _username;
  }

  

  /**
   * Gets the field password.
   * @return the value of the field password; may be null.
   */
  public java.lang.String getPassword()
  {
    return password;
  }

  /**
   * Sets the field password.
   * @param _password the new value of the field password.
   */
  public void setPassword(java.lang.String _password)
  {
    password = _password;
  }

  

  /**
   * Gets the field fullname.
   * @return the value of the field fullname; may be null.
   */
  public java.lang.String getFullname()
  {
    return fullname;
  }

  /**
   * Sets the field fullname.
   * @param _fullname the new value of the field fullname.
   */
  public void setFullname(java.lang.String _fullname)
  {
    fullname = _fullname;
  }

  

  /**
   * Gets the field email.
   * @return the value of the field email; may be null.
   */
  public java.lang.String getEmail()
  {
    return email;
  }

  /**
   * Sets the field email.
   * @param _email the new value of the field email.
   */
  public void setEmail(java.lang.String _email)
  {
    email = _email;
  }

  

  /**
   * Gets the field companyEntity.
   * @return the value of the field companyEntity; may be null.
   */
  public vn.com.phuclocbao.entity.CompanyEntity getCompanyEntity()
  {
    return companyEntity;
  }

  /**
   * Sets the field companyEntity.
   * @param _companyEntity the new value of the field companyEntity.
   */
  public void setCompanyEntity(vn.com.phuclocbao.entity.CompanyEntity _companyEntity)
  {
    companyEntity = _companyEntity;
  }

}