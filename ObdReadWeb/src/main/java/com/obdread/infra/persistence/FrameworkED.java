package com.obdread.infra.persistence;

public interface FrameworkED<PK> {

  /**
   * usado pela api rest
   * @hidden 
   */
	public abstract PK getId();

	/**
	 * usado pela api rest
   * @hidden 
   */
	public abstract PropLista getPropLista();

}