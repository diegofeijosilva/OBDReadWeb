package com.obdread.infra;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Métodos utilitários para JSF.
 * 
 * @author diego-feijo
 *
 */
public class FacesUtil {

  /**
   * Adiciona uma Error Message global no FacesContext.
   * 
   * @param message
   */
  public static void addErrorMessage(String message) {
    addErrorMessage(null, message);
  }

  /**
   * Adiciona uma Error Message específica de um componente e	faz o setValid(false) do mesmo.
   * Se o componente não for encontrado, adiciona uma Error Message global.
   * 
   * @param id ID do componente JSF
   * @param message
   */
  public static void addErrorMessage(String id, String message) {
    createMessage(getContext(), FacesMessage.SEVERITY_ERROR, id, message);
  }

  /**
   * Adiciona uma Fatal Message global no FacesContext.
   * 
   * @param message
   */
  public static void addFatalMessage(String message) {
    addFatalMessage(null, message);
  }

  /**
   * Adiciona uma Fatal Message específica de um componente e	faz o setValid(false) do mesmo.
   * Se o componente não for encontrado, adiciona uma Fatal Message global.
   * 
   * @param id ID do componente JSF
   * @param message
   */
  public static void addFatalMessage(String id, String message) {
    createMessage(getContext(), FacesMessage.SEVERITY_FATAL, id, message);
  }

  /**
   * Adiciona uma Info Message global no FacesContext.
   * 
   * @param message
   */
  public static void addInfoMessage(String message) {
    addInfoMessage(null, message);
  }

  /**
   * Adiciona uma Info Message específica de um componente.
   * Se o componente não for encontrado, adiciona uma Info Message global.
   * 
   * @param id ID do componente JSF
   * @param message
   */
  public static void addInfoMessage(String id, String message) {
    createMessage(getContext(), FacesMessage.SEVERITY_INFO, id, message);
  }

  /**
   * Adiciona uma Warn Message global no FacesContext.
   * 
   * @param message
   */
  public static void addWarnMessage(String message) {
    addWarnMessage(null, message);
  }

  /**
   * Adiciona uma Warn Message específica de um componente.
   * Se o componente não for encontrado, adiciona uma Warn Message global.
   * 
   * @param id ID do componente JSF
   * @param message
   */
  public static void addWarnMessage(String id, String message) {
    createMessage(getContext(), FacesMessage.SEVERITY_WARN, id, message);
  }

  /**
   * Adiciona uma Message global no FacesContext.
   * 
   * @param severity
   * @param message
   */
  public static void addMessage(Severity severity, String message) {
    addMessage(severity, null, message);
  }

  /**
   * Adiciona uma Message específica de um componente.
   * Se o componente não for encontrado, adiciona uma Message global.
   * Se a severidade informado for ERROR ou FATAL, faz o setValid(false) no componente.
   * 
   * @param severity
   * @param id ID do componente JSF
   * @param message
   */
  public static void addMessage(Severity severity, String id, String message) {
    createMessage(getContext(), severity, id, message);
  }

  /**
   * Encontra um componente na View JSF.
   * 
   * @param id ID do componente JSF.
   * @return
   */
  public static UIComponent findComponent(String id) {
    return findComponent(getContext(), id);
  }

  /**
   * Encontra um componente na View JSF.
   * 
   * @param context FacesContext
   * @param id ID do componente JSF.
   * @return
   */
  public static UIComponent findComponent(FacesContext context, String id) {
    if (id == null) {
      return null;
    }
    return FacesUtil.findComponent(context.getViewRoot(), id);
  }

  /**
   * foi deprecated no prime5.
   * @param base
   * @param id
   * @return
   */
  @Deprecated
  public static UIComponent findComponent(UIComponent base, String id) {
    if (id.equals(base.getId()))
      return base;

    UIComponent kid = null;
    UIComponent result = null;
    Iterator<UIComponent> kids = base.getFacetsAndChildren();
    while (kids.hasNext() && (result == null)) {
      kid = (UIComponent) kids.next();
      if (id.equals(kid.getId())) {
        result = kid;
        break;
      }
      result = findComponent(kid, id);
      if (result != null) {
        break;
      }
    }
    return result;
  }

  /**
   * Obtém o objeto Session.
   * 
   * @return
   */
  public static HttpSession getSession() {
    return getSession(getContext());
  }

  /**
   * Obtém o objeto Session a partir do FacesContext informado.
   * 
   * @param context
   * @return
   */
  public static HttpSession getSession(FacesContext context) {
    return getRequest(context).getSession();
  }

  /**
   * Obtém o objeto Request a partir do FacesContext informado.
   * 
   * @param context
   * @return
   */
  public static HttpServletRequest getRequest(FacesContext context) {
    return (HttpServletRequest) context.getExternalContext().getRequest();
  }

  /**
   * Obtém o objeto Response a partir do FacesContext informado.
   * 
   * @param context
   * @return
   */
  public static HttpServletResponse getResponse(FacesContext context) {
    return (HttpServletResponse) context.getExternalContext().getResponse();
  }

  /**
   * Obtém o flash map (que tem ciclo de vida de uma requisiçao e sobrevive ao redirect).
   *
   * @return
   */
  public static Flash getFlash() {
    return getExternalContext().getFlash();
  }

  /**
   * Obtém o FacesContext.
   * 
   * @return
   */
  public static FacesContext getContext() {
    return FacesContext.getCurrentInstance();
  }

  /**
   * Obtém o external context do FacesContext.
   *
   * @return
   */
  public static ExternalContext getExternalContext() {
    return getContext().getExternalContext();
  }

  /**
     * Obtém o valor do cookie informado.
     * 
     * @param name Nome do cookie
     */
  public static String getCookieValue(String name) {
    return getCookieValue(getRequest(getContext()), name);
  }

  /**
   * Obtém o valor do cookie informado.
   * 
   * @param request Objeto Request
   * @param name Nome do cookie 
   */
  public static String getCookieValue(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null && name != null) {
      for (Cookie cookie : cookies) {
        if (cookie != null && name.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  /**
   * Cria um cookie com o valor e tempo de expiração informados.
   * 
   * @param name Nome do cookie
   * @param value Valor do cookie
   * @param maxAge Tempo de expiração em segundos. Se informado zero, expiração é imediata.
   */
  public static void setCookieValue(String name, String value, int maxAge) {
    setCookieValue(getResponse(getContext()), name, value, maxAge);
  }

  /**
   * Cria um cookie com o valor e tempo de expiração informados.
   *
   * @param response Objeto response
   * @param name Nome do cookie
   * @param value Valor do cookie
   * @param maxAge Tempo de expiração em segundos. Se informado zero, expiração é imediata.
   */
  public static void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  /**
   * Remove o cookie.
   * 
   * @param name Nome do cookie
   */
  public static void removeCookie(String name) {
    removeCookie(getResponse(getContext()), name);

  }

  /**
   * Remove o cookie.
   * 
   * @param response Objeto Response
   * @param name Nome do cookie
   */
  public static void removeCookie(HttpServletResponse response, String name) {
    setCookieValue(response, name, "", 0);
  }

  // Utils

  private static void createMessage(FacesContext context, Severity severity, String id, String message) {
    UIComponent component = findComponent(context, id);
    if (component == null) {
      context.addMessage(null, new FacesMessage(severity, null, message));
      return;
    }
    String clientId = component.getClientId();
    context.addMessage(clientId, new FacesMessage(severity, null, message));
    if (severity == FacesMessage.SEVERITY_ERROR || severity == FacesMessage.SEVERITY_FATAL) {
      setValidFalse(component);
    }
  }

  private static void setValidFalse(UIComponent comp) {
    if (comp instanceof EditableValueHolder) {
      ((EditableValueHolder) comp).setValid(false);
    }
  }

}
