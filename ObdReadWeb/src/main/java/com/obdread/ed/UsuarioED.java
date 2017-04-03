package com.obdread.ed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.obdread.infra.AppED;

@Entity
@Table(name = "TBL_USUARIO")
@NamedQueries(value = { @NamedQuery(name = "UsuarioED.consulta", query = "SELECT C FROM UsuarioED C WHERE C.usuarioId = :id"),
                        @NamedQuery(name = "UsuarioED.findByEmailSenha", query = "SELECT c FROM UsuarioED c "+ "WHERE c.email = :email AND c.senha = :senha")})
public class UsuarioED extends AppED<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USUARIO_ID")
  private Long              usuarioId;

  @Column(name = "EMAIL")
  private String            email;

  @Column(name = "SENHA")
  private String            senha;

  @Column(name = "TICKET")
  private String            ticket;

  @Override
  public Long getId() {
    return usuarioId;
  }

  public Long getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(Long usuarioId) {
    this.usuarioId = usuarioId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

}