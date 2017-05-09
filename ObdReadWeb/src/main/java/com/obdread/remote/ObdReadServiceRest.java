package com.obdread.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.obdread.ed.LogVeiculoED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.ed.rest.ErrosEcuType;
import com.obdread.ed.rest.LogVeiculoTypeED;
import com.obdread.ed.rest.ObdType;
import com.obdread.ed.rest.UsuarioType;
import com.obdread.ed.rest.VeiculoTypeED;
import com.obdread.errosecu.ErrosEcuRN;
import com.obdread.exception.RNException;
import com.obdread.logveiculo.LogVeiculoRN;
import com.obdread.usuario.UsuarioRN;
import com.obdread.veiculo.VeiculoRN;
import com.obdread.veiculo.dadosveiculo.DadosVeiculoRN;

@Path("/ObdService")
@Produces(MediaType.APPLICATION_JSON)
@AccessTimeout(value = 60, unit = TimeUnit.SECONDS)
public class ObdReadServiceRest {

	@Inject
	UsuarioRN usuarioRN;

	@Inject
	VeiculoRN veiculoRN;

	@Inject
	LogVeiculoRN logVeiculoRN;

	@Inject
	DadosVeiculoRN dadosVeiculoRN;

	@Inject
	ErrosEcuRN errosEcuRN;

	/**
	 * Método para verificar se serviço está respondendo
	 * 
	 * @return Data + Hora atual
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response status() {
		return Response.ok("OK").build();
	}

	/**
	 * Recebe os dados vindos da aplicação Android
	 * 
	 * @responseMessage 403 erro Usuario não autorizado
	 * @status 404 Transacao não encontrada!
	 * @status 500 Erro interno -
	 */
	@POST
	@Path("/recebeDados")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recebeDados(ObdType dados) {
		dadosVeiculoRN.incluiDados(dados);
		return Response.ok("OK").build();
		// return Response.ok(, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Recebe os erros da ECU vindos da aplicação Android
	 * 
	 * @responseMessage 403 erro Usuario não autorizado
	 * @status 404 Transacao não encontrada!
	 * @status 500 Erro interno -
	 */
	@POST
	@Path("/recebeErrosEcu")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recebeErrosEcu(ErrosEcuType dados) {
		errosEcuRN.inclui(dados);
		return Response.ok("OK").build();
		// return Response.ok(, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Lista os veículos do Usuário
	 * 
	 * @responseMessage 403 erro Usuario não autorizado
	 * @status 404 Transacao não encontrada!
	 * @status 500 Erro interno -
	 */
	@POST
	@Path("/listaVeiculos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VeiculoTypeED> consulta(UsuarioType user) {

		List<VeiculoTypeED> listaVeiculoType = new ArrayList<VeiculoTypeED>();
		UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(user.getTicket());

		if (usuarioED == null)
			return listaVeiculoType;

		
		for (VeiculoED ed : veiculoRN.listaVeiculosUsusario(usuarioED)) {
			VeiculoTypeED veiEd = new VeiculoTypeED();
			veiEd.setId(ed.getId());
			veiEd.setNome(ed.getNome());

			listaVeiculoType.add(veiEd);
		}

		return listaVeiculoType;
	}

	/**
	 * Lista os logs do(s) veiculo(s) do Usuário
	 * 
	 * @responseMessage 403 erro Usuario não autorizado
	 * @status 404 Transacao não encontrada!
	 * @status 500 Erro interno -
	 */
	@GET
	@Path("/listaLogsVeiculos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LogVeiculoTypeED> consultaLogsVeiculos(@HeaderParam("hashUser") String hashUser) {

		UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(hashUser);

		if (usuarioED == null)
			throw new RNException("Dados Inválidos!");

		List<LogVeiculoTypeED> listaLogs = new ArrayList<LogVeiculoTypeED>();

		for (LogVeiculoED ed : logVeiculoRN.listaLogUsusario(usuarioED)) {
			LogVeiculoTypeED edLog = new LogVeiculoTypeED();

			edLog.setIdVeiculo(ed.getId());
			edLog.setTipo(ed.getTipoLog());
			edLog.setDescricao(ed.getDescricao());

			listaLogs.add(edLog);
		}

		return listaLogs;
	}
	
	/**
	 * Login para o sistema mobile
	 * 
	 * @responseMessage 403 erro Usuario não autorizado
	 * @status 404 Transacao não encontrada!
	 * @status 500 Erro interno -
	 */
	/**
	 * @param email
	 * @param senha
	 * @return
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioType login(UsuarioType user) {
		
		if(user == null )
			return new UsuarioType();

		UsuarioED usuarioED = usuarioRN.isUsuarioReadyToLogin(user.getEmail(), user.getSenha());

		if (usuarioED != null){
			
			UsuarioType userRet = new UsuarioType();
			userRet.setEmail(usuarioED.getEmail());
			userRet.setTicket(usuarioED.getTicket());
			
			return userRet;
		} else {
			return new UsuarioType();
		}

	}

}
