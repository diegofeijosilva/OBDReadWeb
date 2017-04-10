package com.obdread.ed.enums;

/*
 * Gerador de log do ve�culo que ser� apresentado no Android e no Dashboard do sistema web.
 */
public enum LogVeiculoEnum {
  
  NECESSARIO_TROCA_DE_OLEO("REVIS�O PERI�DICA","Revisar a troca de �leo do ve�culo."),
  REVISAO_PERIODICA("REVIS�O PERI�DICA","Necess�rio levar o ve�culo a concession�ria para revis�o per�odica.")
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
