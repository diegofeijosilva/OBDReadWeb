package com.obdread.veiculo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppRN;

@Stateless
public class VeiculoRN extends AppRN<VeiculoED, Long> {

  private static final long serialVersionUID = 1L;

  @Inject
  VeiculoBD                 bd;

  @Inject
  public void setBD(VeiculoBD bd) {
    super.setBD(bd);
  }

  @Override
  public VeiculoED inclui(VeiculoED ed) {
    ed.setNome(ed.getNome().toUpperCase());
    return super.inclui(ed);
  }

  @Override
  public VeiculoED altera(VeiculoED ed) {
    ed.setNome(ed.getNome().toUpperCase());
    return super.altera(ed);
  }

  public List<VeiculoED> listaVeiculosUsusario (UsuarioED usuarioED) {
    return bd.listaVeiculosUsusario(usuarioED);
  }
}