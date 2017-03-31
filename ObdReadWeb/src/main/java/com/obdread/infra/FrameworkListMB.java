package com.obdread.infra;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.obdread.infra.persistence.FrameworkED;

public abstract class FrameworkListMB<ED extends FrameworkED<?>, PED extends ED> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * RN que o MB chama.
	 */
	FrameworkRN<ED, ?> rn;
	
	/**
	 * ED de pesquisa.
	 */
	PED ped;

	/**
	 * Paginador padr�o.
	 */
//	Paginator<ED> paginator = new Paginator<>();

	
	@Inject
	SessionFilter sessionFilter;
	
	// Actions
	
	@PostConstruct
	public void init() {
		initPesqED();
		//initPaginator();
	}
	
	/**
	 * Inicializa o Paginator default do ListMB.
	 * 
	 */
//	protected void initPaginator() {
//		paginator.setPaginatorRN(new PaginatorRN<ED>(ped) {
//			
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public List<ED> lista() {
//				return (List<ED>) rn.lista(ped);
//			}
//			
//			@Override
//			public int conta() {
//				return rn.conta(ped);
//			}
//		});
//	}

	/**
	 * Inicializa PesqED do ListMB com a inst�ncia que est� na sess�o do usu�rio.
	 * Caso n�o exista, cria uma nova inst�ncia e adiciona na sess�o.
	 *  
	 */
	protected void initPesqED() {
		PED ped = sessionFilter.get(this.getClass());
		if (ped == null) {
			ped = criaPesqED();
			sessionFilter.add(getClass(), ped);
		}
		setPed(ped);
	}
	
	/**
	 * Cria uma inst�ncia de PesqED.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected PED criaPesqED() {
		try {
			return ((Class<PED>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Limpa os crit�rios de pesquisa.
	 * 
	 */
	public void limpa() {
		sessionFilter.clear(this.getClass());
		setPed(criaPesqED());
	//	initPaginator();
	}
	
	/**
	 * Exclui os registros selecionados no paginator.
	 */
	public void exclui() {
	//	rn.exclui(getPaginator().getSelection());
		//FacesUtil.addInfoMessage(provider.getMessage("registro.exclui-sucesso"));
		FacesUtil.addInfoMessage("Registro Excluido com Sucesso.");
	}
	
	// 	Getters & Setters
	
//	public Paginator<ED> getPaginator() {
//		return paginator;
//	}

	public PED getPed() {
		return ped;
	}

	public void setPed(PED ped) {
		this.ped = ped;
	}
	
	public void setRN(FrameworkRN<ED, ?> rn) {
		this.rn = rn;
	}
	
	public FrameworkRN<ED, ?> getRN() {
		return rn;
	}

}
