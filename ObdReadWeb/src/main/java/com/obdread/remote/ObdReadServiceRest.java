package com.obdread.remote;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.obdread.ed.ObdType;
import com.obdread.ed.UsuarioED;
import com.obdread.usuario.UsuarioRN;

@Path("/ObdService")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObdReadServiceRest {
  
  @Inject
  UsuarioRN usuarioRN;
  
  /**
   * Método para verificar se serviço está respondendo
   * 
   * @return Data + Hora atual
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String status() {
    String dataHoraAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    
    return "[" + dataHoraAtual + "] - Serviço conversão de transações Online.";
  }
  
  /**
   * Recebe os dados vindos da aplicação Android
   * @responseMessage 403 erro Usuario não autorizado
   * @status 404 Transacao não encontrada!
   * @status 500 Erro interno -
   */
  @POST
  @Path("/recebeDados")
  public String consulta(ObdType dados) {

    return "OK";
  }
  
  /**
   * Recebe os dados vindos da aplicação Android
   * @responseMessage 403 erro Usuario não autorizado
   * @status 404 Transacao não encontrada!
   * @status 500 Erro interno -
   */
  @POST
  @Path("/incluiUsuario")
  public UsuarioED incluiUsuario(UsuarioED usuarioED) {

    return usuarioRN.inclui(usuarioED);
    
  }

}
