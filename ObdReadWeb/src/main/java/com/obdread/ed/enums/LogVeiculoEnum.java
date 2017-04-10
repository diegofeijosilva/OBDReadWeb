package com.obdread.ed.enums;

/*
 * Gerador de log do veículo que será apresentado no Android e no Dashboard do sistema web.
 */
public enum LogVeiculoEnum {
  
  NECESSARIO_TROCA_DE_OLEO("REVISÃO PERIÓDICA","Revisar a troca de óleo do veículo."),
  REVISAO_PERIODICA("REVISÃO PERIÓDICA","Necessário levar o veículo a concessionária para revisão períodica.")
  ;
  
  private final String codigoErro;
  private final String mensagemErro;
  
  private LogVeiculoEnum(String codigoErroParam, String mensagemErroParam) {
    this.codigoErro = codigoErroParam;
    this.mensagemErro = mensagemErroParam;
  }
  
  public String getCodigoErro() {
    return codigoErro;
  }
  
  public String getMensagemErro() {
    return mensagemErro;
  }

}
