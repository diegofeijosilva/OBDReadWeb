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
	 * Paginador padrão.
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
	 * Inicializa PesqED do ListMB com a instância que está na sessão do usuário.
	 * Caso não exista, cria uma nova instância e adiciona na sessão.
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
	 * Cria uma instância de PesqED.
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
	 * Limpa os critérios de pesquisa.
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
