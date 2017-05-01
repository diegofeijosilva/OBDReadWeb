package com.obdread.web.usuario;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.obdread.ed.UsuarioED;
import com.obdread.infra.AppFormMB;
import com.obdread.security.SessionMB;
import com.obdread.usuario.UsuarioRN;

@Named
@ViewScoped
public class UsuarioFormMB extends AppFormMB<UsuarioED, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	UsuarioRN usuarioRN;

	@Inject
	SessionMB logon;

	@PostConstruct
	void initRN() {
		super.setRN(usuarioRN);
	}

	@Override
	public void init() {
		super.setEd(logon.getUsuarioLogado());
	}

	@Override
	public UsuarioED criaED() {
		return logon.getUsuarioLogado();
	}

	@Override
	public void salva() {

		return;
	}

}