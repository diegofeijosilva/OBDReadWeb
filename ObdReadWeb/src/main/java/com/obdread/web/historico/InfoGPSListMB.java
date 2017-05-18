package com.obdread.web.historico;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppListMB;
import com.obdread.veiculo.VeiculoRN;
import com.obdread.veiculo.dadosveiculo.DadosVeiculoRN;

@Named
@ViewScoped
public class InfoGPSListMB extends AppListMB<DadosVeiculoED, DadosVeiculoED> {

  private static final long    serialVersionUID = 1L;

  @Inject
  DadosVeiculoRN               infoVeiRN;

  @Inject
  VeiculoRN                    veiculoRN;

  private MapModel             simpleModel;
  private List<VeiculoED>      listaVeiculos;
  private Long                 veiculoId;
  private Calendar             dthInicio        = Calendar.getInstance();
  private Calendar             dthFim           = Calendar.getInstance();

  private List<DadosVeiculoED> listaHistGPS;

  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(infoVeiRN);

  }

  @Override
  public void init() {

    DadosVeiculoED ed = new DadosVeiculoED();

    // Lista os veículos do usuário logado na sessão
    listaVeiculos = veiculoRN.listaVeiculosUsuarioLogado();

    super.init();

    //Shared coordinates 
    //    LatLng coord1 = new LatLng(-30.074238, -51.016985);
    //    LatLng coord2 = new LatLng(-30.075517, -51.022567);
    //    LatLng coord3 = new LatLng(-30.079782, -51.022004);
    //    LatLng coord4 = new LatLng(-30.078582, -51.017182);
    //
    //    //Basic marker
    //    simpleModel.addOverlay(new Marker(coord1));
    //    simpleModel.addOverlay(new Marker(coord2));
    //    simpleModel.addOverlay(new Marker(coord3));
    //    simpleModel.addOverlay(new Marker(coord4));
  }

  @Override
  public void limpa() {
    //    super.getPed().setUsaItensTela(true);
    //    super.getPed().setItensSituacaoSelecionados( new Integer[SituacaoCronogramaEnum.getAll().size()]);   
    //    super.getPed().getItensSituacaoSelecionados()[0] = 1;
    //    super.getPed().getItensSituacaoSelecionados()[1] = 2;
    super.limpa();
  }

  public void listaHistoricoGPSVeiculo() {
    if (veiculoId != null) {
      listaHistGPS = infoVeiRN.listaHistoricoGPSVeiculo(veiculoId, dthInicio, dthFim);

      if (listaHistGPS != null && !listaHistGPS.isEmpty()) {
        simpleModel = new DefaultMapModel();

        /// Percorre a lista do histórico adicionando os pontos no mapa
        for (DadosVeiculoED ed : listaHistGPS) {
          LatLng coord1 = new LatLng(Double.parseDouble(ed.getGpsLat()), Double.parseDouble(ed.getGpsLong()));
          simpleModel.addOverlay(new Marker(coord1));
        }

      }

    } else
      listaHistGPS = null;
  }

  public MapModel getSimpleModel() {
    return simpleModel;
  }

  public void setSimpleModel(MapModel simpleModel) {
    this.simpleModel = simpleModel;
  }

  public List<VeiculoED> getListaVeiculos() {
    return listaVeiculos;
  }

  public void setListaVeiculos(List<VeiculoED> listaVeiculos) {
    this.listaVeiculos = listaVeiculos;
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

  public Long getVeiculoId() {
    return veiculoId;
  }

  public void setVeiculoId(Long veiculoId) {
    this.veiculoId = veiculoId;
  }

}
