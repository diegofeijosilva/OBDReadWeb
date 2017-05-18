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
//  // RETORNA UMA LISTA DE PROFISSIONAIS FILTRADOS PELO NOME
//  public List<AtividadeProfissionalED> getProfissionaisPorNome(String nome) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("  SELECT * ");
//    sql.append("  FROM KGID206D");
//    sql.append("  WHERE K206_TP_PROFIS = 29 AND K206_STATUS = 2 AND K206_NOME LIKE '%" + nome + "%'");
//    sql.append("  ORDER BY K206_NOME ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), AtividadeProfissionalED.class);
//
//    return (List<AtividadeProfissionalED>) query.getResultList();
//  }
//
//  // RETORNA UM PROFISSIONAL FILTRADO POR IDENTIDADE E TP PROFISSAO
//  public AtividadeProfissionalED getProfissionalPorDocumento(Integer tpProfis, String docIdentProfis, String ufDocIdentProfis) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("  SELECT * ");
//    sql.append("  FROM KGID206D");
//    sql.append("  WHERE K206_TP_PROFIS = '" + tpProfis + "'");
//    sql.append("  AND K206_STATUS = 2 ");
//    sql.append("  AND K206_DOC_IDENT = '" + docIdentProfis + "'");
//    sql.append("  AND K206_UF_DOC_IDENT = '" + ufDocIdentProfis + "'");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), AtividadeProfissionalED.class);
//
//    return (AtividadeProfissionalED) query.getSingleResult();
//  }
//
//  public String buscaEmailDoProfissional(String docIdent, String ufDocIdent) {
//    String email = "";
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT K205_EMAIL ");
//    sql.append("   FROM KGID205D ");
//    sql.append("  WHERE K205_DOC_IDENT = :docIdent ");
//    sql.append("    AND K205_UF_DOC_IDENT = :ufDocIdent ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//    query.setParameter("docIdent", docIdent);
//    query.setParameter("ufDocIdent", ufDocIdent);
//    email = (String) query.getSingleResult();
//    return email;
//  }
//
//  public List<String> buscaEmailsDosCredenciados(String filtroClientes) {
//    if (filtroClientes == "" || filtroClientes == null)
//      return null;
//
//    List<String> emails = new ArrayList<String>();
//    StringBuffer sql = new StringBuffer();
//
//    // select n.k200_email from Kgid200d n where n.k200_cod_cliente =
//    // 'DESCHC01'
//    sql.append(" SELECT K200_EMAIL ");
//    sql.append("   FROM KGID200D ");
//    sql.append("  WHERE K200_COD_CLIENTE IN ( " + filtroClientes + " ) ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//    List<Object[]> listaArrayObj = query.getResultList();
//    if (listaArrayObj.size() > 0) {
//      String email;
//
//      for (int i = 0; i < listaArrayObj.size(); i++) {
//        try {
//          email = Util.parseString(listaArrayObj.get(i));
//          emails.add(email);
//        } catch (ProcergsUtilException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//      }
//    }
//    return emails;
//  }
//
//  public List<Long> buscaCodIndivDosCandidatosAgendados(Long nroIntJuntaCronograma) {
//    List<Long> listCodIndiv = new ArrayList<Long>();
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT * FROM HAB_JUNTA_AGENDAMENTO_COND A ");
//    sql.append(" WHERE A.NRO_INT_JUNTA_CRONOGRAMA = :nroIntJuntaCronograma");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaAgendamentoCondutorED.class);
//    query.setParameter("nroIntJuntaCronograma", nroIntJuntaCronograma);
//    List<JuntaAgendamentoCondutorED> lista = query.getResultList();
//
//    if (lista.size() > 0) {
//      Long codIndiv;
//
//      for (JuntaAgendamentoCondutorED ed : lista) {
//        codIndiv = ed.getIndividuoED().getCodIndiv();
//        listCodIndiv.add(codIndiv);
//      }
//    }
//    return listCodIndiv;
//  }
//
//  public List<String> buscaListaDeCodClienteFromConjuntoDeCodIndiv(String listCodIndiv) {
//    List<String> listaCodCliente = new ArrayList<String>();
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT DISTINCT a.k161_cod_cliente ");
//    sql.append("   FROM KGID161D a ");
//    sql.append("  WHERE a.k161_situacao <> 9 ");
//    sql.append("   AND  a.k161_cod_indiv in ( " + listCodIndiv + " )");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//    List<Object[]> listaArrayObj = query.getResultList();
//    if (listaArrayObj.size() > 0) {
//      String codCliente;
//
//      for (int i = 0; i < listaArrayObj.size(); i++) {
//        try {
//          codCliente = Util.parseString(listaArrayObj.get(i));
//          listaCodCliente.add(codCliente);
//        } catch (ProcergsUtilException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//      }
//    }
//
//    return listaCodCliente;
//  }
//
//  // RETORNA O CREDENCIADO PELO MUNICIPIO
//  public List<CredenciadoED> getCredenciado(Integer codMunic) {
//
//    try {
//
//      StringBuilder sqlSB = new StringBuilder();
//      sqlSB.append(" select k200.* from kgid200d k200, ");
//      sqlSB.append(" hab_local_atuacao la ");
//      sqlSB.append(" where ");
//      sqlSB.append(" k200.k200_cod_cliente = la.cod_creden_kgid200 ");
//      sqlSB.append(" and k200.k200_cod_munic = la.cod_munic_kgid32 ");
//      sqlSB.append(" and la.situacao = 1 ");
//      sqlSB.append(" and la.cod_munic_kgid32 = :codMunic ");
//      Query query = super.getEntityManager().createNativeQuery(sqlSB.toString(), CredenciadoED.class);
//      query.setParameter("codMunic", codMunic);
//
//      return query.getResultList();
//
//    } catch (Exception e) {
//      return null;
//    }
//
//  }
//
//  // VERIFICA SE O CRONOGRAMA QUE SE ESTÁ CRIANDO JÁ NÃO FOI INSERIDO
//  public Boolean verificaCronogramaExiste(JuntaCronogramaED ed) {
//    try {
//      String sql = "SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.COD_MUNIC = :codMunic AND B.DTH_AGENDA = :dthAgenda AND B.TURNO = :turno AND B.SITUACAO NOT IN(9)";
//      Query query = super.getEntityManager().createNativeQuery(sql, JuntaCronogramaED.class);
//      query.setParameter("codMunic", ed.getMunicipioED().getCodMunicipio());
//      query.setParameter("dthAgenda", ed.getDthAgenda());
//      query.setParameter("turno", ed.getTurno());
//
//      List<JuntaCronogramaED> edRet = query.getResultList();
//
//      if (edRet.size() > 0)
//        return true;
//      else
//        return false;
//
//    } catch (Exception e) {
//      return null;
//    }
//  }
//
//  // Busca todos os registros na HAB_JUNTA_CRONOGRAMA FILTRANDO PELO INTERVALO DE DATAS
//  public List<JuntaCronogramaED> getCronogramaPublicado(Calendar dataInicio, Calendar dataFim) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.DTH_AGENDA >= :dataInicio");
//    sql.append(" AND B.DTH_AGENDA <= :dataFim AND B.SITUACAO = 1");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dataInicio", dataInicio);
//    query.setParameter("dataFim", dataFim);
//
//    return (List<JuntaCronogramaED>) query.getResultList();
//  }
//
//  public CronogramaParametrosED atualizaParametros(CronogramaParametrosED ed) {
//    ed = super.getEntityManager().merge(ed);
//    super.getEntityManager().flush();
//    return ed;
//  }
//
//  public CronogramaParametrosED getCronogramaParametros(String abrevPropriedade) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("  select * from hab_config_cronograma_junta ");
//    sql.append("  where abrev_propriedade = :abrevPropriedade ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), CronogramaParametrosED.class);
//    query.setParameter("abrevPropriedade", abrevPropriedade);
//
//    return (CronogramaParametrosED) query.getSingleResult();
//  }
//
//  public List<JuntaCronogramaED> verificaTurnoMesmoDia(JuntaCronogramaED ed) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.SITUACAO IN (1,7) ");
//    sql.append(" AND B.COD_MUNIC = :codMunic AND B.TURNO = :turno AND B.DTH_AGENDA = :dthAgenda ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("codMunic", ed.getMunicipioED().getCodMunicipio());
//    query.setParameter("turno", ed.getTurno());
//    query.setParameter("dthAgenda", ed.getDthAgenda());
//
//    return query.getResultList();
//  }
//
//  public List<JuntaCronogramaED> getListaTotalDeTurnosNoMunicipio(Integer samdInicial, Integer samdFinal, Integer codMunicipio) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.SITUACAO NOT IN (9) ");
//    sql.append(" AND B.DTH_AGENDA >= :dthInicial AND B.DTH_AGENDA <= :dthFinal ");
//    sql.append(" AND B.COD_MUNIC = :codMunic ");
//    sql.append(" ORDER BY B.DTH_AGENDA, B.TURNO");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthInicial", Util.paraCalendar(samdInicial));
//    query.setParameter("dthFinal", Util.paraCalendar(samdFinal));
//    query.setParameter("codMunic", codMunicipio);
//
//    return query.getResultList();
//  }
//
//  public List<JuntaCronogramaED> getListaTurnosDisponiveisMunicipio(Integer samdInicial, Integer samdFinal, Integer codMunic) {
//    StringBuffer sql = new StringBuffer();
//
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.SITUACAO IN (1,7) ");
//    sql.append(" AND B.DTH_AGENDA >= :dthInicial AND B.DTH_AGENDA <= :dthFinal ");
//    sql.append(" AND B.COD_MUNIC = :codMunic ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthInicial", Util.paraCalendar(samdInicial));
//    query.setParameter("dthFinal", Util.paraCalendar(samdFinal));
//    query.setParameter("codMunic", codMunic);
//
//    return query.getResultList();
//  }
//
//  // RETORNA OS MUNICIPIOS QUE JÁ TIVERAM JUNTA
//  public List<MunicipioED> getMunicipiosQueJaTiveramJunta() {
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT DISTINCT A.COD_MUNIC FROM HAB_JUNTA_CRONOGRAMA A ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//    List<BigDecimal> listaRetorno = query.getResultList();
//    List<Integer> listaCodMunic = new ArrayList<Integer>();
//    for (BigDecimal o : listaRetorno) {
//      try {
//        listaCodMunic.add(Util.parseInteger(o));
//      } catch (ProcergsUtilException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    }
//
//    return municipioRN.getMunicipios(listaCodMunic);
//  }
//
//  // RETORNA OS MUNICIPIOS DE JUNTA
//  public List<MunicipioED> getMunicipiosDeJunta() {
//    StringBuffer sql = new StringBuffer();
//    sql.append(" SELECT  * FROM kgid36d a where a.k36_ind_mun_junta > 0 ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), DivisoesDetranED.class);
//    List<DivisoesDetranED> listaRetorno = query.getResultList();
//    List<MunicipioED> listaMunicipio = new ArrayList<MunicipioED>();
//
//    for (DivisoesDetranED ed : listaRetorno) {
//      listaMunicipio.add(ed.getMunicipioED());
//    }
//
//    return listaMunicipio;
//  }
//
//  /**
//   * Retorna os turnos que deverão ser encerrados no dia atual. Considera na busca o dia atual. Ex.: DTH_AGENDA <= :dthAgenda
//   * @param samdAtual
//   * @return
//   */
//  public List<JuntaCronogramaED> getListaCronogramaASerEncerrado(Integer samdAtual) {
//    String sql = "SELECT * FROM HAB_JUNTA_CRONOGRAMA B WHERE B.DTH_AGENDA <= :dthAgenda AND B.SITUACAO IN (1,2,7)";
//    Query query = super.getEntityManager().createNativeQuery(sql, JuntaCronogramaED.class);
//    query.setParameter("dthAgenda", Util.paraCalendar(samdAtual));
//    return (List<JuntaCronogramaED>) query.getResultList();
//  }
//
//  // Retorna todos os profissionais da comissão ativa ordenados pelo doc-ident
//  @SuppressWarnings("unchecked")
//  public List<AtividadeProfissionalED> getProfissonaisComissaoAtiva(String profsComissaoAtiva) {
//    StringBuilder sql = new StringBuilder();
//    sql.append(" SELECT * FROM KGID206D A ");
//    sql.append(" WHERE A.K206_TP_PROFIS = 29 AND A.K206_STATUS = 2");
//    sql.append(" AND A.K206_DOC_IDENT IN (" + profsComissaoAtiva + ") ");
//    sql.append(" ORDER BY TO_NUMBER(A.K206_DOC_IDENT) ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), AtividadeProfissionalED.class);
//
//    return query.getResultList();
//  }
//
//  // Retorna todos os profissionais da comissão ativa ordenados pelo doc-ident
//  @SuppressWarnings("unchecked")
//  public List<AtividadeProfissionalED> getProfissonaisQuePodemAtuarNoMes(Integer anoAtual, Integer mesAtual, String profsComissaoAtiva) {
//
//    Integer proxMes = mesAtual, ano = anoAtual;
//    proxMes++;
//
//    if (proxMes > 12) {
//      ano++;
//      proxMes = 1;
//    }
//
//    String mesPesquisa = String.valueOf(proxMes);
//    if (mesPesquisa.length() < 2)
//      mesPesquisa = "0" + mesPesquisa;
//
//    String dtInicial = anoAtual + mesPesquisa + "00";
//    StringBuilder sql = new StringBuilder();
//    sql.append(" SELECT * FROM KGID206D A ");
//    sql.append(" WHERE A.K206_TP_PROFIS = 29 AND A.K206_STATUS = 2 AND A.K206_SAMD_VEN > :dtInicial");
//    sql.append(" AND A.K206_DOC_IDENT IN (" + profsComissaoAtiva + ") ");
//    sql.append(" ORDER BY TO_NUMBER(A.K206_DOC_IDENT) ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), AtividadeProfissionalED.class);
//    query.setParameter("dtInicial", dtInicial);
//
//    return query.getResultList();
//  }
//
//  public List<CronogramaDistribuicaoMinimaProfisED> getProfQueRecebemUmTurnoAMais() {
//    StringBuilder sql = new StringBuilder();
//    sql.append(" SELECT * ");
//    sql.append(" FROM HAB_DISTR_MIN_PROFIS A");
//    sql.append(" ORDER BY A.QTD_DISTRIBUICAO_MIN ASC ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), CronogramaDistribuicaoMinimaProfisED.class);
//
//    return query.getResultList();
//  }
//
//  // RETORNA OS PROFISSIONAIS DISTRIBUIDOS NO MES ANTERIOR - SÓ SERVE PARA OS TURNOS DO INTERIOR. 
//  // PORTO ALEGRE NÃO É INSERIDO NESSA TABELA
//  public List<CronogramaDistribuidoMunicMesED> getProfSDistribuidosMesAnterior(Integer anoAtual, Integer mesAtual, Integer codMunicipio) {
//    StringBuilder sql = new StringBuilder();
//    Integer mesAnterior = mesAtual;
//
//    // Junta de Alegrete
//    if (codMunicipio.equals(97540)) {
//      mesAnterior = (mesAtual - 2);
//      // CASOS DE JANEIRO
//      if (mesAnterior <= 0) {
//        anoAtual = anoAtual - 1;
//        mesAnterior = 11;
//      }
//    }
//
//    // ORDENA OS PROFISSIONAIS PELA ORDEM DE INSERÇÃO NA TABELA
//    sql.append(" SELECT * FROM HAB_PROFIS_DIST_MUNIC_MES A ");
//    sql.append(" WHERE A.ANO = :ano AND A.MES = :mes");
//    sql.append(" AND A.COD_MUNIC_KGID32D = :codMunicipio");
//    sql.append(" ORDER BY A.NRO_INT_PROFIS_DIST_MUNIC_MES desc");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), CronogramaDistribuidoMunicMesED.class);
//    query.setMaxResults(3);
//    query.setParameter("ano", anoAtual);
//    query.setParameter("mes", mesAnterior);
//    query.setParameter("codMunicipio", codMunicipio);
//
//    return query.getResultList();
//  }
//
//  public Integer obtemHoraInicialViaHabLocalAtuacao(Integer codMunic, Integer turno) {
//
//    Query query;
//    StringBuilder sb = new StringBuilder();
//
//    sb.append(" select ");
//    if (turno == 1) {
//      sb.append(" la.hm_inicio_manha hmIni ");
//    } else if (turno == 2) {
//      sb.append(" la.hm_inicio_tarde hmIni ");
//    } else {
//      throw new RuntimeException("Turno inválido :" + turno);
//    }
//    sb.append(" from hab_local_atuacao la ");
//    sb.append(" where ");
//    sb.append(" (la.turno_atend = 'MT' ");
//    if (turno == 1) {
//      sb.append("  or la.turno_atend = 'M') ");
//    } else if (turno == 2) {
//      sb.append("  or la.turno_atend = 'T') ");
//    } else {
//      throw new RuntimeException("Turno inválido :" + turno);
//    }
//    sb.append(" and la.cod_munic_kgid32 = ").append(codMunic);
//    sb.append(" and la.situacao = 1 ");
//
//    try {
//      query = this.getEntityManager().createNativeQuery(sb.toString());
//      BigDecimal result = (BigDecimal) query.getSingleResult();
//      return result.intValue();
//    } catch (NoResultException nre) {
//      return null;
//    } catch (NonUniqueResultException nue) {
//      throw new RuntimeException("Mais de um registro LocalAtuacao para o municipio:" + codMunic + " de turno:" + turno);
//    }
//
//  }
//
//  public List<JuntaCronogramaED> getTurnosSituacao(Integer situacao) {
//    String hql = "SELECT j FROM JuntaCronogramaED j WHERE j.situacao = :situacao ORDER BY j.municipioED";
//    Query query = super.getEntityManager().createQuery(hql, JuntaCronogramaED.class);
//    query.setParameter("situacao", situacao);
//    return query.getResultList();
//  }
//
//  // Verifica no período se o profissíonal se encontra afastado no dia a ser
//  // agendado
//  public Boolean verificaProfissionalAfastado(Calendar diaAgendamento, AtividadeProfissionalED atividadeProfissionalED) {
//
//    Boolean ret = false;
//    StringBuilder sql = new StringBuilder();
//
//    sql.append("SELECT AFAST.Nro_Int_Afast_Profis_Junta FROM HAB_AFASTAMENTO_PROFIS_JUNTA AFAST ");
//    sql.append(" WHERE  AFAST.DOC_IDENT_PROFIS = :docIdent AND AFAST.UF_DOC_IDENT_PROFIS = :ufDocIdent ");
//    sql.append(" AND AFAST.SITUACAO = 0 ");
//    sql.append(" AND (:diaAgendamento >= AFAST.DTH_INICIO AND :diaAgendamento <= AFAST.DTH_FIM) ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//    query.setParameter("diaAgendamento", diaAgendamento);
//    query.setParameter("docIdent", atividadeProfissionalED.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent());
//    query.setParameter("ufDocIdent", atividadeProfissionalED.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getUfIdent());
//
//    List<Object[]> listaArrayObj = query.getResultList();
//
//    // Se retornar alguma informação retorna TRUE
//    // confirmando que o perito está no período de afastamento
//    if (listaArrayObj.size() > 0) {
//      logger.warn("*** PROFISSIONAL: " + atividadeProfissionalED.getNome() + " EM PERÍODO DE AFASTAMENTO ***");
//      ret = true;
//    }
//
//    return ret;
//  }
//
//  // BUSCA OS CRONOGRAMAS COM SITUAÇÃO: 7-EM ANÁLISE;
//  public List<JuntaCronogramaED> getListaCronogramaASerEncerrado(Calendar dthInicio, Calendar dthFim) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA A WHERE (A.DTH_AGENDA >= :dthInicio AND A.DTH_AGENDA <= :dthFim)");
//    sql.append(" AND A.SITUACAO = 7 ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthInicio", dthInicio);
//    query.setParameter("dthFim", dthFim);
//
//    return query.getResultList();
//  }
//
//  public List<JuntaCronogramaED> getCronogramaInterior(Integer situacao) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA A WHERE ");
//    sql.append(" A.SITUACAO IN (:situacao) AND A.COD_MUNIC <> 90000");
//    sql.append(" ORDER BY A.COD_MUNIC");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("situacao", situacao);
//
//    return query.getResultList();
//  }
//
//  public List<String> getEmailProfissionaisVigente() {
//    List<String> lstReturn = new ArrayList<String>();
//    StringBuilder sql = new StringBuilder();
//
//    sql.append("SELECT B.K205_EMAIL, B.K205_DOC_IDENT from KGID206D A, KGID205D B");
//    sql.append(" WHERE A.K206_TP_PROFIS = 29 AND A.K206_STATUS = 2");
//    sql.append(" AND A.K206_DOC_IDENT = B.K205_DOC_IDENT");
//    sql.append(" AND A.K206_UF_DOC_IDENT = B.K205_UF_DOC_IDENT");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString());
//
//    List<Object[]> listaArrayObj = query.getResultList();
//    if (listaArrayObj.size() > 0) {
//      for (int i = 0; i < listaArrayObj.size(); i++) {
//        if (listaArrayObj.get(i)[0] != null)
//          lstReturn.add(listaArrayObj.get(i)[0].toString());
//      }
//    }
//    return lstReturn;
//  }
//
//  public List<JuntaCronogramaED> buscaListaCronogramasComTurnosLotadosHoje(Calendar dthHoje) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA A WHERE A.DTH_LOTACAO = :dthHoje");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthHoje", dthHoje);
//
//    return query.getResultList();
//  }
//
//  public List<JuntaCronogramaED> getTurnosEmAnaliseNoPeriodo(Calendar dthInicio, Calendar dthFim) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA A WHERE A.SITUACAO IN(7) AND A.DTH_AGENDA > :dthInicio AND A.DTH_AGENDA < :dthFim ");
//
//    //ATENÇÃO! NÃO REMOVER O ORDER BY. É UTILIZADO NA REDISTRIBUIÇÃO DOS PROFISSIONAIS
//    sql.append(" ORDER BY A.COD_MUNIC, A.DTH_AGENDA, A.TURNO ASC ");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthInicio", dthInicio);
//    query.setParameter("dthFim", dthFim);
//
//    return query.getResultList();
//  }
//
//  public List<JuntaCronogramaED> getCronograma(Calendar dthInicio, Calendar dthFim) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT * FROM HAB_JUNTA_CRONOGRAMA A WHERE A.SITUACAO NOT IN(9,8,2) AND A.DTH_AGENDA >= :dthInicio AND A.DTH_AGENDA <= :dthFim");
//
//    Query query = super.getEntityManager().createNativeQuery(sql.toString(), JuntaCronogramaED.class);
//    query.setParameter("dthInicio", dthInicio);
//    query.setParameter("dthFim", dthFim);
//
//    return query.getResultList();
//  }
//
//  public Integer getQuantAgendamentos(Long nroIntJuntaCronograma) {
//    String sql = "SELECT COUNT(*) AS TOTAL FROM HAB_JUNTA_AGENDAMENTO_COND A WHERE A.NRO_INT_JUNTA_CRONOGRAMA = :nroIntJuntaCronograma";
//    Query query = super.getEntityManager().createNativeQuery(sql);
//    query.setParameter("nroIntJuntaCronograma", nroIntJuntaCronograma);
//
//    BigDecimal ret = (BigDecimal) query.getSingleResult();
//
//    return ret.intValue();
//  }
//
//  public Boolean validaProfTurnoSituacao(String docIdent, String ufDocIdent, SituacaoCronogramaEnum situacaoEnum) {
//    Integer quantidade = null;
//
//    try {
//      StringBuffer sql = new StringBuffer();
//
//      sql.append("SELECT COUNT(*) FROM HAB_JUNTA_CRONOGRAMA TURNO, HAB_AGENDA_PROFIS PROF ");
//      sql.append(" WHERE TURNO.NRO_INT_JUNTA_CRONOGRAMA = PROF.NRO_INT_AGENDA_JUNTA ");
//      sql.append(" AND TURNO.SITUACAO = :situacao ");
//      sql.append(" AND PROF.DOC_IDENT_PROFIS    = :docIdent ");
//      sql.append(" AND PROF.UF_DOC_IDENT_PROFIS = :ufDocIdent ");
//
//      Query query = super.getEntityManager().createNativeQuery(sql.toString());
//      query.setParameter("situacao", situacaoEnum.getCodigo());
//      query.setParameter("docIdent", docIdent);
//      query.setParameter("ufDocIdent", ufDocIdent);
//
//      BigDecimal bdQuantidade = (BigDecimal) query.getSingleResult();
//      quantidade = bdQuantidade.intValue();
//    } catch (NoResultException r) {
//      return false;
//    }
//
//    if (quantidade > 0) {
//      return true;
//    }
//
//    return false;
//  }
//  
////INCLUI PROFISSIONAL DISTRIBUIDO EM JUNTA DO INTERIOR
// public void incluiProfsDistInterior(CronogramaDistribuidoMunicMesED ed) {
//
//   // ADICIONA CAMPOS DE CONTROLE
//   ed.setCtrDthAtu(Calendar.getInstance());
//   ed.setCtrDthInc(Calendar.getInstance());
//   ed.setCtrNroIpAtu("127.0.0.1");
//   ed.setCtrNroIpInc("127.0.0.1");
//   ed.setCtrOrgAtu("PROCERGS");
//   ed.setCtrOrgInc("PROCERGS");
//   ed.setCtrUsuAtu(4027L);
//   ed.setCtrUsuInc(4027L);
//   ed.setCtrCodSoeInc(4027L);
//   ed.setCtrCodSoeAtu(4027L);
//
//   super.getEntityManager().merge(ed);
// }
//
// //--------------------------------- 

}