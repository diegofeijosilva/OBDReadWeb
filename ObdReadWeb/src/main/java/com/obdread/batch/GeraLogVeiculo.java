package com.obdread.batch;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Esse job roda todos os dias para verificar se é preciso gerar algum log para o veiculo
 * @author diego-silva
 *
 */

@Singleton
public class GeraLogVeiculo {

  @Schedule(hour = "22", persistent = false, info = "Executa todos os dias às 22:00h.")
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void execute() {
    
    /// Varrer a tabela de veiculos pesquisando pela data
    
  }
}
