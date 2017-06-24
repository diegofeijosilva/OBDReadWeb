package com.obdread.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe que produz instância de Logger.
 */
public class LoggerProducer {

	@Produces
	public Logger produceLogger(final InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getCanonicalName());
	}

}
