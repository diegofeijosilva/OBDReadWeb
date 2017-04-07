package com.obdread.ed.rest;

import java.io.Serializable;

/**
 * Classe utilizada para receber os dados vindos do android atrav�s do servi�o Rest
 * 
 * @author diego-silva
 *
 */

public class VeiculoTypeED implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9186535305608152233L;

  /// Hash do usu�rio
  private Long              idVeiculo;
  private String            nome;

  public Long getIdVeiculo() {
    return idVeiculo;
  }

  public void setIdVeiculo(Long idVeiculo) {
    this.idVeiculo = idVeiculo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

}
