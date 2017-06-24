package com.obdread.veiculo.dadosveiculo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.obdread.ed.DadosVeiculoED;
import com.obdread.ed.ErrosEcuED;
import com.obdread.infra.AppBD;
import com.obdread.util.UtilRN;

public class DadosVeiculoBD extends AppBD<DadosVeiculoED, Long> {

	public List<DadosVeiculoED> listaHistoricoGPSVeiculo(Long veiculoId, Calendar dthInicio, Calendar dthFim) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TBL_DADOS_VEICULO A WHERE A.VEICULO_ID = :veiculo");
		sql.append(" AND DTH_LOG BETWEEN :dthInicio AND :dthFim ");

		Query query = super.getEntityManager().createNativeQuery(sql.toString(), DadosVeiculoED.class);
		query.setParameter("veiculo", veiculoId);
		query.setParameter("dthInicio", UtilRN.converteCalendarDateTime(dthInicio));
		query.setParameter("dthFim", UtilRN.converteCalendarDateTime(dthFim));
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	//// Utilizado no dashboard
	public List<DadosVeiculoED> listaHistoricoOBDVeiculo(Long veiculoId, Calendar dthAtual) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TBL_DADOS_VEICULO");
		sql.append(" WHERE VEICULO_ID = :veiculo ");
		//sql.append(" AND DTH_LOG = :dthAtual ");
		sql.append(" ORDER BY CTR_DTH_INC DESC ");
		sql.append(" LIMIT 10 ");

		Query query = super.getEntityManager().createNativeQuery(sql.toString(), DadosVeiculoED.class);
		query.setParameter("veiculo", veiculoId);
		//query.setParameter("dthInicio", UtilRN.converteCalendarDateTime(dthAtual));
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<DadosVeiculoED> listaHistoricoOBDVeiculo(Long veiculoId, Calendar dthInicio, Calendar dthFim) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TBL_DADOS_VEICULO A WHERE A.VEICULO_ID = :veiculo");
		sql.append(" AND DTH_LOG BETWEEN :dthInicio AND :dthFim ");

		Query query = super.getEntityManager().createNativeQuery(sql.toString(), DadosVeiculoED.class);
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