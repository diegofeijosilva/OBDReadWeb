package com.obdread.errosecu;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.obdread.ed.ErrosEcuED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppBD;

public class ErrosEcuBD extends AppBD<ErrosEcuED, Long> {

	public List<ErrosEcuED> listaErrosEcuVeiculo(VeiculoED ed) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TBL_ERROS_ECU A WHERE A.VEICULO_ID = :veiculo");

		Query query = super.getEntityManager().createNativeQuery(sql.toString(), ErrosEcuED.class);
		query.setParameter("veiculo", ed.getId());
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}