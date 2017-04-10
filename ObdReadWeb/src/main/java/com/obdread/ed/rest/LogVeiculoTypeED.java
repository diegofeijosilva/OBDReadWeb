package com.obdread.ed.rest;

import java.io.Serializable;

/**
 * 
 * 
 * @author diego-silva
 *
 */

public class LogVeiculoTypeED implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9186535305608152233L;

  private Long              idVeiculo;
  private String            tipo;
  private String            descricao;

  public Long getIdVeiculo() {
    return idVeiculo;
  }

  public void setIdVeiculo(Long idVeiculo) {
    this.idVeiculo = idVeiculo;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
