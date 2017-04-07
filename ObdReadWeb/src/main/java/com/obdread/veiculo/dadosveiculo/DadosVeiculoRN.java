package com.obdread.veiculo.dadosveiculo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.infra.AppRN;

@Stateless
public class DadosVeiculoRN extends AppRN<DadosVeiculoED, Long> {

  private static final long serialVersionUID = 1L;

  @Inject
  DadosVeiculoBD            bd;

  @Inject
  public void setBD(DadosVeiculoBD bd) {
    super.setBD(bd);
  }

}