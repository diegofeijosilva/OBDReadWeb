package com.obdread.web.historico;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppListMB;
import com.obdread.infra.FacesUtil;
import com.obdread.util.UtilRN;
import com.obdread.veiculo.VeiculoRN;
import com.obdread.veiculo.dadosveiculo.DadosVeiculoRN;

@Named
@ViewScoped
public class InfoOBDListMB extends AppListMB<DadosVeiculoED, DadosVeiculoED> {

	private static final long serialVersionUID = 1L;

	@Inject
	DadosVeiculoRN infoVeiRN;

	@Inject
	VeiculoRN veiculoRN;

	private MapModel simpleModel;
	private List<VeiculoED> listaVeiculos;
	private Long veiculoId;
	private Calendar dthInicio = Calendar.getInstance();

	private List<DadosVeiculoED> listaHistOBD;

	private LineChartModel lineModelVelocidade;
	private LineChartModel lineModelRPM;
	private MeterGaugeChartModel meterGaugeModel1;

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

		// //// Pega o primeiro veículo da lista
		// if (listaVeiculos != null || listaVeiculos.size() > 0) {
		// if (veiculoED == null) {
		// veiculoED = listaVeiculos.get(0);
		// }
		// }

		//listaHistoricoGPSVeiculo();

		createLineModels();

		createMeterGaugeModels();

		super.init();
	}

	/// Inicia o gráfico de velocidade
	private void createLineModels() {
		lineModelVelocidade = initLinearModelVelocidade();
		lineModelVelocidade.setTitle("Gráfico Velocidade");
		lineModelVelocidade.setAnimate(true);
		lineModelVelocidade.setLegendPosition("e");
		lineModelVelocidade.getAxes().put(AxisType.X, new CategoryAxis(""));
		Axis yAxis = lineModelVelocidade.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(220);

		lineModelRPM = initCategoryModel();
		lineModelRPM.setTitle("Gráfico RPM");
		lineModelRPM.setAnimate(true);
		lineModelRPM.setLegendPosition("e");

		// lineModelRPM.setTitle("Category Chart");
		lineModelRPM.setLegendPosition("e");
		lineModelRPM.setShowPointLabels(true);
		lineModelRPM.getAxes().put(AxisType.X, new CategoryAxis(""));
		yAxis = lineModelRPM.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(9000);
		// yAxis.setLabel("Births");

	}

	private LineChartModel initLinearModelVelocidade() {

		LineChartModel model = new LineChartModel();

		ChartSeries series1 = new ChartSeries();
		series1.setLabel("Km/h");

		if (listaHistOBD != null) {
			for (DadosVeiculoED ed : listaHistOBD) {
				series1.set(UtilRN.retornaHHMM(ed.getCtrDthInc()), ed.getObdVelocidade());
			}

			model.addSeries(series1);
		}
		return model;

		// LineChartModel model = new LineChartModel();
		//
		// LineChartSeries series1 = new LineChartSeries();
		// //series1.setLabel("Velocidade");
		//
		// series1.set(1,1000);
		// series1.set(2, 8500);
		// series1.set(3, 3500);
		// series1.set(4, 6900);
		// series1.set(5, 8000);
		//
		//
		// model.addSeries(series1);
		//
		// return model;
	}

	private LineChartModel initCategoryModel() {

		LineChartModel model = new LineChartModel();

		ChartSeries series1 = new ChartSeries();
		series1.setLabel("RPM");

		if (listaHistOBD != null) {
			for (DadosVeiculoED ed : listaHistOBD) {
				series1.set(UtilRN.retornaHHMM(ed.getCtrDthInc()), ed.getObdRpm());
			}

			model.addSeries(series1);
		}
		return model;
	}

	////////////// GAUGE
	public MeterGaugeChartModel getMeterGaugeModel1() {
		return meterGaugeModel1;
	}

	private MeterGaugeChartModel initMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>() {
			{
				add(10);
				add(50);
				add(100);
			}
		};

		Long media = new Long(0);
		if (listaHistOBD != null) {
			for (DadosVeiculoED ed : listaHistOBD) {
				media = media + ed.getObdQtdeCombustivel();
			}
		}

		if (media > 0)
			media = media / listaHistOBD.size();

		return new MeterGaugeChartModel(Math.round(media), intervals);
	}

	private void createMeterGaugeModels() {
		meterGaugeModel1 = initMeterGaugeModel();
		// meterGaugeModel1.setTitle("Consumo Médio");
		meterGaugeModel1.setGaugeLabel("Consumo médio");
	}

	public void listaHistoricoGPSVeiculo() {

		if (veiculoId != null) {
			listaHistOBD = infoVeiRN.listaHistoricoOBDVeiculo(veiculoId, dthInicio);

			if (listaHistOBD != null && !listaHistOBD.isEmpty()) {

			} else {
				FacesUtil.addInfoMessage("Nenhum Registro de OBD Encontrado.");
			}

		} else {
			listaHistOBD = null;
			// FacesUtil.addInfoMessage("Nenhum Registro de GPS Encontrado.");
		}
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

	public Long getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}

	public List<DadosVeiculoED> getListaHistOBD() {
		return listaHistOBD;
	}

	public void setListaHistOBD(List<DadosVeiculoED> listaHistOBD) {
		this.listaHistOBD = listaHistOBD;
	}

	public LineChartModel getLineModelVelocidade() {
		return lineModelVelocidade;
	}

	public void setLineModelVelocidade(LineChartModel lineModelVelocidade) {
		this.lineModelVelocidade = lineModelVelocidade;
	}

	public LineChartModel getLineModelRPM() {
		return lineModelRPM;
	}

	public void setLineModelRPM(LineChartModel lineModelRPM) {
		this.lineModelRPM = lineModelRPM;
	}

	public void setMeterGaugeModel1(MeterGaugeChartModel meterGaugeModel1) {
		this.meterGaugeModel1 = meterGaugeModel1;
	}

}
