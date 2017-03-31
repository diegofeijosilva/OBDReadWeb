package com.obdread.ed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.obdread.infra.AppED;

@Entity
@Table(name = "TBL_DADOS_VEICULO")
@NamedQueries(value = { @NamedQuery(name = "DadosVeiculoED.consulta", query = "SELECT C FROM DadosVeiculoED C WHERE C.dadosVeiculoId = :id") })
public class DadosVeiculoED extends AppED<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "DADOS_VEICULO_ID")
  private Long              dadosVeiculoId;

  @ManyToOne
  @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
  private UsuarioED         usuarioED;

  @ManyToOne
  @JoinColumn(name = "VEICULO_ID", referencedColumnName = "VEICULO_ID")
  private VeiculoED         veiculoED;

  @Column(name = "GPG_LONG")
  private String            gpsLong;

  @Column(name = "GPG_LAT")
  private String            gpsLat;

  @Override
  public Long getId() {
    return dadosVeiculoId;
  }

  public Long getDadosVeiculoId() {
    return dadosVeiculoId;
  }

  public void setDadosVeiculoId(Long dadosVeiculoId) {
    this.dadosVeiculoId = dadosVeiculoId;
  }

  public UsuarioED getUsuarioED() {
    return usuarioED;
  }

  public void setUsuarioED(UsuarioED usuarioED) {
    this.usuarioED = usuarioED;
  }

  public VeiculoED getVeiculoED() {
    return veiculoED;
  }

  public void setVeiculoED(VeiculoED veiculoED) {
    this.veiculoED = veiculoED;
  }

  public String getGpsLong() {
    return gpsLong;
  }

  public void setGpsLong(String gpsLong) {
    this.gpsLong = gpsLong;
  }

  public String getGpsLat() {
    return gpsLat;
  }

  public void setGpsLat(String gpsLat) {
    this.gpsLat = gpsLat;
  }

}