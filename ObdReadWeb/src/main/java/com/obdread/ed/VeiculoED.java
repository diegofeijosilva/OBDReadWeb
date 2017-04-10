package com.obdread.ed;

import java.io.Serializable;
import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.obdread.infra.AppED;

@Entity
@Table(name = "TBL_VEICULO")
@NamedQueries(value = { @NamedQuery(name = "VeiculoED.consulta", query = "SELECT C FROM VeiculoED C WHERE C.veiculoID = :id") })
public class VeiculoED extends AppED<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "VEICULO_ID")
  private Long              veiculoID;

  @ManyToOne
  @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
  private UsuarioED         usuarioED;

  @Column(name = "NOME")
  private String            nome;
  
  @Column(name = "MODELO")
  private String            modelo;
  
  @Column(name = "ANO_FABRICACAO")
  private String            anoFabricacao;
  
  @Column(name = "DTH_ULT_TROCA_OLEO")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar          dthUltTrocaOleo;
  
  @Column(name = "DTH_ULT_REVISAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar          dthUltRevisao;

  @Override
  public Long getId() {
    return veiculoID;
  }

  public Long getVeiculoID() {
    return veiculoID;
  }

  public void setVeiculoID(Long veiculoID) {
    this.veiculoID = veiculoID;
  }

  public UsuarioED getUsuarioED() {
    return usuarioED;
  }

  public void setUsuarioED(UsuarioED usuarioED) {
    this.usuarioED = usuarioED;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
  
  

}