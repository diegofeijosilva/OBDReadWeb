package com.obdread.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.obdread.ed.UsuarioED;
import com.obdread.infra.AppListMB;
import com.obdread.usuario.UsuarioRN;

@Named
@ViewScoped
public class UsuarioListMB extends AppListMB<UsuarioED, UsuarioED> {

  private static final long serialVersionUID = 1L;

  @Inject
  UsuarioRN                 usuarioRN;

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(usuarioRN);

  }

  @Override
  public void init() {
    super.init();
  }

  @Override
  public void limpa() {
    //    super.getPed().setUsaItensTela(true);
    //    super.getPed().setItensSituacaoSelecionados( new Integer[SituacaoCronogramaEnum.getAll().size()]);   
    //    super.getPed().getItensSituacaoSelecionados()[0] = 1;
    //    super.getPed().getItensSituacaoSelecionados()[1] = 2;
    super.limpa();
  }

}
