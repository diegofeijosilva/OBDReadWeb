package com.obdread.infra.persistence;

import java.io.Serializable;

/**
 * Cont�m as propriedades necess�rias para montagem din�mica 
 * dos crit�rios de ordena��o de uma lista (ORDER BY)
 * @author mauro-flores
 *
 */
public class Ordem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String propriedade;	
	private boolean asc = true;
	
	public Ordem() {
	}
	
	public Ordem(String propriedade) {
		this.propriedade = propriedade;
	}
	
	public Ordem(String propriedade, boolean asc) {
		this.propriedade = propriedade;
		this.asc = asc;
	}
	
	public String getPropriedade() {
		return propriedade;
	}
	
	public void setPropriedade(String propriedade) {
	  this.propriedade = propriedade;
	}
	
	public boolean isAsc() {
		return asc;
	}
	
	public void setAsc(boolean asc) {
	  this.asc = asc;
	}
	
}
