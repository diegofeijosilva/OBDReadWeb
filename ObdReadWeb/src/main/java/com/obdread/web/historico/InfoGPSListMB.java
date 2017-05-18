package com.obdread.web.historico;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.infra.AppListMB;
import com.obdread.veiculo.dadosveiculo.DadosVeiculoRN;

@Named
@ViewScoped
public class InfoGPSListMB extends AppListMB<DadosVeiculoED, DadosVeiculoED> {

  private static final long serialVersionUID = 1L;

  @Inject
  DadosVeiculoRN infoVeiRN;
  
  private MapModel simpleModel;

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(infoVeiRN);

  }

  @Override
  public void init() {
	  
	  DadosVeiculoED ed = new DadosVeiculoED(); 
	//  listaVeiculos = veiculoRN.listaVeiculosUsuarioLogado();
	  
    super.init();
    
    simpleModel = new DefaultMapModel();
    
    //Shared coordinates 
    LatLng coord1 = new LatLng(-30.074238, -51.016985);
    LatLng coord2 = new LatLng(-30.075517, -51.022567);
    LatLng coord3 = new LatLng(-30.079782, -51.022004);
    LatLng coord4 = new LatLng(-30.078582, -51.017182);
    
    
  //Basic marker
    simpleModel.addOverlay(new Marker(coord1, "Konyaalti"));
    simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
    simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
    simpleModel.addOverlay(new Marker(coord4, "Kaleici"));
  }

  @Override
  public void limpa() {
    //    super.getPed().setUsaItensTela(true);
    //    super.getPed().setItensSituacaoSelecionados( new Integer[SituacaoCronogramaEnum.getAll().size()]);   
    //    super.getPed().getItensSituacaoSelecionados()[0] = 1;
    //    super.getPed().getItensSituacaoSelecionados()[1] = 2;
    super.limpa();
  }

public MapModel getSimpleModel() {
	return simpleModel;
}

public void setSimpleModel(MapModel simpleModel) {
	this.simpleModel = simpleModel;
}


  
  

}
