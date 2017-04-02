package com.obdread.web.logon;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

/**
 * Managed Bean para suporte as páginas de logon e alteração de senha.
 * 
 * @author 
 *
 */
@Named
@ViewScoped
public class LogonFormMB implements Serializable {
	
  private static final long serialVersionUID = 1L;
  private static final String LOGO_ASSINADOR_FX = "/resources/image/logo-assinador-fx.png";
  private static final String ASSINADOR_FX_CSS = "/resources/css/assinador-fx.css";
  private static final int EXPIRA_EM = 60 * 60 * 24 * 365; // 1 ano
	
	
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

	LogonFormED ed;


	// Actions

	@PostConstruct
	public void init() {
//    urlEsqueciSenha = Propriedades.getInstance("SOE").getValor("soe.esqsen.url");
//    HttpServletRequest request = FacesUtil.getRequest(FacesUtil.getContext());
//    privateKey = request.getServletContext().getInitParameter(Captcha.PRIVATE_KEY);
//    publicKey = request.getServletContext().getInitParameter(Captcha.PUBLIC_KEY);
//    loadAssinadorConfig(request);
//    loadCookieValues(request);
//		loadTipoDocumentos();
//		loadMessages(request);
//		defineFocus(request);
	}


	public LogonFormED getEd() {
		return ed;
	}


	public void setEd(LogonFormED ed) {
		this.ed = ed;
	}



}
