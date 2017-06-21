package com.obdread.web.relatorio;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppListMB;
import com.obdread.veiculo.VeiculoRN;
import com.obdread.veiculo.dadosveiculo.DadosVeiculoRN;

@Named
@ViewScoped
public class RelObdListMB extends AppListMB<DadosVeiculoED, DadosVeiculoED> {

	private static final long serialVersionUID = 1L;

	@Inject
	DadosVeiculoRN dadosVeiculoRN;

	@Inject
	VeiculoRN veiculoRN;

	private List<DadosVeiculoED> listaDadosOBD;
	private List<VeiculoED> listaVeiculos;
	private Long veiculoId;
	private Calendar dthInicio = Calendar.getInstance();
	private Calendar dthFim = Calendar.getInstance();

	@PostConstruct
	void initRN() {
		super.setRN(dadosVeiculoRN);
	}

	@Override
	public void init() {
		DadosVeiculoED ed = new DadosVeiculoED();
		// Lista os veículos do usuário logado na sessão
		listaVeiculos = veiculoRN.listaVeiculosUsuarioLogado();

		super.init();
	}

	public void listaDadosObdVeiculo() {
		if (veiculoId != null)
			listaDadosOBD = dadosVeiculoRN.listaHistoricoOBDVeiculo(veiculoId, dthInicio, dthFim);
		else
			listaDadosOBD = null;
	}

	public List<DadosVeiculoED> getListaDadosOBD() {
		return listaDadosOBD;
	}

	public void setListaDadosOBD(List<DadosVeiculoED> listaDadosOBD) {
		this.listaDadosOBD = listaDadosOBD;
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
