package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;

public class UserAccountDto implements IBaseDTO{
	  /**
	 * 
	 */
	private static final long serialVersionUID = -8883117033212035998L;
	/**
	   * Identifier
	   */
	  private java.lang.Integer id;
	  private String isAdmin;
	  private java.lang.String state;
	  
	  
	  public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public String getIsAdmin() {
		return isAdmin;
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

	  private java.lang.String username;

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

	  private java.lang.String password;

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

	  private java.lang.String fullname;

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

	  private java.lang.String email;

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

	  public CompanyDto getCompanyEntity() {
		return companyEntity;
	}

	public void setCompanyEntity(CompanyDto companyEntity) {
		this.companyEntity = companyEntity;
	}

	private CompanyDto companyEntity;

	 
 
}
