package com.obdread.infra.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropLista implements Serializable {

	private static final long serialVersionUID = 1L;

	private int inicio;
	private int tamanho;
	private Map<String, Object> filtros;
	private List<Ordem> ordem;
	
	public PropLista(int inicio, int tamanho) {
		this();
		this.inicio = inicio;
		this.tamanho = tamanho;
	}

	public PropLista() {
		this.filtros = new HashMap<>();
		this.ordem = new ArrayList<>();
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public Map<String, Object> getFiltros() {
		return filtros;
	}

	public List<Ordem> getOrdem() {
		return ordem;
	}
	
	public void setOrdem(List<Ordem> ordem) {
	  this.ordem = ordem;
	}

}
