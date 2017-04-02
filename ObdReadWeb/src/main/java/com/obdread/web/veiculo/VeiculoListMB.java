package com.obdread.web.veiculo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppListMB;
import com.obdread.veiculo.VeiculoRN;

@Named
@ViewScoped
public class VeiculoListMB extends AppListMB<VeiculoED, VeiculoED> {

  private static final long serialVersionUID = 1L;

  @Inject
  VeiculoRN                 veiculoRN;
  
  List<VeiculoED> listaVeiculos;

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(veiculoRN);

  }

  @Override
  public void init() {
	  
	  VeiculoED ed = new VeiculoED(); 
	  listaVeiculos = veiculoRN.lista(ed);
	  
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

public List<VeiculoED> getListaVeiculos() {
	return listaVeiculos;
}

public void setListaVeiculos(List<VeiculoED> listaVeiculos) {
	this.listaVeiculos = listaVeiculos;
}
  
  

}
