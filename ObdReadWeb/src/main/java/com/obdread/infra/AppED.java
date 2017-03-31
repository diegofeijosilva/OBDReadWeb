package com.obdread.infra;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.obdread.infra.persistence.FrameworkED;
import com.obdread.infra.persistence.PropLista;

@MappedSuperclass
public abstract class AppED<PK> implements FrameworkED<PK>, Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "CTR_DTH_ATU")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar          ctrDthAtu;

  @Column(name = "CTR_DTH_INC")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar          ctrDthInc;

  @Transient
  private PropLista         propLista        = new PropLista();

  @Transient
  private boolean           delete           = false;

  public Calendar getCtrDthAtu() {
    return ctrDthAtu;
  }

  public void setCtrDthAtu(Calendar ctrDthAtu) {
    this.ctrDthAtu = ctrDthAtu;
  }

  public Calendar getCtrDthInc() {
    return ctrDthInc;
  }

  public void setCtrDthInc(Calendar ctrDthInc) {
    this.ctrDthInc = ctrDthInc;
  }

  public PropLista getPropLista() {
    return propLista;
  }

  public void setPropLista(PropLista propLista) {
    this.propLista = propLista;
  }

  public boolean isDelete() {
    return delete;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

}