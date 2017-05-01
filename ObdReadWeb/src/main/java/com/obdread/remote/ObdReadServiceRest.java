package com.obdread.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;

import com.obdread.ed.LogVeiculoED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.ed.rest.ErrosEcuType;
import com.obdread.ed.rest.LogVeiculoTypeED;
import com.obdread.ed.rest.ObdType;
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
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response status() {
		String dataHoraAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
		return Response.ok("[" + dataHoraAtual + "] - Serviço Online.").build();
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
	@GET
	@Path("/listaVeiculos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VeiculoTypeED> consulta(@HeaderParam("hashUser") String hashUser) {

		UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(hashUser);

		if (usuarioED == null)
			throw new RNException("Dados Inválidos!");

		List<VeiculoTypeED> listaVeiculoType = new ArrayList<VeiculoTypeED>();
		for (VeiculoED ed : veiculoRN.listaVeiculosUsusario(usuarioED)) {
			VeiculoTypeED veiEd = new VeiculoTypeED();
			veiEd.setIdVeiculo(ed.getId());
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

}
