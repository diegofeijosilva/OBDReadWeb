package com.obdread.logveiculo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.LogVeiculoED;
import com.obdread.ed.UsuarioED;
import com.obdread.exception.RNException;
import com.obdread.infra.AppRN;

@Stateless
public class LogVeiculoRN extends AppRN<LogVeiculoED, Long> {

  private static final long serialVersionUID = 1L;

  @Inject
  LogVeiculoBD              bd;

  @Inject
  public void setBD(LogVeiculoBD bd) {
    super.setBD(bd);
  }

  public List<LogVeiculoED> listaLogUsusario(UsuarioED usuarioED) {
    if (usuarioED == null)
      throw new RNException(this.getClass() + ": Informar o usuário");
    return bd.listaLogUsusario(usuarioED);
  }

  //  @Override
  //  public VeiculoED inclui(LogVeiculoED ed) {
  //    ed.setNome(ed.getNome().toUpperCase());
  //    return super.inclui(ed);
  //  }
  //
  //  @Override
  //  public VeiculoED altera(VeiculoED ed) {
  //    ed.setNome(ed.getNome().toUpperCase());
  //    return super.altera(ed);
  //  }
  //
  //  public List<VeiculoED> listaVeiculosUsusario (UsuarioED usuarioED) {
  //    if(usuarioED == null)
  //      throw new RNException(this.getClass()+": Informar o usuário");
  //    return bd.listaVeiculosUsusario(usuarioED);
  //  }
}