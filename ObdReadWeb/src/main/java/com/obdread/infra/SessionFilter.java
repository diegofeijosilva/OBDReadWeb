package com.obdread.infra;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import com.obdread.infra.persistence.FrameworkED;

/**
 * 
 * @author rafael-pestano
 * 
 * Classe que mantem filtros das telas de pesquisa em sessão.
 * Os filtros são agrupados(key do hashMap) por AppListMB, ou seja,
 * cada tela de listagem tem uma chave para o filtro de sessao cujo valor eh um ED.
 * 
 */
@SessionScoped
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SessionFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map sessionMap = new HashMap();

	public <V extends FrameworkED<?>> void add(Class<? extends FrameworkListMB> key, V value) {
		sessionMap.put(key, value);
	}

	public void clear(Class<? extends FrameworkListMB> key) {
    if (sessionMap.containsKey(key)) {
      sessionMap.put(key, null);
    }
  }

	public <V extends FrameworkED> V get(Class<? extends FrameworkListMB> key) {
		return (V) sessionMap.get(key);
	}

}
