package com.obdread.web.logon;

import java.io.Serializable;

public class LogonFormED implements Serializable {

	private static final long serialVersionUID = 1L;

	// Campos para Logon
	private String logonPor;
	private String organizacao;
	private String matricula;
	private String email;
	private String documento;
	private String tipoDocumento;
	private String documentoOrganizacao;
	private String certificadoOrganizacao;
	private String senha;
	
	// Campos para alteração de senha
	private String senhaAtual;
	private String novaSenha;
	private String novaSenhaConfirma;

	public String getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(String organizacao) {
		this.organizacao = organizacao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumentoOrganizacao() {
		return documentoOrganizacao;
	}

	public void setDocumentoOrganizacao(String documentoOrganizacao) {
		this.documentoOrganizacao = documentoOrganizacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirma() {
		return novaSenhaConfirma;
	}

	public void setNovaSenhaConfirma(String novaSenhaConfirma) {
		this.novaSenhaConfirma = novaSenhaConfirma;
	}

	public String getCertificadoOrganizacao() {
		return certificadoOrganizacao;
	}

	public void setCertificadoOrganizacao(String certificadoOrganizacao) {
		this.certificadoOrganizacao = certificadoOrganizacao;
	}

	public String getLogonPor() {
		return logonPor;
	}

	public void setLogonPor(String logonPor) {
		this.logonPor = logonPor;
	}

}
