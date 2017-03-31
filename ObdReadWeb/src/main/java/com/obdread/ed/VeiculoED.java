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
@Table(name = "TBL_VEICULO")
@NamedQueries(value = { @NamedQuery(name = "VeiculoED.consulta", query = "SELECT C FROM VeiculoED C WHERE C.veiculoID = :id") })
public class VeiculoED extends AppED<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "VEICULO_ID")
  private Long              veiculoID;

  @ManyToOne
  @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
  private UsuarioED         usuarioED;

  @Column(name = "NOME")
  private String            nome;

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