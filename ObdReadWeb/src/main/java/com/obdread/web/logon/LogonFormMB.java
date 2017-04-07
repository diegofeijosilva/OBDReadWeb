package com.obdread.web.logon;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.ViewScoped;
import org.slf4j.Logger;

import com.obdread.ed.UsuarioED;
import com.obdread.infra.FacesUtil;
import com.obdread.usuario.UsuarioRN;

/**
 * Managed Bean para suporte as p�ginas de logon e altera��o de senha.
 * 
 * @author 
 *
 */
@Named
@ViewScoped
public class LogonFormMB implements Serializable {

  //  @Inject
  //  Event<LogonEvent> logonEvent;
  //	
  //	@Inject
  //	ApplicationProperties applicationProperties;
  //	
  //	@Inject
  //	MessageProvider messageProvider;
  //	
  //	@Inject
  //	Logger logger;

  @Inject
  UsuarioRN         usuarioRN;

  @Inject
  Logger            logger;

  //True se usu�rio est� logado e false caso contr�rio
  private boolean   loggedIn;

  //Armazena o usu�rio logado
  private UsuarioED usuarioLogado;

  //Email e senha digitado pelo usu�rio na p�gina XHTML
  private String    email, senha;

  // Actions

  @PostConstruct
  public void init() {

  }

  //Realiza o login caso de tudo certo
  public String doLogin() {

    //Verifica se o e-mail e senha existem e se o usuario pode logar          
    UsuarioED usuarioFound = usuarioRN.isUsuarioReadyToLogin(email, senha);

    //Caso n�o tenha retornado nenhum usuario, ent�o mostramos um erro
    //e redirecionamos ele para a p�gina login.xhtml              
    //para ele realiza-lo novamente          
    if (usuarioFound == null) {
      FacesUtil.addErrorMessage("Email ou Senha errado, tente novamente !");
      FacesContext.getCurrentInstance().validationFailed();
      return "/login.xhtml?faces-redirect=true";
    } else {
      //caso tenha retornado um usuario, setamos a vari�vel loggedIn      
      //como true e guardamos o usuario encontrado na vari�vel 
      //usuarioLogado. Depois de tudo, mandamos o usu�rio 
      //para a p�gina index.xhtml
      loggedIn = true;
      usuarioLogado = usuarioFound;

      logger.debug("Usu�rio logado.");

      HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      ses.setAttribute("logonFormMB", this);

      //return "/index.xhtml?faces-redirect=true";
      return "/index.xhtml";
    }
  }

  //Realiza o logout do usu�rio logado
  public String doLogout() {

    //Setamos a vari�vel usuarioLogado como nulo, ou seja, limpamos
    //os dados do usu�rio que estava logado e depois setamos a vari�vel
    //loggedIn como false para sinalizar que o usu�rio n�o est� mais           
    //logado
    usuarioLogado = null;
    loggedIn = false;

    // Finaliza a Sess�o
    HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    ses.setAttribute("logonFormMB", null);

    return "/mod-login/login.xhtml?faces-redirect=true";
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public UsuarioED getUsuarioLogado() {
    return usuarioLogado;
  }

  public void setUsuarioLogado(UsuarioED usuarioLogado) {
    this.usuarioLogado = usuarioLogado;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

}
