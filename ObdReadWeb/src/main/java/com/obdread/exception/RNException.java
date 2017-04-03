package com.obdread.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class RNException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private List<RNException> exceptionList = new ArrayList<>();

	private String field;
  private String id;
  private String detail;

  public RNException() {
  }

  public RNException(String message) {
		super(message);
	}

	public RNException(String field, String message) {
		super(message);
		this.field = field;
	}
	
	/**
	 * Construtor utilizado para lan�ar RNException mais detalhadas para servi�os Rest.
	 * @param id
	 * @param field
	 * @param message
	 * @param detail
	 */
	public RNException(String id, String field, String message, String detail) {
	  this(field, message);
	  this.id = id;
	  this.detail = detail;
	}

	public String getField() {
		return field;
	}
	
	public String getId() {
	  return id;
	}
	
	public String getDetail() {
	  return detail;
	}

  /**
   * Adiciona RNException na lista de exception a ser lan�adas posteriormente via RNException#build()
   * @param message
   */
	public void addException(String message){
	  exceptionList.add(new RNException(message));
	}

  /**
   * Adiciona RNException na lista de exception a ser lan�adas posteriormente via RNException#build()
   * @param field
   * @param message
   */
  public void addException(String field, String message) {
    exceptionList.add(new RNException(field, message));
  }

  /**
   * Adiciona RNException na lista de exception a ser lan�adas posteriormente via RNException#build()
   * @param id
   * @param field
   * @param message
   * @param detail
   */
  public void addException(String id, String field, String message, String detail) {
    exceptionList.add(new RNException(id, field, message ,detail));
  }

  public void addException(RNException rNException){
    exceptionList.add(rNException);
  }

  /**
   * lan�a RNException se existe exce��o na lista de exce�oes ou existe mensagem de erro na superclasse
   */
	public void build(){
    //lanca exception
    if(super.getMessage() != null || (exceptionList != null && !exceptionList.isEmpty())){
      throw this;
    }
	}

  public List<RNException> getExceptionList() {
    return exceptionList;
  }

  public void setExceptionList(List<RNException> exceptionList) {
    this.exceptionList = exceptionList;
  }
}
