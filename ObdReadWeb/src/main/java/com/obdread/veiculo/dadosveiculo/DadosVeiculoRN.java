package com.obdread.veiculo.dadosveiculo;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.ed.rest.ObdType;
import com.obdread.exception.RNException;
import com.obdread.infra.AppRN;
import com.obdread.usuario.UsuarioRN;
import com.obdread.veiculo.VeiculoRN;

@Stateless
public class DadosVeiculoRN extends AppRN<DadosVeiculoED, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	DadosVeiculoBD bd;

	@Inject
	UsuarioRN usuarioRN;

	@Inject
	VeiculoRN veiculoRN;

	@Inject
	public void setBD(DadosVeiculoBD bd) {
		super.setBD(bd);
	}

	public DadosVeiculoED incluiDados(ObdType dados) {

		this.validaCamposEntrada(dados);
		UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(dados.getHashUser());
		VeiculoED veiculoED = veiculoRN.consulta(dados.getIdVeiculo());

		if (usuarioED == null || veiculoED == null)
			throw new RNException("Veiculo não encontrado!");

		DadosVeiculoED ed = new DadosVeiculoED();

		ed.setUsuarioED(usuarioED);
		ed.setVeiculoED(veiculoED);

		// Dados do GPS
		ed.setGpsLat(dados.getLatitude());
		ed.setGpsLong(dados.getLongitude());

		/// Dados vindos da ECU
		ed.setObdQtdeCombustivel(dados.getObdQtdeCombustivel());
		ed.setObdRpm(dados.getObdRpm());
		ed.setObdVelocidade(dados.getObdVelocidade());
		return inclui(ed);

	}

	private void validaCamposEntrada(ObdType dados) {
		if (dados.getHashUser().equals(""))
			throw new RNException("Veiculo não encontrado!");

		if (dados.getIdVeiculo().equals(0))
			throw new RNException("Veiculo não encontrado!");
	}

	public List<DadosVeiculoED> listaHistoricoGPSVeiculo(Long veiculoId, Calendar dthInicio, Calendar dthFim) {
		return bd.listaHistoricoGPSVeiculo(veiculoId, dthInicio, dthFim);
	}

	public List<DadosVeiculoED> listaHistoricoOBDVeiculo(Long veiculoId, Calendar dthAtual) {
		return bd.listaHistoricoOBDVeiculo(veiculoId, dthAtual);
	}
	
	public List<DadosVeiculoED> listaHistoricoOBDVeiculo(Long veiculoId, Calendar dthInicio, Calendar dthFim) {
		return bd.listaHistoricoOBDVeiculo(veiculoId, dthInicio, dthFim);
	}

}