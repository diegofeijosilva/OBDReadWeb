package com.obdread.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.obdread.ed.UsuarioED;
import com.obdread.exception.RNException;
import com.obdread.web.logon.LogonFormMB;

@Named
@RequestScoped
public class SessionMB {

  public UsuarioED getUsuarioLogado() {
    HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    LogonFormMB logon = (LogonFormMB) ses.getAttribute("logonFormMB");
    
    if(logon == null)
      throw new RNException("Usu�rio n�o est� logado no sistema!");

    // Pega o usu�rio logado no sistema
    return logon.getUsuarioLogado();
  }

}
