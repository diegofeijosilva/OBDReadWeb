package com.obdread.logveiculo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.obdread.ed.LogVeiculoED;
import com.obdread.ed.UsuarioED;
import com.obdread.ed.VeiculoED;
import com.obdread.infra.AppBD;

public class LogVeiculoBD extends AppBD<LogVeiculoED, Long> {

  @SuppressWarnings("unchecked")
  public List<VeiculoED> listaVeiculosUsusario(UsuarioED usuarioED) {
    DetachedCriteria dc = DetachedCriteria.forClass(VeiculoED.class, "veiculoED");
    dc.add(Restrictions.eq("usuarioED", usuarioED));

    Criteria c = dc.getExecutableCriteria(getSession());
    return c.list();
  }

  @SuppressWarnings("unchecked")
  public List<LogVeiculoED> listaLogUsusario(UsuarioED usuarioED) {
    DetachedCriteria dc = DetachedCriteria.forClass(LogVeiculoED.class, "logVeiculoED");
    dc.add(Restrictions.eq("usuarioED", usuarioED));

    Criteria c = dc.getExecutableCriteria(getSession());
    return c.list();
  }

  //  @Inject
  //  MunicipioRN   municipioRN;
  //
  //  @Inject
  //  CredenciadoRN credenciadoRN;
  //
  //  @Inject
  //  Logger        logger;

  //  @Override
  //  public DetachedCriteria montaCriterios(UsuarioED ped) {
  //    DetachedCriteria dc = DetachedCriteria.forClass(UsuarioED.class);
  //    dc.createAlias("municipioED", "municipioED", JoinType.INNER_JOIN);
  //    dc.createAlias("credenciadoED", "credenciadoED", JoinType.INNER_JOIN);
  //
  //    Calendar fimPesquisa;
  //    Calendar inicioPesquisa;
  //
  //    // Filtro com as situações '1-Disponível' e '2-Lotado'.
  //    Integer[] filtraSituacao1e2 = { 1, 2 };
  //
  //    if (ped.getDataFimPesquisa() != null) {
  //      fimPesquisa = ped.getDataFimPesquisa();
  //    } else {
  //      fimPesquisa = HabWebUtil.getDataFutura();
  //      ped.setDataFimPesquisa(HabWebUtil.getDataFutura());
  //    }
  //
  //    if (ped.getDataInicioPesquisa() != null) {
  //      inicioPesquisa = ped.getDataInicioPesquisa();
  //    } else {
  //      inicioPesquisa = HabWebUtil.getDataAtual();
  //      ped.setDataInicioPesquisa(HabWebUtil.getDataAtual());
  //    }
  //
  //    dc.add(Restrictions.between("dthAgenda", inicioPesquisa, fimPesquisa));
  //    
  //    if (ped.getSituacao() != null) {
  //      dc.add(Restrictions.eq("situacao", ped.getSituacao()));
  //    } else {
  //
  //      //usa o que foi marcado nos itens de situaçao na tela
  //      if(ped.getUsaItensTela()!=null && ped.getUsaItensTela()==true){
  //        if(ped.getItensSituacaoSelecionados()!=null && ped.getItensSituacaoSelecionados().length>0){
  //          dc.add(Restrictions.in("situacao", ped.getItensSituacaoSelecionados()));
  //        }
  //      }else{ 
  //        dc.add(Restrictions.in("situacao", filtraSituacao1e2 ));
  //      }
  //
  //    }
  //    
  //    // VERIFICA SE ESTÁ USANDO ALGUM TIPO DE ORDENAÇÃO
  //    // SE NÃO ESTIVER ORDENA PELA DATA AGENDA
  //    List<Ordem> ordenacao = ped.getPropLista().getOrdem();
  //    for (Ordem ordem : ordenacao) {
  //      if (ordem.getPropriedade().equals("dthAgenda"))
  //        dc.addOrder(Order.asc("dthAgenda"));
  //    }
  //
  //    // FILTRO MUNICIPIO
  //    if (ped.getMunicipioED() != null)
  //      if (ped.getMunicipioED().getCodMunicipio() != null)
  //        dc.add(Restrictions.sqlRestriction("COD_MUNIC = " + ped.getMunicipioED().getCodMunicipio()));
  //
  //    // FILTRO CREDENCIADO
  //    if (ped.getCredenciadoED() != null){
  //      if (ped.getCredenciadoED().getCodCliente() != null){        
  //        dc.add(Restrictions.sqlRestriction("COD_EMPRESA = '" + ped.getCredenciadoED().getCodCliente().toUpperCase()+"'"));
  //      }
  //    }
  //
  //    
  //    super.adicionaRestricoes(ped, dc);
  //
  //    return dc;
  //  }
  //

}