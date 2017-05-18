package com.obdread.errosecu;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.obdread.ed.ErrosEcuED;
import com.obdread.infra.AppBD;
import com.obdread.util.UtilRN;

public class ErrosEcuBD extends AppBD<ErrosEcuED, Long> {

	public List<ErrosEcuED> listaErrosEcuVeiculo(Long veiculoId, Calendar dthInicio, Calendar dthFim) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TBL_ERROS_ECU A WHERE A.VEICULO_ID = :veiculo");
		sql.append(" AND DTH_LOG BETWEEN :dthInicio AND :dthFim ");

		Query query = super.getEntityManager().createNativeQuery(sql.toString(), ErrosEcuED.class);
		query.setParameter("veiculo", veiculoId);
		query.setParameter("dthInicio", UtilRN.converteCalendarDateTime(dthInicio));
		query.setParameter("dthFim", UtilRN.converteCalendarDateTime(dthFim));
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}