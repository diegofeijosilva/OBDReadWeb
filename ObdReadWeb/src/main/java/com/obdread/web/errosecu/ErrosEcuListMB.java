package com.obdread.web.errosecu;

import java.util.Calendar;
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

  private List<ErrosEcuED>  listaErros;
  private List<VeiculoED>   listaVeiculos;
  private Long              veiculoId;
  private Calendar          dthInicio = Calendar.getInstance();
  private Calendar          dthFim = Calendar.getInstance();

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(errosEcuRN);

  }

  @Override
  public void init() {
    ErrosEcuED ed = new ErrosEcuED();
    // Lista os veículos do usuário logado na sessão
    listaVeiculos = veiculoRN.listaVeiculosUsuarioLogado();

    super.init();
  }

  public void listaErrosEcuVeiculo() {
    if (veiculoId != null)
      listaErros = errosEcuRN.listaErrosEcuVeiculo(veiculoId, dthInicio, dthFim);
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
    return veiculoId;
  }

  public void setVeiculoId(Long veiculoId) {
    this.veiculoId = veiculoId;
  }

  public Calendar getDthInicio() {
    return dthInicio;
  }

  public void setDthInicio(Calendar dthInicio) {
    this.dthInicio = dthInicio;
  }

  public Calendar getDthFim() {
    return dthFim;
  }

  public void setDthFim(Calendar dthFim) {
    this.dthFim = dthFim;
  }

}
