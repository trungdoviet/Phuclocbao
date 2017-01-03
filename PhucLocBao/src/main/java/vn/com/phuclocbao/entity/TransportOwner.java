package vn.com.phuclocbao.entity;

import javax.persistence.GenerationType;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@javax.persistence.Entity
@javax.persistence.Table(name="tblTransportOwner")
public class TransportOwner implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -1191660338529349750L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
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

  @javax.persistence.Column(length=255)
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

  private java.lang.String transportType;

  /**
   * Gets the field transportType.
   * @return the value of the field transportType; may be null.
   */
  public java.lang.String getTransportType()
  {
    return transportType;
  }

  /**
   * Sets the field transportType.
   * @param _transportType the new value of the field transportType.
   */
  public void setTransportType(java.lang.String _transportType)
  {
    transportType = _transportType;
  }

  private java.lang.String numberPlate;

  /**
   * Gets the field numberPlate.
   * @return the value of the field numberPlate; may be null.
   */
  public java.lang.String getNumberPlate()
  {
    return numberPlate;
  }

  /**
   * Sets the field numberPlate.
   * @param _numberPlate the new value of the field numberPlate.
   */
  public void setNumberPlate(java.lang.String _numberPlate)
  {
    numberPlate = _numberPlate;
  }

  private java.lang.String chassisNumber;

  /**
   * Gets the field chassisNumber.
   * @return the value of the field chassisNumber; may be null.
   */
  public java.lang.String getChassisNumber()
  {
    return chassisNumber;
  }

  /**
   * Sets the field chassisNumber.
   * @param _chassisNumber the new value of the field chassisNumber.
   */
  public void setChassisNumber(java.lang.String _chassisNumber)
  {
    chassisNumber = _chassisNumber;
  }

  private java.lang.String chassisFrameNumber;

  /**
   * Gets the field chassisFrameNumber.
   * @return the value of the field chassisFrameNumber; may be null.
   */
  public java.lang.String getChassisFrameNumber()
  {
    return chassisFrameNumber;
  }

  /**
   * Sets the field chassisFrameNumber.
   * @param _chassisFrameNumber the new value of the field chassisFrameNumber.
   */
  public void setChassisFrameNumber(java.lang.String _chassisFrameNumber)
  {
    chassisFrameNumber = _chassisFrameNumber;
  }

  private java.lang.String detail;

  /**
   * Gets the field detail.
   * @return the value of the field detail; may be null.
   */
  public java.lang.String getDetail()
  {
    return detail;
  }

  /**
   * Sets the field detail.
   * @param _detail the new value of the field detail.
   */
  public void setDetail(java.lang.String _detail)
  {
    detail = _detail;
  }

  @javax.persistence.OneToOne(cascade={javax.persistence.CascadeType.ALL}, fetch=javax.persistence.FetchType.EAGER, orphanRemoval=false)
  private vn.com.phuclocbao.entity.Contract contract;

  /**
   * Gets the field contract.
   * @return the value of the field contract; may be null.
   */
  public vn.com.phuclocbao.entity.Contract getContract()
  {
    return contract;
  }

  /**
   * Sets the field contract.
   * @param _contract the new value of the field contract.
   */
  public void setContract(vn.com.phuclocbao.entity.Contract _contract)
  {
    contract = _contract;
  }

}
