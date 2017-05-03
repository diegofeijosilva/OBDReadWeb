package com.obdread.web.errosecu;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.obdread.ed.ErrosEcuED;
import com.obdread.ed.VeiculoED;
import com.obdread.errosecu.ErrosEcuRN;
import com.obdread.infra.AppListMB;
import com.obdread.veiculo.VeiculoRN;

@Named
@ViewScoped
public class ErrosEcuListMB extends AppListMB<ErrosEcuED, ErrosEcuED> {

  private static final long serialVersionUID = 1L;

  @Inject
  ErrosEcuRN                errosEcuRN;

  @Inject
  VeiculoRN                 veiculoRN;

  List<ErrosEcuED>          listaErros;
  List<VeiculoED>           listaVeiculos;
  Long                      VeiculoId;

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(errosEcuRN);

  }

  @Override
  public void init() {
    ErrosEcuED ed = new ErrosEcuED();
    // Lista os ve�culos do usu�rio logado na sess�o
    listaVeiculos = veiculoRN.listaVeiculosUsuarioLogado();

    super.init();
  }

  public void listaErrosEcuVeiculo(AjaxBehaviorEvent vce) {
    if (VeiculoId != null)
      listaErros = errosEcuRN.listaErrosEcuVeiculo(super.getPed().getVeiculoED());
    else
      listaErros = null;
  }

  @Override
  public void limpa() {
    // super.getPed().setUsaItensTela(true);
    // super.getPed().setItensSituacaoSelecionados( new
    // Integer[SituacaoCronogramaEnum.getAll().size()]);
    // super.getPed().getItensSituacaoSelecionados()[0] = 1;
    // super.getPed().getItensSituacaoSelecionados()[1] = 2;
    super.limpa();
  }

  public List<ErrosEcuED> getListaErros() {
    return listaErros;
  }

  public void setListaErros(List<ErrosEcuED> listaErros) {
    this.listaErros = listaErros;
  }

  public List<VeiculoED> getListaVeiculos() {
    return listaVeiculos;
  }

  public void setListaVeiculos(List<VeiculoED> listaVeiculos) {
    this.listaVeiculos = listaVeiculos;
  }

  public Long getVeiculoId() {
    return VeiculoId;
  }

  public void setVeiculoId(Long veiculoId) {
    VeiculoId = veiculoId;
  }

}
