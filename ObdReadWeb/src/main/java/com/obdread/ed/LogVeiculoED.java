package com.obdread.ed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.obdread.infra.AppED;

@Entity
@Table(name = "TBL_LOG_VEICULO")
@NamedQueries(value = { @NamedQuery(name = "LogVeiculoED.consulta", query = "SELECT C FROM LogVeiculoED C WHERE C.logVeiculoID = :id") })
public class LogVeiculoED extends AppED<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LOG_VEICULO_ID")
  private Long              logVeiculoID;

  @ManyToOne
  @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
  private UsuarioED         usuarioED;

  @ManyToOne
  @JoinColumn(name = "VEICULO_ID", referencedColumnName = "VEICULO_ID")
  private VeiculoED         veiculoED;

  @Column(name = "TIPO")
  private String            tipoLog;

  @Column(name = "DESCRICAO")
  private String            descricao;

  @Override
  public Long getId() {
    return logVeiculoID;
  }

  public Long getLogVeiculoID() {
    return logVeiculoID;
  }

  public void setLogVeiculoID(Long logVeiculoID) {
    this.logVeiculoID = logVeiculoID;
  }

  public VeiculoED getVeiculoED() {
    return veiculoED;
  }

  public void setVeiculoED(VeiculoED veiculoED) {
    this.veiculoED = veiculoED;
  }

  public String getTipoLog() {
    return tipoLog;
  }

  public void setTipoLog(String tipoLog) {
    this.tipoLog = tipoLog;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public UsuarioED getUsuarioED() {
    return usuarioED;
  }

  public void setUsuarioED(UsuarioED usuarioED) {
    this.usuarioED = usuarioED;
  }

}