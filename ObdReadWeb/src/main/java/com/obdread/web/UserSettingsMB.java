package com.obdread.web;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class UserSettingsMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@Inject
//	ApplicationProperties applicationProperties;
	
	private String theme;
	private String layout;
	//private String language;
	private Locale locale;
	
	@PostConstruct
	void init() {
		//language = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
//		theme = applicationProperties.getTheme();
//		layout = applicationProperties.getLayout();
	}
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getLayout() {
		return layout;
	}
	
	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	


}
