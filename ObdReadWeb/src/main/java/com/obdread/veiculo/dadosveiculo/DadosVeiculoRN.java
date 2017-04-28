package com.obdread.veiculo.dadosveiculo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;

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

}