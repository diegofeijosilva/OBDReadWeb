package com.obdread.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.obdread.ed.UsuarioED;
import com.obdread.exception.RNException;

public class UtilRN {

  /**
   * TRANSFORMA A SENHA EM UM HASH
   * @param ed
   * @return
   */
  public String convertStringToSHA256(String senha) {
    if (senha == null)
      throw new RNException("Favor informar a senha!");

    String frase = "#" + senha + "#";
    MessageDigest algorithm ;
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
   * GERA UM CÓDIGO HASH QUE SERÁ UTILIZADO COMO TICKET PELO ANDROID
   * PARA ENVIO DAS INFORMAÇÕES
   * @param ed
   * @return
   */
  public String geradorTicket(UsuarioED ed) {
    Random random = new Random();
    String frase = ed.getEmail() + "???" + ed.getSenha() + "???" + random.nextInt(100000);
    MessageDigest algorithm ;
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

}
