package com.obdread.veiculo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppBD;

public class VeiculoBD extends AppBD<VeiculoED, Long> {

  @SuppressWarnings("unchecked")
  public List<VeiculoED> listaVeiculosUsusario(UsuarioED usuarioED) {
    DetachedCriteria dc = DetachedCriteria.forClass(VeiculoED.class, "veiculoED");
    dc.add(Restrictions.eq("usuarioED", usuarioED));

    Criteria c = dc.getExecutableCriteria(getSession());
    return c.list();
  }

}