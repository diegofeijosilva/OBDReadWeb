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
 * Managed Bean para suporte as páginas de logon e alteração de senha.
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

  //True se usuário está logado e false caso contrário
  private boolean   loggedIn;

  //Armazena o usuário logado
  private UsuarioED usuarioLogado;

  //Email e senha digitado pelo usuário na página XHTML
  private String    email, senha;

  // Actions

  @PostConstruct
  public void init() {

  }

  //Realiza o login caso de tudo certo
  public String doLogin() {

    //Verifica se o e-mail e senha existem e se o usuario pode logar          
    UsuarioED usuarioFound = usuarioRN.isUsuarioReadyToLogin(email, senha);

    //Caso não tenha retornado nenhum usuario, então mostramos um erro
    //e redirecionamos ele para a página login.xhtml              
    //para ele realiza-lo novamente          
    if (usuarioFound == null) {
      FacesUtil.addErrorMessage("Email ou Senha errado, tente novamente !");
      FacesContext.getCurrentInstance().validationFailed();
      return "/login.xhtml?faces-redirect=true";
    } else {
      //caso tenha retornado um usuario, setamos a variável loggedIn      
      //como true e guardamos o usuario encontrado na variável 
      //usuarioLogado. Depois de tudo, mandamos o usuário 
      //para a página index.xhtml
      loggedIn = true;
      usuarioLogado = usuarioFound;

      logger.debug("Usuário logado.");

      HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      ses.setAttribute("logonFormMB", this);

      //return "/index.xhtml?faces-redirect=true";
      return "/index.xhtml";
    }
  }

  //Realiza o logout do usuário logado
  public String doLogout() {

    //Setamos a variável usuarioLogado como nulo, ou seja, limpamos
    //os dados do usuário que estava logado e depois setamos a variável
    //loggedIn como false para sinalizar que o usuário não está mais           
    //logado
    usuarioLogado = null;
    loggedIn = false;

    // Finaliza a Sessão
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
