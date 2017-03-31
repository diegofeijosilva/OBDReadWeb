package com.obdread.infra;

import java.io.Serializable;
import java.util.List;

import com.obdread.infra.persistence.FrameworkED;

public interface FrameworkRN<ED extends FrameworkED<PK>, PK> extends Serializable {

	public abstract ED consulta(PK id);

	public abstract ED inclui(ED ed);

	public abstract ED altera(ED ed);

	public abstract void exclui(ED ed);

	public abstract void exclui(List<ED> eds);

	public abstract List<ED> lista(ED ped);

	public abstract int conta(ED ped);
}