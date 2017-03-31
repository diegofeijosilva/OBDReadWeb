package com.obdread.infra;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;

import com.obdread.infra.persistence.FrameworkBDImpl;
import com.obdread.infra.persistence.FrameworkED;

/**
 * Superclasse com os métodos de CRUD básico para as RNs da aplicação.
 *
 * @param <ED> - ED que a RN atende
 * @param <PK> - Tipo da chave primária (Long, Integer, etc.) 
 * 
 */
public abstract class FrameworkRNImpl<ED extends FrameworkED<PK>, PK> implements Serializable, FrameworkRN<ED , PK> {
	
	private static final long serialVersionUID = 1L;

	private FrameworkBDImpl<ED, PK> bd;
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#consulta(PK)
	 */
	@Override
	@TransactionAttribute(SUPPORTS)
	public ED consulta(PK id) {
		return bd.consulta(id);
	}
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#inclui(ED)
	 */
	@Override
	public ED inclui(ED ed) {
		return bd.inclui(ed);
	}
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#altera(ED)
	 */
	@Override
	public ED altera(ED ed) {
		return bd.altera(ed);
	}
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#exclui(ED)
	 */
	@Override
	public void exclui(ED ed) {
		bd.exclui(ed);
	}
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#exclui(java.util.List)
	 */
	@Override
	public void exclui(List<ED> eds) {
		for (ED ed : eds) {
			exclui(ed);
		}
	}

	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#lista(ED)
	 */
	@Override
	@TransactionAttribute(SUPPORTS)
	public List<ED> lista(ED ped){
		return bd.lista(ped);
	}
	
	/* (non-Javadoc)
	 * @see com.procergs.apm.infra.FrameworkRN#conta(ED)
	 */
	@Override
	@TransactionAttribute(SUPPORTS)
	public int conta(ED ped){
		return bd.conta(ped);
	}
	
	// Getters & Setters 


	public void setBD(FrameworkBDImpl<ED, PK> bd) {
		this.bd = bd;
	}
	
}
