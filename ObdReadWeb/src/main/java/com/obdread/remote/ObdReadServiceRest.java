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
import com.obdread.ed.rest.LogVeiculoTypeED;
import com.obdread.ed.rest.ObdType;
import com.obdread.ed.rest.VeiculoTypeED;
import com.obdread.exception.RNException;
import com.obdread.logveiculo.LogVeiculoRN;
import com.obdread.usuario.UsuarioRN;
import com.obdread.veiculo.VeiculoRN;

@Path("/ObdService")
@Produces(MediaType.APPLICATION_JSON)
@AccessTimeout(value = 60, unit = TimeUnit.SECONDS)
public class ObdReadServiceRest {

  @Inject
  UsuarioRN    usuarioRN;

  @Inject
  VeiculoRN    veiculoRN;

  @Inject
  LogVeiculoRN logVeiculoRN;

  /**
   * M�todo para verificar se servi�o est� respondendo
   * 
   * @return Data + Hora atual
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response status() {
    String dataHoraAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());

    return Response.ok("[" + dataHoraAtual + "] - Servi�o Online.").build();
  }

  /**
   * Recebe os dados vindos da aplica��o Android
   * @responseMessage 403 erro Usuario n�o autorizado
   * @status 404 Transacao n�o encontrada!
   * @status 500 Erro interno -
   */
  @POST
  @Path("/recebeDados")
  @Produces(MediaType.APPLICATION_JSON)
  public Response recebeDados(ObdType dados) {

    if (dados.getHashUser().equals(""))
      return Response.ok("Veiculo n�o encontrado!", MediaType.APPLICATION_JSON).build();

    UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(dados.getHashUser());

    if (dados.getIdVeiculo().equals(0))
      //return Response.status(HttpStatus.SC_NOT_FOUND).entity("Veiculo n�o encontrado!").build();
      return Response.status(Status.BAD_REQUEST).entity("Veiculo n�o encontrado!").build();

    VeiculoED veiculoED = veiculoRN.consulta(dados.getIdVeiculo());

    if (usuarioED == null || veiculoED == null)
      return Response.status(HttpStatus.SC_NOT_FOUND).entity("Usuario ou Veiculo n�o encontrado!").build();

    return Response.ok(dados, MediaType.APPLICATION_JSON).build();
    // return Response.ok(dados).build();
  }

  /**
   * Lista os ve�culos do Usu�rio
   * @responseMessage 403 erro Usuario n�o autorizado
   * @status 404 Transacao n�o encontrada!
   * @status 500 Erro interno -
   */
  @GET
  @Path("/listaVeiculos")
  @Produces(MediaType.APPLICATION_JSON)
  public List<VeiculoTypeED> consulta(@HeaderParam("hashUser") String hashUser) {

    UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(hashUser);

    if (usuarioED == null)
      throw new RNException("Dados Inv�lidos!");

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
   * Lista os logs do(s) veiculo(s) do Usu�rio
   * @responseMessage 403 erro Usuario n�o autorizado
   * @status 404 Transacao n�o encontrada!
   * @status 500 Erro interno -
   */
  @GET
  @Path("/listaLogsVeiculos")
  @Produces(MediaType.APPLICATION_JSON)
  public List<LogVeiculoTypeED> consultaLogsVeiculos(@HeaderParam("hashUser") String hashUser) {

    UsuarioED usuarioED = usuarioRN.bucaUsuarioTicket(hashUser);

    if (usuarioED == null)
      throw new RNException("Dados Inv�lidos!");

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
