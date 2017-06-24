package com.obdread.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.obdread.ed.UsuarioED;
import com.obdread.exception.RNException;

public class UtilRN {

	/**
	 * TRANSFORMA A SENHA EM UM HASH
	 * 
	 * @param ed
	 * @return
	 */
	public String convertStringToSHA256(String senha) {
		if (senha == null)
			throw new RNException("Favor informar a senha!");

		String frase = "#" + senha + "#";
		MessageDigest algorithm;
		try {

			algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(frase.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String();
	}

	/**
	 * GERA UM C�DIGO HASH QUE SER� UTILIZADO COMO TICKET PELO ANDROID PARA
	 * ENVIO DAS INFORMA��ES
	 * 
	 * @param ed
	 * @return
	 */
	public String geradorTicket(UsuarioED ed) {
		Random random = new Random();
		String frase = ed.getEmail() + "???" + ed.getSenha() + "???" + random.nextInt(100000);
		MessageDigest algorithm;
		try {

			algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(frase.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String();

	}

	/**
	 * Converte Calendar em dd/mm/yyyy
	 */

	public static String converteddmmyy(Calendar obj) {
		if (obj != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setTimeZone(obj.getTimeZone());
			return df.format(obj.getTime());
		}
		return new String();
	}

	public static String converteCalendarDateTime(Calendar obj) {
		if (obj != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.setTimeZone(obj.getTimeZone());
			return df.format(obj.getTime());
		}
		return new String();
	}

	/**
	 * RETORNA A HORA E MINUTO
	 * 
	 * @param obj
	 * @return
	 */

	public static String retornaHHMM(Calendar obj) {
		if (obj != null) {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			df.setTimeZone(obj.getTimeZone());
			return df.format(obj.getTime());
		}
		return new String();
	}

	/**
	 * Retorna a diferen�a entre datas
	 */
	public static Long diferencaEntreDatasEmDias(Calendar dthInicio, Calendar dthFim) {
		// Calcula a diferen�a entre hoje e da data de inicio
		long diferenca = dthFim.getTimeInMillis() - dthInicio.getTimeInMillis();

		// Quantidade de milissegundos em um dia
		int tempoDia = 1000 * 60 * 60 * 24;
		return diferenca / tempoDia;
	}

}
