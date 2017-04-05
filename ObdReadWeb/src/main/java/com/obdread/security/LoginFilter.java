package com.obdread.security;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obdread.web.logon.LogonFormMB;

public class LoginFilter implements Filter {


  public void destroy() {
    // TODO Auto-generated method stub

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    //Captura o ManagedBean chamado 
    LogonFormMB logonFormMB = (LogonFormMB) ((HttpServletRequest) request).getSession().getAttribute("logonFormMB");
    HttpServletRequest req = (HttpServletRequest) request;

    
    if (isFacesResourceRequest(req)) {
      chain.doFilter(request, response);
      return;
    }

    //Verifica se nosso ManagedBean ainda não 
    //foi instanciado ou caso a
    //variável loggedIn seja false, assim saberemos que  
    // o usuário não está logado
    if (logonFormMB == null || !logonFormMB.isLoggedIn()) {
      String contextPath = ((HttpServletRequest) request).getContextPath();
      
      if (req.getRequestURI().equals("/ObdReadWeb/mod-login/login.xhtml")){
     // Otherwise, nothing to do if he has not logged in
        chain.doFilter(request, response);
      } else {
        //Redirecionamos o usuário imediatamente 
        //para a página de login.xhtml
        ((HttpServletResponse) response).sendRedirect(contextPath + "/mod-login/login.xhtml");
      }
      

    } else {
      //Caso ele esteja logado, apenas deixamos 
      //que o fluxo continue
      chain.doFilter(request, response);
    }
  }

  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }
  
  /**
   * Verifica se o request é resource JSF (css, javascript, imagens).
   * 
   * @param request
   * @return
   */
  private boolean isFacesResourceRequest(HttpServletRequest request) {
    return request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
  }
}