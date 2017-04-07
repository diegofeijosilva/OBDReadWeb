package com.obdread.usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.obdread.ed.UsuarioED;
import com.obdread.exception.RNException;
import com.obdread.infra.AppRN;
import com.obdread.util.UtilRN;

@Stateless
public class UsuarioRN extends AppRN<UsuarioED, Long> {

  private static final long serialVersionUID = 1L;

  @Inject
  UsuarioBD                 bd;

  @Inject
  UtilRN                    utilRN;

  @Inject
  public void setBD(UsuarioBD bd) {
    super.setBD(bd);
  }

  @Override
  public UsuarioED inclui(UsuarioED ed) {
    if (ed.getEmail() == null || ed.getSenha() == null)
      throw new RNException("CAMPO EMAIL E SENHA OBRIGATÓRIOS!");

    ed.setEmail(ed.getEmail().toLowerCase().trim());

    ed.setSenha(utilRN.convertStringToSHA256(ed.getSenha()));
    ed.setTicket(utilRN.geradorTicket(ed));
    return super.inclui(ed);
  }

  @Override
  public UsuarioED altera(UsuarioED ed) {
    if (ed.getEmail() == null || ed.getSenha() == null)
      throw new RNException("CAMPO EMAIL E SENHA OBRIGATÓRIOS!");

    ed.setEmail(ed.getEmail().toLowerCase().trim());

    ed.setSenha(utilRN.convertStringToSHA256(ed.getSenha()));
    ed.setTicket(utilRN.geradorTicket(ed));
    return super.altera(ed);
  }

  public UsuarioED isUsuarioReadyToLogin(String email, String senha) {
    email.toLowerCase().trim();
    senha = utilRN.convertStringToSHA256(senha);
 //   logger.info("Verificando login do usuário " + email);

    return bd.findByEmailSenha(email, senha);

  }
  
  public UsuarioED bucaUsuarioTicket(String userTicket) {
    if (userTicket == null || userTicket.equals(""))
      throw new RNException("Ticket é obrigatório!");

    return bd.bucaUsuarioTicket(userTicket);
  }

  //  @Inject
  //  CronogramaDistribuicaoMinimaProfisBD cronogramaDistribuicaoMinimaProfisBD;
  //
  //  @Inject
  //  JuntaAgendaProfissionalRN            agendaProfRN;
  //
  //  @Inject
  //  JuntaAgendaHistoricoRN               juntaAgHistRN;
  //
  //  @Inject
  //  CronogramaDistribuicaoMinimaProfisRN distMinProfs;
  //
  //  @Inject
  //  CronogramaAgendaProfissionalSubsRN   agendaProfsSubsRN;
  //
  //  @Inject
  //  SequencialGenericoRN                 seqGenericoRN;
  //
  //  @Inject
  //  RenachRN                             renachRN;
  //
  //  @Inject
  //  JuntaComissaoRN                      juntaComissaoRN;
  //
  //  @Inject
  //  ComissaoLocalAtuacaoBD               localAtuacaoBD;
  //
  //  @Inject
  //  AgendaSemanalJuntaRN                 agendaRN;
  //
  //  @Inject
  //  CredenciadoRN                        credenciadoRN;
  //
  //  @Inject
  //  JuntaProfissionalDistribuidoMunicRN  juntaProfisDistRN;
  //
  //  @Inject
  //  SessionMB                            sessionMB;
  //
  //  @Inject
  //  HABProperties                        propriedades;
  //
  //  @Inject
  //  Logger                               logger;
  //
  //  @EJB
  //  JuntaAgendamentoCondutorRN           juntaAgCondRN;
  //
  //  @Inject
  //  JuntaCronogramaTwoFaseRN             juntaCronogramaTwoFaseRN;
  //
  //  @Inject
  //  JuntaQuantitativoProfisRN            juntaQuantProfisRN;
  //  
  //  @Inject
  //  AtividadeProfissionalRN              atividadeProfissionalRN;
  //
  //  public Logger getLogger() {
  //    return logger;
  //  }
  //
  //  public void setLogger(Logger logger) {
  //    this.logger = logger;
  //  }
  //
  //  @Inject
  //  public void setBD(UsuarioBD bd) {
  //    super.setBD(bd);
  //  }
  //
  //  @Override
  //  @Permissao(objeto = "H113", acao = "")
  //  public int conta(JuntaCronogramaED ped) {
  //    return super.conta(ped);
  //  }
  //
  //  @Override
  //  @Permissao(objeto = "H113", acao = "")
  //  public JuntaCronogramaED consulta(Long id) {
  //    return super.consulta(id);
  //  }
  //
  //  @Override
  //  @Permissao(objeto = "H113", acao = "")
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> lista(JuntaCronogramaED ped) {
  //    return bd.lista(ped);
  //  }
  //
  //  @Override
  //  @Permissao(sistema = "GID", objeto = "H115", acao = "I")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public JuntaCronogramaED inclui(JuntaCronogramaED ed) {
  //
  //    this.validaCamposInclui(ed);
  //
  //    // Será permitido criar turnos somente para datas futuras.
  //    if (Util.calendarParaYYYYMMDD(ed.getDthAgenda()) < Util.calendarParaYYYYMMDD(Calendar.getInstance()))
  //      throw new RNException("Atenção! Não é permitido a criação de turnos com data menor que atual.");
  //
  //    // VERIFICA SE O TURNO QUE SE ESTÁ CRIANDO JÁ EXISTE
  //    if (bd.verificaCronogramaExiste(ed))
  //      throw new RNException("Atenção! Já existe cronograma com mesma data, município e turno informados.");
  //
  //    // VALIDA O TURNO E HORA INFORMADOS
  //    this.validaHoraTurno(ed);
  //
  //    // bd ira usar sequence
  //    ed.setNroIntJuntaCronograma(null);
  //
  //    if (ed.getHmInicio() == null)
  //      ed.setHmInicio(HabWebUtil.horaParaHM(ed.getHoraInicio()));
  //
  //    ed.setAtendAgend(0);
  //    ed.setDthLotacao(null);
  //
  //    ed.setSituacao(SituacaoCronogramaEnum.EM_ANALISE.getCodigo());
  //    // AO INCLUIR, O PRÓXIMO HORÁRIO É O MESMO DO HORA INICIO
  //    ed.setHmPrx(ed.getHmInicio());
  //
  //    if (ed.getAtendExtra() == null)
  //      ed.setAtendExtra(0);
  //
  //    ed = super.inclui(ed);
  //
  //    // Gerar registro na Agenda do profissional: HAB_AGENDA_PROFIS
  //    for (JuntaCronogramaProfsED agProf : ed.getListaCronogramaProfs()) {
  //      agProf.setNroIntAgendaJunta(ed.getId());
  //      agProf.setDthAgenda(ed.getDthAgenda());
  //      agProf.setTurno(ed.getTurno());
  //      agProf.setSituacao(1); // SITUACAO = 1 - ATIVO
  //      agProf.setTipoProfis(29);
  //      agProf.setProfissionalED(null); //removido para não dar erro cascade na inclusao
  //      agendaProfRN.inclui(agProf);
  //
  //      // - Decrementar 1 da QTD_DISTRIBUICAO_MIN na tabela HAB_DISTR_MIN_PROFIS dos profissionais
  //      //  distMinProfs.decrementaDistribuicaoMinima(agProf.getDocIdentProfis(), agProf.getUfDocIdentProfis());
  //    }
  //
  //    this.incluiTurnoHistorico(ed.getId());
  //
  //    juntaCronogramaTwoFaseRN.inclui(ed);
  //    return ed;
  //  }
  //
  //  private void validaCamposInclui(JuntaCronogramaED ed) {
  //    if (ed.getMunicipioED() == null)
  //      throw new RNException("Favor Informar o Municipio.");
  //
  //    if (ed.getCredenciadoED() == null)
  //      throw new RNException("Favor Informar o Credenciado.");
  //
  //    if (ed.getHmInicio() == 0)
  //      throw new RNException("Favor Informar a Hora de Início.");
  //  }
  //
  //  @Permissao(objeto = "H113", acao = "")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  private void incluiTurnoHistorico(Long nroIntAgendaJunta) {
  //    JuntaCronogramaHistoricoED histJuntaED = new JuntaCronogramaHistoricoED();
  //    histJuntaED.setNroIntAgendaJunta(nroIntAgendaJunta);
  //    histJuntaED.setCodEvento(EventoJuntaEnum.INCLUSAO_MANUAL_CRONOGRAMA_JM.getCodigo());
  //    histJuntaED.setTexto(juntaAgHistRN.montaConteudoHistorico("Em análise", null, "Inclusão Manual"));
  //    juntaAgHistRN.inclui(histJuntaED);
  //  }
  //
  //  @Override
  //  @Permissao(objeto = "H115", acao = "C")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public JuntaCronogramaED altera(JuntaCronogramaED ed) {
  //
  //    // PEGA OS DADOS EM BANCO
  //    JuntaCronogramaED edAnterior = bd.consulta(ed.getId());
  //
  //    // VALIDA O TURNO E HORA INFORMADOS
  //    this.validaHoraTurno(ed);
  //
  //    // A alteração pode ser realizada quando cronograma nas situações
  //    // "Em análise = 7", "Disponível = 1" e "Lotado = 2"
  //    if (edAnterior.getSituacao().equals(SituacaoCronogramaEnum.CANCELADO.getCodigo()) || edAnterior.getSituacao().equals(SituacaoCronogramaEnum.ENCERRADO.getCodigo())) {
  //      throw new RNException("Cronograma com situação '" + SituacaoCronogramaEnum.get(ed.getSituacao()) + "' não pode ser alterado!");
  //    }
  //
  //    /*
  //     - Pode ser alterado: turno, hora início, máx. atendimento, atend.
  //     extra, credenciado e profissionais. Se já existirem condutores
  //     agendados, não permitir alterar turno e hora início.
  //     Para consultar se existem condutores agendados, acessar a hab_junta_agendamento_cond
  //     pelo SEQ_AGENDA e SITUACAO = 1
  //     */
  //
  //    int condutoresAgendados = juntaAgCondRN.getCondutoresAgendadosNoTurno(ed.getId()).size();
  //    if (condutoresAgendados > 0) {
  //      // BUSCA O REGISTRO NO BANCO PARA VERIFICAR SE HOUVE ALTERAÇÃO
  //      JuntaCronogramaED cronogramaED = bd.consulta(ed.getId());
  //
  //      if (!cronogramaED.getTurno().equals(ed.getTurno())) {
  //        throw new RNException("O Cronograma possui " + condutoresAgendados + " condutores agendados. O turno não pode ser alterado!");
  //      }
  //      if (!cronogramaED.getHoraInicio().equals(ed.getHoraInicio())) {
  //        throw new RNException("O Cronograma possui " + condutoresAgendados + " condutore(s) agendado(s). A Hora de início não pode ser alterada!");
  //      }
  //    }
  //
  //    JuntaCronogramaProfsED agProfsEDAnterior;
  //    JuntaCronogramaProfsED agProfsEDNovo;
  //    List<JuntaCronogramaProfsED> listaProfsCronograma = agendaProfRN.getListaProfissionaisVinculadosAoCronograma(ed.getId());
  //    //
  //    //    // #118019 - ESSE CASO RESOLVE O PROBLEMA QUANDO É CRIADO UM NOVO TURNO AUTOMATICAMENTE SEM OS 3 PROFISSIONAIS
  //    //    if (listaProfsCronograma.size() == 0) {
  //    //      if (ed.getListaCronogramaProfs().size() != 3) {
  //    //        throw new RNException("Atenção! Favor verificar se os 3 profissionais foram informados corretamente.");
  //    //      }
  //    //      for (JuntaCronogramaProfsED juntaAgendaProfissionalED : ed.getListaCronogramaProfs()) {
  //    //        this.incluiProfissionalAgenda(ed, juntaAgendaProfissionalED);
  //    //      }
  //    //    }
  //
  //    // TESTA PROFISSONAL 01
  //    if (listaProfsCronograma.size() > 0 && ed.getListaCronogramaProfs().size() > 0) {
  //      agProfsEDAnterior = listaProfsCronograma.get(0);
  //      agProfsEDNovo = ed.getListaCronogramaProfs().get(0);
  //
  //      if (!agProfsEDAnterior.getDocIdentProfis().equals(agProfsEDNovo.getDocIdentProfis())) {
  //        // HOUVE ALTERAÇÃO
  //        // Valida se o profissional é DETRAN ou DESDETRA
  //        SessionED sessionED = dadosSessaoUtil.montaDadosSessao();
  //        if (!sessionED.getSOE_SiglaOrganizacao().equals("DETRAN") && !sessionED.getSOE_SiglaOrganizacao().equals("DESDETRA")) {
  //          throw new RNException("Atenção! A troca de profissionais só é permitida para os usuários do DETRAN.");
  //        }
  //        // DESATIVA O PROFISSIONAL ANTERIOR
  //        agProfsEDAnterior.setSituacao(0);
  //        agendaProfRN.altera(agProfsEDAnterior);
  //        this.incluiProfissionalAgenda(ed, agProfsEDNovo);
  //        // INSERI O PROFISSIONAL SUBSTITUTO
  //        this.incluiSubstituicoes(agProfsEDAnterior, agProfsEDNovo, ed);
  //      }
  //    }
  //
  //    // TESTA PROFISSONAL 02
  //    if (listaProfsCronograma.size() > 1 && ed.getListaCronogramaProfs().size() > 1) {
  //      agProfsEDAnterior = listaProfsCronograma.get(1);
  //      agProfsEDNovo = ed.getListaCronogramaProfs().get(1);
  //
  //      if (!agProfsEDAnterior.getDocIdentProfis().equals(agProfsEDNovo.getDocIdentProfis())) {
  //        // HOUVE ALTERAÇÃO
  //        // Valida se o profissional é DETRAN ou DESDETRA
  //        SessionED sessionED = dadosSessaoUtil.montaDadosSessao();
  //        if (!sessionED.getSOE_SiglaOrganizacao().equals("DETRAN") && !sessionED.getSOE_SiglaOrganizacao().equals("DESDETRA")) {
  //          throw new RNException("Atenção! A troca de profissionais só é permitida para os usuários do DETRAN.");
  //        }
  //        // DESATIVA O PROFISSIONAL ANTERIOR
  //        agProfsEDAnterior.setSituacao(0);
  //        agendaProfRN.altera(agProfsEDAnterior);
  //        this.incluiProfissionalAgenda(ed, agProfsEDNovo);
  //        // INSERI O PROFISSIONAL SUBSTITUTO
  //        this.incluiSubstituicoes(agProfsEDAnterior, agProfsEDNovo, ed);
  //      }
  //    }
  //
  //    // TESTA PROFISSONAL 03
  //    if (listaProfsCronograma.size() > 2 && ed.getListaCronogramaProfs().size() > 2) {
  //      agProfsEDAnterior = listaProfsCronograma.get(2);
  //      agProfsEDNovo = ed.getListaCronogramaProfs().get(2);
  //
  //      if (!agProfsEDAnterior.getDocIdentProfis().equals(agProfsEDNovo.getDocIdentProfis())) {
  //        // HOUVE ALTERAÇÃO
  //        // Valida se o profissional é DETRAN ou DESDETRA
  //        SessionED sessionED = dadosSessaoUtil.montaDadosSessao();
  //        if (!sessionED.getSOE_SiglaOrganizacao().equals("DETRAN") && !sessionED.getSOE_SiglaOrganizacao().equals("DESDETRA")) {
  //          throw new RNException("Atenção! A troca de profissionais só é permitida para os usuários do DETRAN.");
  //        }
  //        // DESATIVA O PROFISSIONAL ANTERIOR
  //        agProfsEDAnterior.setSituacao(0);
  //        agendaProfRN.altera(agProfsEDAnterior);
  //        this.incluiProfissionalAgenda(ed, agProfsEDNovo);
  //        // INSERI O PROFISSIONAL SUBSTITUTO
  //        this.incluiSubstituicoes(agProfsEDAnterior, agProfsEDNovo, ed);
  //      }
  //    }
  //
  //    /*
  //     * Quando for editado um cronograma que está lotado, e aumentarem o
  //     * número de atendimentos extras ou de atendimentos normais e passar a
  //     * ter saldo positivo para atendimentos, o cronograma tem que passar
  //     * para disponível novamente
  //     */
  //
  //    // SE ESTÁ LOTADO
  //    if (ed.getSituacao().equals(2)) {
  //      if ((ed.getAtendExtra() > edAnterior.getAtendExtra()) || ed.getAtendMax() > edAnterior.getAtendMax()) {
  //        // PASSA PARA DISPONIVEL
  //        ed.setSituacao(SituacaoCronogramaEnum.DISPONIVEL.getCodigo());
  //      }
  //    }
  //
  //    // VERIFICA SE FOI INFORMADO TURNO_EXTRA
  //    if (ed.getAtendExtra() == null)
  //      ed.setAtendExtra(0);
  //
  //    // SE ESTIVER EM ANÁLISE E FOR ALTERADO O HORÁRIO DE INICIO
  //    // ATUALIZA O PRÓXIMO HORÁRIO.
  //    if (ed.getSituacao().equals(7)) {
  //      ed.setHmPrx(ed.getHmInicio());
  //    }
  //
  //    juntaCronogramaTwoFaseRN.altera(ed);
  //    return super.altera(ed);
  //  }
  //
  //  // INCLUI O NOVO PROFISSIONAL
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  private void incluiProfissionalAgenda(JuntaCronogramaED ed, JuntaCronogramaProfsED agProfsEDNovo) {
  //    JuntaCronogramaProfsED agendaProfs = agendaProfRN.consulta(agProfsEDNovo.getNroIntAgendaJunta());
  //
  //    agProfsEDNovo.setDthAgenda(ed.getDthAgenda());
  //    agProfsEDNovo.setTurno(ed.getTurno());
  //    agProfsEDNovo.setTipoProfis(29);
  //    agProfsEDNovo.setSituacao(1); // ATIVO
  //
  //    // SENÃO EXISTE O REGISTRO INCLUI
  //    if (agendaProfs == null) {
  //      agendaProfRN.inclui(agProfsEDNovo);
  //    } else { // senão atualiza
  //      agendaProfRN.altera(agProfsEDNovo);
  //    }
  //  }
  //
  //  @Permissao(objeto = "H113", acao = "")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  private void incluiSubstituicoes(JuntaCronogramaProfsED profAnterior, JuntaCronogramaProfsED profSubs, JuntaCronogramaED ed) {
  //    // - Deve ser incrementado o QTD_DISTR_MIN_PROFIS do
  //    // profissional que foi substituído e decrementar o
  //    // QTD_DISTR_MIN_PROFIS do profissional que fará a
  //    // substituição (tabela HAB_DISTR_MIN_PROFIS).
  //    distMinProfs.incrementaDistribuicaoMinima(profAnterior.getDocIdentProfis(), profAnterior.getUfDocIdentProfis());
  //    distMinProfs.decrementaDistribuicaoMinima(profSubs.getDocIdentProfis(), profSubs.getUfDocIdentProfis());
  //
  //    // - Ao realizar uma troca, deve ser gerado registro no
  //    // KGID220D com TIPO_SUB = 1, sem informar MOTIVO.
  //    // Também deverá ser atualizada a agenda dos
  //    // profissionais envolvidos (setar 1 na SITUACAO do
  //    // antigo e criar registro para o novo)
  //    CronogramaAgendaProfissionalSubsED edSubs = new CronogramaAgendaProfissionalSubsED();
  //    edSubs.setNroIntAgendaJunta(ed.getId());
  //    edSubs.setTipoSubs(1);
  //    edSubs.setMotivo(0);
  //
  //    // PROFISSIONAL ANTERIOR
  //    edSubs.setProfissionalAntED(profAnterior.getProfissionalED());
  //    // NOVO PROFISSIONAL
  //    edSubs.setProfissionalSubsED(profSubs.getProfissionalED());
  //
  //    // INSERE A SUBSTITUIÇÃO NA HAB_SUBSTITUICAO_JUNTA
  //    agendaProfsSubsRN.inclui(edSubs);
  //
  //    this.atualizaQtdDistribuidaProfissionalAnteriorAtual(profAnterior, profSubs, ed);
  //  }
  //
  //  private void atualizaQtdDistribuidaProfissionalAnteriorAtual(JuntaCronogramaProfsED profAnterior, JuntaCronogramaProfsED profSubs, JuntaCronogramaED ed) {
  //    //    // - Se a troca de profissionais ocorrer após a
  //    //    // publicação do turno (situação "Disponível" ou
  //    //    // "Lotado"), deve ser decrementado o QTD_ATUJUNTA
  //    //    // () do profissional antigo e incrementado o do
  //    //    // profissional novo. Buscar pelo COD_MUNIC,
  //    //    // COD_COMISSAO, TP_COMISSAO e dados do profissional.
  //    //    if (ed.getSituacao() == SituacaoCronogramaEnum.DISPONIVEL.getCodigo() || ed.getSituacao() == SituacaoCronogramaEnum.LOTADO.getCodigo()) {
  //    //      // O ANTERIOR DECREMENTA QTD_ATUJUNTA
  //    //      this.decremetaQteDistribuidaProfissional(profAnterior, ed);
  //    //      // O SUBSTITUTO INCREMENTA QTD_ATUJUNTA
  //    //    //  this.incrementaQteDistribuidaProfissional(profSubs, ed);
  //    //    }
  //  }
  //
  //  /**
  //   * Método que realiza a Publicação do Cronograma.
  //   * 
  //   * @param JuntaCronogramaED ed
  //   */
  //  @Permissao(sistema = "GID", objeto = "CRONOGRAMA-JUN", acao = "PUB")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void publicarCronograma(JuntaCronogramaED ed) {
  //
  //    // RECEBE A LISTA DE PROFISSIONAIS DO TURNO
  //    List<JuntaCronogramaProfsED> listaProfs = ed.getListaCronogramaProfs();
  //
  //    // Realiza a publicação somente se o cronograma estiver EM ANÁLISE
  //    if (ed.getSituacao() != SituacaoCronogramaEnum.EM_ANALISE.getCodigo()) {
  //      throw new RNException("Publicação deve ocorrer somente para cronograma com situação 'EM ANÁLISE'");
  //    }
  //
  //    // Altera situação do cronograma
  //    ed.setSituacao(SituacaoCronogramaEnum.DISPONIVEL.getCodigo());
  //    ed = this.altera(ed);
  //
  //    ed.setListaCronogramaProfs(listaProfs);
  //
  //    this.geraHistoricoJunta(ed, EventoJuntaEnum.PUBLICACAO_MANUAL_CRONOGRAMA_JM.getCodigo(), "Disponível", "Publicado");
  //
  //    // INCREMENTA A QUANTIDADE DISTRIBUÍDA NA TABELA HAB_JUNTA_QUANTITATIVO_PROFIS
  //    this.incrementaQteDistribuidaProfissional(ed);
  //
  //    // Por fim, chama o método para o envio de e-mail
  //    this.enviarEmailPublicacaoCronograma(ed.getListaCronogramaProfs(), ed);
  //
  //  }
  //
  //  // RETORNA OS PROFISSIONAIS ATRAVÉS DO FILTRO NOME
  //  @Permissao(desabilitada = true)
  //  public List<AtividadeProfissionalED> getProfissionaisPorNome(String query) {
  //    return this.bd.getProfissionaisPorNome(query);
  //  }
  //
  //  // RETORNA OS PROFISSIONAIS ATRAVÉS DO FILTRO POR DOCUMENTO
  //  @Permissao(desabilitada = true)
  //  public AtividadeProfissionalED getProfissionalPorDocumento(Integer tpProfis, String docIdentProfis, String ufDocIdentProfis) {
  //    return bd.getProfissionalPorDocumento(tpProfis, docIdentProfis, ufDocIdentProfis);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void geraHistoricoJunta(JuntaCronogramaED ed, Integer codEvento, String situacao, String motivo) {
  //    JuntaCronogramaHistoricoED edHist = new JuntaCronogramaHistoricoED();
  //    edHist.setNroIntAgendaJunta(ed.getId());
  //    edHist.setCodEvento(codEvento);
  //    edHist.setTexto(juntaAgHistRN.montaConteudoHistorico(situacao, null, dadosSessaoUtil.montaDadosSessao().getSOE_SiglaOrganizacao() + "/" + dadosSessaoUtil.montaDadosSessao().getSOE_Matricula()));
  //    juntaAgHistRN.inclui(edHist);
  //  }
  //
  //  /**
  //   * Método que realiza o Cancelamento do Cronograma, ajustando os campos de
  //   * controle de distribuição e enviando e-mail de aviso aos envolvidos sobre
  //   * o Cancelamento.
  //   * 
  //   * @param ed
  //   * @param motivoCancelamento
  //   * @return cronogramaED ed
  //   */
  //  @Permissao(sistema = "GID", objeto = "H115", acao = "E")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public JuntaCronogramaED cancelaTurno(JuntaCronogramaED ed, String motivoCancelamento) {
  //    if (motivoCancelamento == null || motivoCancelamento == "")
  //      throw new RNException("Atenção! Favor informar o motivo do cancelamento do turno.");
  //
  //    if (ed.getSituacao() == SituacaoCronogramaEnum.CANCELADO.getCodigo())
  //      throw new RNException("Atenção! O turno já está cancelado.");
  //
  //    boolean houveSubstituicao = false;
  //    List<JuntaCronogramaProfsED> profisCronogramaOriginais = new ArrayList<JuntaCronogramaProfsED>();
  //    List<JuntaCronogramaProfsED> profisCronograma = new ArrayList<JuntaCronogramaProfsED>();
  //    Integer situacaoAnterior = ed.getSituacao();
  //
  //    ed.setSituacao(SituacaoCronogramaEnum.CANCELADO.getCodigo());
  //
  //    // GERA HISTÓRIO NA HAB_AGENDA_JUNTA_HISTORICO
  //    this.geraHistoricoJunta(ed, 2, "Cancelamento", motivoCancelamento);
  //
  //    // Para saber se houve substituição, é necessário buscar na KGID220D com
  //    // SITUACAO = 0 e SEQ_AGENDA
  //    List<CronogramaAgendaProfissionalSubsED> substituicoes = agendaProfsSubsRN.getSubsTrocasAgenda(ed.getId());
  //
  //    if (substituicoes != null && substituicoes.size() > 0) {
  //      houveSubstituicao = true;
  //    }
  //
  //    // Se a Situação é diferente de 'EM ANALISE'
  //    if (!situacaoAnterior.equals(SituacaoCronogramaEnum.EM_ANALISE.getCodigo())) {
  //      if (houveSubstituicao) {
  //        profisCronograma = agendaProfRN.getListaProfissionaisVinculadosAoCronograma(ed.getId());
  //        for (JuntaCronogramaProfsED profis : profisCronograma) {
  //          JuntaCronogramaProfsED novoProfis = this.verificaTrocaSubstituicao(profis, substituicoes, ed.getId());
  //          profisCronogramaOriginais.add(novoProfis);
  //        }
  //      } else {
  //        profisCronogramaOriginais = agendaProfRN.getListaProfissionaisVinculadosAoCronograma(ed.getId());
  //      }
  //      this.atribuirControlesDistribuicaoProfisNoCancelamento(profisCronogramaOriginais, ed);
  //    }
  //
  //    if (!situacaoAnterior.equals(SituacaoCronogramaEnum.EM_ANALISE.getCodigo())) {
  //      this.enviarEmailCancelamento(profisCronogramaOriginais, motivoCancelamento, ed);
  //    }
  //
  //    if (ed.getListaCronogramaProfs() == null || ed.getListaCronogramaProfs().size() == 0)
  //      ed.setListaCronogramaProfs(agendaProfRN.getListaProfissionaisVinculadosAoCronograma(ed.getId()));
  //
  //    // Cancela a agenda dos profissionais
  //    agendaProfRN.cancelarAgendaProfissionais(ed.getListaCronogramaProfs());
  //    JuntaCronogramaED retorno = this.altera(ed);
  //    return retorno;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private void enviarEmailCancelamento(List<JuntaCronogramaProfsED> profisCronograma, String motivoCancelamento, JuntaCronogramaED cronogramaED) {
  //
  //    if (profisCronograma == null || profisCronograma.size() == 0) {
  //      throw new RNException("Atenção! É preciso informar os profissionais para efetivar o cancelamento do turno.");
  //    }
  //
  //    List<String> destinatarios = new ArrayList<String>();
  //    List<String> comCopia = new ArrayList<String>();
  //
  //    List<Long> listaCodIndivDosAgendadosNosCFCs = new ArrayList<Long>();
  //    List<String> listaCodClientes = new ArrayList<String>();
  //    List<String> listaEmailsClientes = new ArrayList<String>();
  //
  //    destinatarios.add("cpm-juntas@detran.rs.gov.br");
  //
  //    // Busca Email dos Profissionais
  //    for (JuntaCronogramaProfsED profis : profisCronograma) {
  //      String email = bd.buscaEmailDoProfissional(profis.getDocIdentProfis(), profis.getUfDocIdentProfis());
  //      if (email != null) {
  //        comCopia.add(email);
  //      }
  //    }
  //
  //    // Busca Email dos CFC's
  //    listaCodIndivDosAgendadosNosCFCs = bd.buscaCodIndivDosCandidatosAgendados(cronogramaED.getId());
  //    String filtroParaConsultaClientes = Util.geraListaParaClausulaIN(listaCodIndivDosAgendadosNosCFCs);
  //
  //    if (filtroParaConsultaClientes == null || filtroParaConsultaClientes.isEmpty()) {
  //      filtroParaConsultaClientes = "0";
  //    }
  //
  //    listaCodClientes = bd.buscaListaDeCodClienteFromConjuntoDeCodIndiv(filtroParaConsultaClientes);
  //
  //    String filtroParaConsultaEmailCliente = Util.geraListaParaClausulaIN(listaCodClientes);
  //    listaEmailsClientes = bd.buscaEmailsDosCredenciados(filtroParaConsultaEmailCliente);
  //
  //    if (listaEmailsClientes != null) {
  //      // Adiciona os E-mails de cada Cliente com agendamentos
  //      for (String email : listaEmailsClientes) {
  //        if (email != null && !email.equals(" ")) {
  //          comCopia.add(email);
  //        }
  //      }
  //    }
  //
  //    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  //    String dataFormatada = sdf.format(cronogramaED.getDthAgenda().getTime());
  //
  //    // Por Fim, Envia o e-mail para os destinatários
  //    HabWebUtil.enviaEmail(destinatarios, comCopia, null, "Cancelamento de Junta", "A Junta Médica Especial, prevista para a cidade de " + cronogramaED.getMunicipioED().getNomeMunicipio() + ", " + "no dia " + dataFormatada + ", no turno da " + cronogramaED.getTurnoFormatado() + ", foi cancelada, pelo motivo abaixo: " + motivoCancelamento + ".", null);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  private JuntaCronogramaProfsED verificaTrocaSubstituicao(JuntaCronogramaProfsED profis, List<CronogramaAgendaProfissionalSubsED> substituicoes, Long seqAgenda) {
  //    for (int i = substituicoes.size() - 1; i >= 0; i--) {
  //      if (profis.getDocIdentProfis().equals(substituicoes.get(i).getProfissionalSubsED().getProfissionaisPK().getDocIdent())) {
  //        profis.setDocIdentProfis(substituicoes.get(i).getProfissionalSubsED().getProfissionaisPK().getDocIdent());
  //        profis.setUfDocIdentProfis(substituicoes.get(i).getProfissionalSubsED().getProfissionaisPK().getUfIdent());
  //      }
  //    }
  //    return profis;
  //  }
  //
  //  /**
  //   * Método que realiza incremento/decremento nos valores de quantidade de
  //   * atuações e na quantidade de distribuição mínima dos profissionais no
  //   * CANCELAMENTO de um cronograma.
  //   * 
  //   * @param profisCronograma
  //   * @param ed
  //   */
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private void atribuirControlesDistribuicaoProfisNoCancelamento(List<JuntaCronogramaProfsED> profisCronograma, JuntaCronogramaED ed) {
  //
  //    // Para cada Profissional:
  //    for (JuntaCronogramaProfsED profis : profisCronograma) {
  //
  //      //      CronogramaQtdeAtuacoesPK id = new CronogramaQtdeAtuacoesPK();
  //      //      id.setCodComissao(ed.getCodComissao());
  //      //      id.setTpComissao(ed.getTpComissao());
  //      //      id.setCodMunic(ed.getMunicipioED().getCodMunicipio());
  //      //
  //      //      id.setDocIdent(profis.getDocIdentProfis());
  //      //      id.setUfDocIdent(profis.getUfDocIdentProfis());
  //      //
  //      //      // Decrementa a quantidade do campo  -> QTDATU_JUNTA
  //      //     // cronogramaQtdeAtuacoesBD.decrementaQtdeAtuacoes(id);
  //      //
  //      //      // Incrementar QTD_DISTRIBUICAO_MIN (HAB_DISTR_MIN_PROFIS)
  //      //      cronogramaDistribuicaoMinimaProfisBD.incrementaDistribuicaoMinima(profis.getDocIdentProfis(), profis.getUfDocIdentProfis());
  //
  //    }
  //  }
  //
  //  // RETORNA A LISTA COM AS SUBSTITUIÇÕES
  //  @Permissao(desabilitada = true)
  //  public List<CronogramaAgendaProfissionalSubsED> getListaSubstituicoes(Long seqAgenda) {
  //    return agendaProfsSubsRN.getSubsAgenda(seqAgenda);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public Boolean verifidaDiaUtileFeriado(String data) {
  //
  //    Boolean diaUtil = HabWebUtil.isDiaUtil(data);
  //
  //    Boolean verificaFeriado = HabWebUtil.verificaFeriado(data);
  //
  //    if (diaUtil || verificaFeriado)
  //      return true;
  //
  //    return false;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public Boolean validaDataEncerramentoComissao(JuntaComissaoED juntaComissaoED, Integer samd) {
  //    JuntaComissaoED ed = juntaComissaoRN.getJuntaComissao(juntaComissaoED);
  //    if (ed != null)
  //      if (samd > Util.calendarParaYYYYMMDD(ed.getDthEncerramento()))
  //        return true;
  //
  //    return false;
  //  }
  //
  //  /**
  //   * Monta o e-mail de aviso de publicação de uma nova junta/cronograma, para
  //   * envio de e-mail de aviso sobre a publicação.
  //   * 
  //   * @param profisCronograma
  //   * @param cronogramaED
  //   */
  //  @Permissao(desabilitada = true)
  //  private void enviarEmailPublicacaoCronograma(List<JuntaCronogramaProfsED> profisCronograma, JuntaCronogramaED cronogramaED) {
  //
  //    List<String> destinatarios = new ArrayList<String>();
  //    List<String> comCopia = new ArrayList<String>();
  //    List<String> listaCodClientes = new ArrayList<String>();
  //    List<String> listaEmailsClientes = new ArrayList<String>();
  //
  //    // TODO: Descomentar as 3 primeiras linhas e comentar as duas linhas
  //    // seguintes, após os TESTES!
  //    destinatarios.add("cpm-juntas@detran.rs.gov.br");
  //    destinatarios.add("hab-cpm@detran.rs.gov.br");
  //    destinatarios.add("cet@detran.rs.gov.br");
  //    destinatarios.add("tiago-rabuske@procergs.rs.gov.br");
  //    destinatarios.add("diego-feijo@procergs.rs.gov.br");
  //
  //    if (profisCronograma != null && profisCronograma.size() > 0) {
  //      // Busca Email dos Profissionais <e-mail dos 3 médicos peritos
  //      // alocados>
  //      for (JuntaCronogramaProfsED profis : profisCronograma) {
  //        String email = bd.buscaEmailDoProfissional(profis.getDocIdentProfis(), profis.getUfDocIdentProfis());
  //        if (email != null) {
  //          comCopia.add(email);
  //        }
  //      }
  //    }
  //
  //    // Busca Email da Empresa (para o local de atuação) da Junta/Cronograma
  //    listaCodClientes.add(cronogramaED.getCredenciadoED().getCodCliente());
  //    String filtroParaConsultaEmailCliente = Util.geraListaParaClausulaIN(listaCodClientes);
  //    listaEmailsClientes = bd.buscaEmailsDosCredenciados(filtroParaConsultaEmailCliente);
  //
  //    if (listaEmailsClientes != null && listaEmailsClientes.size() > 0) {
  //      // Adiciona os E-mails de cada Cliente com agendamentos
  //      for (String email : listaEmailsClientes) {
  //        if (email != null && !email.equals(" ")) {
  //          // TODO -> Remover comentário da linha abaixo para
  //          // encaminhar e-mails aos credenciados
  //          comCopia.add(email);
  //        }
  //      }
  //    }
  //
  //    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  //    String dataFormatada = sdf.format(cronogramaED.getDthAgenda().getTime());
  //
  //    String turno = "";
  //    if (cronogramaED.getTurno().equals(1)) {
  //      turno = "Manhã";
  //    } else if (cronogramaED.getTurno().equals(2)) {
  //      turno = "Tarde";
  //    } else if (cronogramaED.getTurno().equals(3)) {
  //      turno = "Noite";
  //    }
  //
  //    // Por Fim, Envia o e-mail para os destinatários
  //    HabWebUtil.enviaEmail(destinatarios, comCopia, null, "Novo turno para atendimento da Junta Médica",
  //        "Conforme previsto na Portaria 464/2012, art. 10, foi criado mais um turno para atendimento da Junta Médica Especial na cidade de " + cronogramaED.getMunicipioED().getNomeMunicipio() + ", em " + dataFormatada + ", no turno da " + turno + ". " + System.lineSeparator() + "  Este e-mail foi gerado automaticamente pelo sistema. " + System.lineSeparator() + "Por favor, não responda. Em caso de dúvidas, encaminhar e-mail para cpm-juntas@detran.rs.gov.br", null);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public void validaHoraTurno(JuntaCronogramaED ed) {
  //
  //    // A regra
  //    // "Se o cronograma for no turno da manhã, o horário deve ser entre 08:00 e 12:00. Se for no turno da tarde, entre 13:00 e 17:00."
  //    // não está sendo validada.
  //    // Exemplo: 20/04/2015 - Pelotas - Turno Manhã - 23:00
  //
  //    if (ed.getHmInicio() >= 800 && ed.getHmInicio() <= 1200)
  //      if (ed.getTurno() != 1)
  //        throw new RNException("Atenção! Favor verificar o turno e a hora de inicio informados.");
  //
  //    if (ed.getHmInicio() >= 1300 && ed.getHmInicio() <= 1700)
  //      if (ed.getTurno() != 2)
  //        throw new RNException("Atenção! Favor verificar o turno e a hora de inicio informados.");
  //
  //    if (ed.getHmInicio() > 1700)
  //      throw new RNException("Atenção! A hora início informada ultrapassa o período de 08:00 até 17:00");
  //
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaProfsED> sugestaoProfsNovoCronograma(Integer codMunicipio) {
  //    return agendaProfRN.sugestaoProfsNovoCronograma(codMunicipio);
  //  }
  //
  //  // A funcionalidade só poderá ser executada entre os dias 05 (geração do
  //  // cronograma) e 19 (um dia antes da publicação do cronograma).
  //  @Permissao(sistema = "GID", objeto = "CRONOGRAMA-JUN", acao = "CFG")
  //  public Boolean regerarCronograma() {
  //    Integer dia = HabWebUtil.getDiaAtual();
  //
  //    if (dia < 5 || dia > 19)
  //      throw new RNException("Atenção! O prazo (05 a 19) para regerar o cronograma foi expirado!");
  //
  //    List<JuntaCronogramaED> lstCronograma = bd.getCronogramaPublicado(Calendar.getInstance(), Calendar.getInstance());
  //
  //    if (lstCronograma.size() > 0) {
  //      throw new RNException("Atenção! Existe cronograma publicado no dia <" + HabWebUtil.calendarForDataFormatada(lstCronograma.get(0).getDthAgenda()) + ">. Não é possível regerar o cronograma.");
  //    }
  //
  //    this.atualizaParametrosRegeraCronograma(1);
  //    return true;
  //  }
  //
  //  @Permissao(sistema = "GID", objeto = "CRONOGRAMA-JUN", acao = "CFG")
  //  public Boolean ativaFlagRegerarCronograma() {
  //    this.atualizaParametrosRegeraCronograma(1);
  //    return true;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public Boolean desativarRegerarCronograma() {
  //    this.atualizaParametrosRegeraCronograma(0);
  //    return true;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  private void atualizaParametrosRegeraCronograma(Integer valorNum) {
  //
  //    CronogramaParametrosED ed = bd.getCronogramaParametros("REGERAR_CRONOGRAMA");
  //
  //    if (ed != null) {
  //
  //      ed.setCtrDthAtu(HabWebUtil.getDataAtual());
  //      // ed.setCtrOrgAtu(dadosSessaoUtil.montaDadosSessao().getSOE_SiglaOrganizacao());
  //      // ed.setCtrUsuAtu(dadosSessaoUtil.montaDadosSessao().getSOE_Matricula());
  //
  //      ed.setValorNum(valorNum);
  //      bd.atualizaParametros(ed);
  //    }
  //
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public void atualizaParametrosRedistribuirProfissionais(Integer valorNum) {
  //
  //    CronogramaParametrosED ed = bd.getCronogramaParametros("REDISTRIBUIR_PROFIS");
  //
  //    if (ed != null) {
  //
  //      ed.setCtrDthAtu(HabWebUtil.getDataAtual());
  //      if (sessionMB != null && sessionMB.getUser() != null) {
  //        String organizacao = String.valueOf(sessionMB.getUser().getProperties().get("organizacao"));
  //        ed.setCtrOrgAtu(organizacao);
  //        ed.setCtrUsuAtu(dadosSessaoUtil.montaDadosSessao().getSOE_Matricula());
  //
  //      } else {
  //        ed.setCtrOrgAtu("PROCERGS");
  //        ed.setCtrUsuAtu(4027L);
  //      }
  //
  //      ed.setValorNum(valorNum);
  //      bd.atualizaParametros(ed);
  //    }
  //
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public CronogramaParametrosED getParametros(String abrevPropriedade) {
  //    return bd.getCronogramaParametros(abrevPropriedade);
  //  }
  //
  //  // MÉTODO UTILIZADO NA TRANSFERENCIA DO CRONOGRAMA
  //  // VERIFICA SE EXISTE TURNO EM ANÁLISE OU DISPONIVEL PARA NOVA DATA
  //  // INFORMADA
  //  @Permissao(desabilitada = true)
  //  public List<JuntaCronogramaED> verificaTurnoMesmoDia(JuntaCronogramaED ed) {
  //    return bd.verificaTurnoMesmoDia(ed);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public void transfereCronograma(JuntaCronogramaED ed) {
  //
  //    // ATUALIZA A DATA DO CRONOGRAMA
  //    this.altera(ed);
  //
  //    // ATUALIZA A DATA NA AGENDA DOS PROFISSIONAIS
  //    agendaProfRN.atualizaDataAgenda(ed);
  //
  //    // INSERE HISTÓRICO DE ALTERAÇÃO DE DATA
  //    // EVENTO: TRANSFERÊNCIA DE CRONOGRAMA DA JUNTA
  //    this.geraHistoricoJunta(ed, 10, "Transferencia Cronograma", "Transferencia Cronograma");
  //
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getTurnosDisponiveis() {
  //    List<JuntaCronogramaED> lista = bd.getTurnosSituacao(SituacaoCronogramaEnum.DISPONIVEL.getCodigo());
  //
  //    // SETA O LOGRADOURO DO CREDENCIADO
  //    for (JuntaCronogramaED ed : lista) {
  //      CredenciadoED credenciadoED = ed.getCredenciadoED();
  //      credenciadoED.setLogradouro(credenciadoRN.getLogradouro(credenciadoED));
  //    }
  //
  //    return lista;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getListaTurnosDisponiveisMunicipio(JuntaCronogramaED ed, Integer codMunic) {
  //    Integer samdInicial = HabWebUtil.getSamdDiaInicialMes(ed.getSamdAgenda());
  //    Integer samdFinal = HabWebUtil.getSamdDiaFinalMes(ed.getSamdAgenda());
  //    return bd.getListaTurnosDisponiveisMunicipio(samdInicial, samdFinal, codMunic);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getListaTotalDeTurnosNoMunicipio(Integer samdAtual, Integer codMunicipio) {
  //    Integer samdInicial = HabWebUtil.getSamdDiaInicialMes(samdAtual);
  //    Integer samdFinal = HabWebUtil.getSamdDiaFinalMes(samdAtual);
  //    return bd.getListaTotalDeTurnosNoMunicipio(samdInicial, samdFinal, codMunicipio);
  //  }
  //
  //  // RETORNA OS MUNICIPIOS QUE JÁ TIVERAM JUNTA
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<MunicipioED> getMunicipiosQueJaTiveramJunta() {
  //    return bd.getMunicipiosQueJaTiveramJunta();
  //  }
  //
  //  // RETORNA OS MUNICIPIOS DE JUNTA
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<MunicipioED> getMunicipiosDeJunta() {
  //    return bd.getMunicipiosDeJunta();
  //  }
  //
  //  // APÓS A LOTAÇÃO DE UM TURNO VERIFICA A POSSIBILIDADE DE CRIAÇÃO DE UM NOVO TURNO
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void criaNovoTurnoInterior(JuntaCronogramaED ed) {
  //
  //    String msgEmail;
  //    MunicipioED municipioED = ed.getMunicipioED();
  //
  //    // SENÃO POSSUI TURNO DISPONIVEL OU EM ANÁLISE TENTA CRIAR UM NOVO TURNO.
  //    if (this.getListaTurnosDisponiveisMunicipio(ed, municipioED.getCodMunicipio()).size() == 0) {
  //      // BUSCA OS DIAS DE ATUAÇÃO DO MUNICIPIO
  //      List<JuntaAgendaAtuacaoED> agenda = agendaRN.getDiasAtuacaoMunicipio(municipioED.getCodMunicipio());
  //
  //      // TOTAL DE TURNO NO MES DESCONSIDERANDO OS CANCELADOS
  //      List<JuntaCronogramaED> listaTotalTurnosMesMunic = this.getListaTotalDeTurnosNoMunicipio(ed.getSamdAgenda(), municipioED.getCodMunicipio());
  //
  //      // SE O TOTAL DE TURNOS É INFERIOR AO MÁXIMO, TENTA CRIAR
  //      if (listaTotalTurnosMesMunic.size() < agenda.get(0).getQtdMaxTurno()) {
  //
  //        // Antecedência para criação automática de turno. Definido para 10 dias.
  //        Integer diaMinimoParaCriacao = HabWebUtil.getSamdMaisSomaDeDias(HabWebUtil.getSamdAtual(), 10);
  //
  //        JuntaCronogramaED turnoASerCriado = this.getTurnoCriar(agenda, listaTotalTurnosMesMunic, ed);
  //
  //        // SETA OS MESMOS PROFISSIONAIS DO TURNO LOTADO
  //        turnoASerCriado.setListaCronogramaProfs(this.getListaProfsClonado(ed, turnoASerCriado));
  //
  //        if (turnoASerCriado == null) {
  //          msgEmail = "Não foi possível criar o novo turno.";
  //        } else {
  //
  //          turnoASerCriado.setMunicipioED(ed.getMunicipioED());
  //          turnoASerCriado.setCredenciadoED(ed.getCredenciadoED());
  //
  //          // Verifica se o dia do NOVO TURNO é maior que a data mínima para criação (diaMinimoParaCriacao)
  //          if (turnoASerCriado.getSamdAgenda() > diaMinimoParaCriacao) {
  //            // CRIA O TURNO
  //            turnoASerCriado = this.geraNovoTurno(turnoASerCriado);
  //            msgEmail = this.geraMensgemNovoTurno(turnoASerCriado);
  //          } else {
  //            msgEmail = "Não houve criação de novo turno por não haver a antecedência necessária.";
  //          }
  //        }
  //
  //      } else {
  //        // EMAIL POR JÁ TER ATINGIDO O MÁXIMO DE TURNOS
  //        msgEmail = "Não houve disponibilidade para a criação de novo turno consecutivo e na mesma semana do turno lotado.";
  //      }
  //
  //      this.enviaEmailNovoTurno(msgEmail, ed);
  //    }
  //
  //  }
  //
  //  private List<JuntaCronogramaProfsED> getListaProfsClonado(JuntaCronogramaED oldTurno, JuntaCronogramaED newTurno) {
  //    List<JuntaCronogramaProfsED> lista = new ArrayList<JuntaCronogramaProfsED>();
  //    for (JuntaCronogramaProfsED profED : agendaProfRN.getListaProfissionaisVinculadosAoCronograma(oldTurno.getId())) {
  //      JuntaCronogramaProfsED ed = profED.clone();
  //      lista.add(ed);
  //    }
  //    return lista;
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void criaNovoTurnoPOA(JuntaCronogramaED cronogramaED) {
  //    // Poa NÃO gera novo turno, por enquanto.
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private void enviaEmailNovoTurno(String msgEmail, JuntaCronogramaED turnoLotado) {
  //
  //    List<String> destinatarios = new ArrayList<String>();
  //    List<String> comCopia = new ArrayList<String>();
  //    destinatarios.add("cpm-juntas@detran.rs.gov.br");
  //    comCopia.add("diego-feijo@procergs.rs.gov.br");
  //
  //    String assunto = "Turno de junta lotado - " + turnoLotado.getMunicipioED().getNomeMunicipio() + " - " + turnoLotado.getSamdAgendaFormatada();
  //
  //    StringBuilder email = new StringBuilder();
  //    email.append("<html> <body>");
  //    email.append("<h3>Aviso de Lotação de Turno</h3>");
  //    email.append("<p>");
  //
  //    email.append("Cidade: " + turnoLotado.getMunicipioED().getNomeMunicipio() + "<br>");
  //    email.append("Turno: " + turnoLotado.getTurnoFormatado() + "<br>");
  //    email.append("Lotado em: " + turnoLotado.getSamdLotacaoFormatada() + "<br> <br>");
  //    email.append(msgEmail);
  //    email.append("</p> </body> </html>");
  //
  //    // Por Fim, Envia o e-mail para os destinatários
  //    HabWebUtil.enviaEmail(destinatarios, comCopia, null, assunto, email.toString(), null);
  //  }
  //
  //  private String geraMensgemNovoTurno(JuntaCronogramaED ed) {
  //    StringBuilder msg = new StringBuilder();
  //
  //    msg.append("Novo turno para ");
  //    msg.append("<" + ed.getSamdAgendaFormatada() + "> - ");
  //    msg.append(ed.getTurnoFormatado());
  //    msg.append("O turno está 'EM ANÁLISE'. Para ser publicado deve-se acessar o GID Habilitação, menu 'Juntas -> Cronograma'");
  //
  //    return msg.toString();
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  private JuntaCronogramaED geraNovoTurno(JuntaCronogramaED ed) {
  //
  //    // SETA COMISSAO ATIVA
  //    ed.setJuntaComissaoED(juntaComissaoRN.getComissaoAtiva(TipoComissaoEnum.MEDICA_ESPECIAL_DETRAN.getCodigo()));
  //
  //    // BUSCA MAX ATENDIMENTO NO LOCAL DE ATUAÇÃO
  //    String turno;
  //    if (ed.getTurno() == 1)
  //      turno = "M";
  //    else
  //      turno = "T";
  //    ComissaoLocalAtuacaoED localAtuacaoED = this.getLocalAtuacao(ed.getMunicipioED().getCodMunicipio(), turno);
  //
  //    ed.setAtendMax(localAtuacaoED.getMaxAtendimento());
  //
  //    return this.inclui(ed);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public JuntaCronogramaED getTurnoCriar(List<JuntaAgendaAtuacaoED> listaAgenda, List<JuntaCronogramaED> listaTurnos, JuntaCronogramaED ed) {
  //
  //    for (JuntaAgendaAtuacaoED agendaED : listaAgenda) {
  //
  //      // VAI GERAR O TURNO A SER CRIADO
  //      if (!this.turnoTemAgenda(agendaED, listaTurnos)) {
  //        JuntaCronogramaED turnoED = new JuntaCronogramaED();
  //
  //        turnoED.setTurno(agendaED.getTurno());
  //        turnoED.setSituacao(SituacaoCronogramaEnum.EM_ANALISE.getCodigo());
  //        turnoED.setHmInicio(bd.obtemHoraInicialViaHabLocalAtuacao(agendaED.getMunicipioED().getCodMunicipio(), agendaED.getTurno()));
  //        turnoED.setHmPrx(turnoED.getHmInicio());
  //        turnoED.setDthAgenda(Util.paraCalendar(this.getDiaNovoTurno(agendaED, ed)));
  //
  //        return turnoED;
  //      }
  //    }
  //
  //    return null;
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private Integer getDiaNovoTurno(JuntaAgendaAtuacaoED agendaED, JuntaCronogramaED ed) {
  //    int ultimoDiaMes = 31;
  //    //int diaInicial = agendaED.getDiaInicial();
  //
  //    // PEGA O DIA DA AGENDA QUE LOTOU
  //    int diaInicial = HabWebUtil.getDia(ed.getSamdAgenda());
  //
  //    String mesAgenda = HabWebUtil.getMesSamd(ed.getSamdAgenda());
  //    String anoAgenda = HabWebUtil.getAnoSamd(ed.getSamdAgenda());
  //
  //    int diaSemanaAgenda = agendaED.getDiaSemana();
  //
  //    while (diaInicial <= ultimoDiaMes) {
  //      String dataAux = String.valueOf(diaInicial) + "/" + mesAgenda + "/" + anoAgenda;
  //
  //      if (dataAux.length() == 9)
  //        dataAux = "0" + dataAux;
  //
  //      if (HabWebUtil.getDiaSemana(dataAux) == diaSemanaAgenda) {
  //        return Integer.parseInt(HabWebUtil.dataForYYYYmmDD(dataAux));
  //      }
  //
  //      diaInicial++;
  //    }
  //
  //    return 0;
  //  }
  //
  //  public void enviaEmailNovoTurno() {
  //
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private boolean turnoTemAgenda(JuntaAgendaAtuacaoED agendaED, List<JuntaCronogramaED> listaTurnos) {
  //    for (JuntaCronogramaED turnoED : listaTurnos) {
  //      Integer diaSemana = HabWebUtil.getDiaSemana(turnoED.getSamdAgenda());
  //      if (agendaED.getDiaSemana() == diaSemana && turnoED.getTurno() == agendaED.getTurno()) {
  //        return true;
  //      }
  //    }
  //    return false;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getListaCronogramaASerEncerrado(Integer samdAtual) {
  //    if (samdAtual == null || samdAtual == 0)
  //      throw new RNException("Data inválida!");
  //
  //    List<JuntaCronogramaED> listaCronograma = bd.getListaCronogramaASerEncerrado(samdAtual);
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      cronogramaED.setListaCronogramaProfs(agendaProfRN.getListaProfissionaisVinculadosAoCronograma(cronogramaED.getId()));
  //    }
  //
  //    return listaCronograma;
  //  }
  //
  //  @Permissao(objeto = "H113", acao = "")
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void encerraTurno(JuntaCronogramaED cronogramaED) {
  //    cronogramaED.setSituacao(SituacaoCronogramaEnum.ENCERRADO.getCodigo()); // encerrado
  //
  //    juntaCronogramaTwoFaseRN.altera(cronogramaED);
  //    super.altera(cronogramaED);
  //  }
  //
  //  /**
  //   * Encerra a lista de turnos informada
  //   * Insere um histórico para o turno e incremanta a quantidade de atuações de cada perito
  //   * @param listaTurnosCronograma
  //   */
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void encerraTurnos(List<JuntaCronogramaED> listaTurnosCronograma) {
  //    for (JuntaCronogramaED cronogramaED : listaTurnosCronograma) {
  //
  //      // INSERE O HISTÓRICO
  //      this.insereHistoricoEncerramento(cronogramaED.getId());
  //
  //      // ENCERRA O TURNO
  //      this.encerraTurno(cronogramaED);
  //
  //      // INCREMENTA A QUANTIDADE DE ATUAÇÕES DE CADA PERITO
  //      if (cronogramaED.getSituacao() != SituacaoCronogramaEnum.EM_ANALISE.getCodigo())
  //        this.incrementaQteAtuacaoProfissional(cronogramaED);
  //
  //      logger.info("TURNO ENCERRADO COM SUCESSO. SEQ_AGENDA: " + cronogramaED.getId());
  //    }
  //  }
  //
  //  // ATUALIZA OU INSERE NA TABELA HAB_JUNTA_QUANTITATIVO_PROFIS
  //  public void incrementaQteDistribuidaProfissional(JuntaCronogramaED cronogramaED) {
  //    for (JuntaCronogramaProfsED ed : cronogramaED.getListaCronogramaProfs()) {
  //      juntaQuantProfisRN.incrementaQteDistribuidaProfissional(ed, cronogramaED);
  //    }
  //  }
  //
  //  // ATUALIZA OU INSERE NA TABELA HAB_JUNTA_QUANTITATIVO_PROFIS
  //  public void incrementaQteAtuacaoProfissional(JuntaCronogramaED cronogramaED) {
  //    for (JuntaCronogramaProfsED ed : cronogramaED.getListaCronogramaProfs()) {
  //      juntaQuantProfisRN.incrementaQteAtuacaoProfissional(ed, cronogramaED);
  //    }
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  private void insereHistoricoEncerramento(Long seqAgenda) {
  //    JuntaCronogramaHistoricoED ed = new JuntaCronogramaHistoricoED();
  //    ed.setNroIntAgendaJunta(seqAgenda);
  //    ed.setCodEvento(EventoJuntaEnum.ENCERRAMENTO_AUTOMATICO_CRONOGRAMA_JM.getCodigo()); // 7 - ENCERRAMENTO
  //    ed.setTexto(juntaAgHistRN.montaConteudoHistorico("Encerrado", "Agenda vencida", "WorkLoad"));
  //    juntaAgHistRN.inclui(ed);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public ComissaoLocalAtuacaoED getLocalAtuacao(Integer municPesq, String turno) {
  //    return localAtuacaoBD.getLocalAtuacao(municPesq, turno);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getTurnosEmAnalise() {
  //    List<JuntaCronogramaED> listaCronograma = bd.getTurnosSituacao(SituacaoCronogramaEnum.EM_ANALISE.getCodigo());
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      cronogramaED.setListaCronogramaProfs(agendaProfRN.getListaProfissionaisVinculadosAoCronograma(cronogramaED.getId()));
  //    }
  //
  //    return listaCronograma;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getTurnosLotado() {
  //    return bd.getTurnosSituacao(SituacaoCronogramaEnum.LOTADO.getCodigo());
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public CronogramaParametrosED getCronogramaParametros(String abrevPropriedade) {
  //    return bd.getCronogramaParametros(abrevPropriedade);
  //  }
  //
  //  // CANCELA A AGENDA DOS PROFISSIONAIS
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void cancelaAgendaDosProfissionais(List<JuntaCronogramaProfsED> lstAgendaProfs) {
  //    for (JuntaCronogramaProfsED juntaAgendaProfissionalED : lstAgendaProfs) {
  //      juntaAgendaProfissionalED.setSituacao(0);
  //      agendaProfRN.altera(juntaAgendaProfissionalED);
  //    }
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void redistribuiProfissionaisNosTurnos(List<JuntaCronogramaED> listaCronograma) {
  //
  //    Integer anoAtual = HabWebUtil.getAnoAtual();
  //    Integer mesAtual = Integer.parseInt(HabWebUtil.getMesAtual());
  //
  //    bd.setLogger(logger);
  //    logger.warn("\n\n*** INICIANDO REDISTRIBUIÇÃO DOS PROFISSIONAIS ***");
  //    logger.warn("*** TOTAL DE TURNOS: " + listaCronograma.size() + " ***");
  //
  //    String profsComissaoAtiva = juntaComissaoRN.geraClausulaInProfsComissao();
  //    logger.warn("\n*** PROFISSIONAIS DA COMISSAO ATIVA: " + profsComissaoAtiva + " ***");
  //
  //    // LISTA COM OS PROFISSIONAIS DA COMISSÃO ATIVA - ESTA LISTA POSSUI TODOS OS PROFISSIONAIS DA COMISSÃO ATIVA
  //    List<AtividadeProfissionalED> profsDisponiveisComissaoAtiva = bd.getProfissonaisComissaoAtiva(profsComissaoAtiva);
  //    logger.warn("\n\n*** LISTA PROFISSIONAIS COMISSÃO ATIVA ***");
  //    this.geraLogListaProfs(profsDisponiveisComissaoAtiva);
  //
  //    // LISTA COM OS PROFISSIONAIS DA COMISSÃO ATIVA QUE PODEM ATUAR NO MES
  //    List<AtividadeProfissionalED> profsDisponiveisNoMes = bd.getProfissonaisQuePodemAtuarNoMes(anoAtual, mesAtual, profsComissaoAtiva);
  //    logger.warn("\n\n*** LISTA PROFISSIONAIS DISPONÍVEIS NO MÊS ***");
  //    this.geraLogListaProfs(profsDisponiveisNoMes);
  //
  //    // Limpa a lista de profissionais de cada turno
  //    for (JuntaCronogramaED ed : listaCronograma) {
  //      ed.getListaCronogramaProfs().clear();
  //    }
  //
  //    // MONTA A QUANTIDADE DE TURNOS QUE CADA PROFISSIONAL IRÁ RECEBER
  //    this.montaQuantidadeDeTurnosQueSeraRecebidoPorProfs(listaCronograma, profsDisponiveisComissaoAtiva, profsDisponiveisNoMes);
  //
  //    // DISTRIBUI OS PROFISSIONAIS NO INTERIOR
  //    this.distribuiProfissionaisNoInterior(listaCronograma, profsDisponiveisComissaoAtiva);
  //
  //    // DISTRIBUI O RESTANTE DOS PROFISSIONAIS EM PORTO ALEGRE
  //    this.distribuiProfissionaisNaCapital(listaCronograma, profsDisponiveisComissaoAtiva);
  //
  //    //valida os turnos e tentar completar
  //    //-------------------
  //    listaCronograma = validaQuantProfsTurnECompletaProfis(listaCronograma);
  //    //-------------------
  //    
  //    // INSERE OS PROFISSIONAIS DISTRIBUIDOS NO BANCO
  //    this.insereProfissionaisDistribuidos(listaCronograma);
  //
  //    // ENVIA EMAIL COM A NOVA REDISTRIBUIÇÃO
  //    this.enviaEmailRegeracaoCronograma(listaCronograma);
  //  }
  //
  //  private void insereProfissionaisDistribuidos(List<JuntaCronogramaED> listaCronograma) {
  //    logger.warn("\n\n*** INSERE OS PROFISSIONAIS RE-DISTRIBUIDOS ***");
  //    for (JuntaCronogramaED ed : listaCronograma) {
  //      List<JuntaCronogramaProfsED> listaAgendaProfissional = ed.getListaCronogramaProfs();
  //      for (JuntaCronogramaProfsED agendaProfsED : listaAgendaProfissional) {
  //
  //        agendaProfsED.setNroIntAgendaJunta(ed.getId());
  //        agendaProfsED.setTipoProfis(29);
  //        agendaProfsED.setDthAgenda(Util.paraCalendar(ed.getSamdAgenda()));
  //        agendaProfsED.setTurno(ed.getTurno());
  //        agendaProfsED.setSituacao(1); // ATIVO
  //        agendaProfRN.inclui(agendaProfsED);
  //
  //        // - Decrementar 1 da QTD_DISTRIBUICAO_MIN na tabela HAB_DISTR_MIN_PROFIS dos profissionais
  //        distMinProfs.decrementaDistribuicaoMinima(agendaProfsED.getDocIdentProfis(), agendaProfsED.getUfDocIdentProfis());
  //
  //        // INSERE OS PROFISSIONAIS REDISTRIBUIDOS NA DISTRIBUIÇÃO DO INTERIOR - HAB_PROFIS_DIST_MUNIC_MES
  //        if (!ed.getMunicipioED().getCodMunicipio().equals(90000)) {
  //          this.insereProfsDistInterior(agendaProfsED, ed);
  //        }
  //
  //      }
  //    }
  //  }
  //
  //  // INSERE OS PROFISSIONAIS QUE TRABALHARAM NO INTERIOR
  //  private void insereProfsDistInterior(JuntaCronogramaProfsED agendaProfsED, JuntaCronogramaED ed) {
  //
  //    Integer anoAtual = HabWebUtil.getAnoAtual();
  //    Integer mesAtual = Integer.parseInt(HabWebUtil.getMesAtual());
  //    Integer proxMes = mesAtual, ano = anoAtual;
  //    proxMes++;
  //
  //    if (proxMes > 12) {
  //      ano++;
  //      proxMes = 1;
  //    }
  //
  //    CronogramaDistribuidoMunicMesED profDisInterior = new CronogramaDistribuidoMunicMesED();
  //    profDisInterior.setCodMunic(ed.getMunicipioED().getCodMunicipio());
  //    profDisInterior.setDocIdent(agendaProfsED.getDocIdentProfis());
  //    profDisInterior.setUfDocIdent(agendaProfsED.getUfDocIdentProfis());
  //    profDisInterior.setAno(ano);
  //    profDisInterior.setMes(proxMes);
  //
  //    bd.incluiProfsDistInterior(profDisInterior);
  //  }
  //
  //  public void distribuiProfissionaisNaCapital(List<JuntaCronogramaED> listaCronograma, List<AtividadeProfissionalED> profsDisponiveis) {
  //    List<AtividadeProfissionalED> listaProfsDistribuirPoa = this.montaListaDistribuicaoProfissionaisPOA(profsDisponiveis);
  //    listaProfsDistribuirPoa = this.ordenaListaProfissionaisPeloTurnoAReceber(listaProfsDistribuirPoa);
  //
  //    for (AtividadeProfissionalED ativProfsED : listaProfsDistribuirPoa) {
  //      this.setaProfissionalNosTurnosDaCapital(listaCronograma, ativProfsED);
  //    }
  //  }
  //
  //  private void setaProfissionalNosTurnosDaCapital(List<JuntaCronogramaED> listaCronograma, AtividadeProfissionalED ativProfsED) {
  //
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      if (cronogramaED.getMunicipioED().getCodMunicipio().equals(90000)) {
  //        if (ativProfsED.getTurnosAReceber() > 0) {
  //          // VERIFICA SE O TURNO AINDA TEM VAGA PARA O PROFISSIONAL
  //          if (cronogramaED.getListaCronogramaProfs().size() < 3) {
  //            Boolean credenciamentoVencido = false;
  //            Boolean profAfastado = false;
  //            Boolean profEmOutraJunta = false;
  //            Boolean naoTemTurnoAReceber = false;
  //            Boolean profAlocadoNoTurno = false;
  //
  //            // VERIFICA SE O PROFISSIONAL JÁ FOI ALOCADO NO TURNO. NÃO PODE PERMITER 2 REGISTROS DO MESMO PROFISSIONAL
  //            for (JuntaCronogramaProfsED profED : cronogramaED.getListaCronogramaProfs()) {
  //              String docIdentProf = ativProfsED.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent();
  //              if (profED.getDocIdentProfis().equals(docIdentProf)) {
  //                profAlocadoNoTurno = true;
  //              }
  //            }
  //
  //            if (ativProfsED.getTurnosAReceber() == null) {
  //              naoTemTurnoAReceber = true;
  //            }
  //
  //            // VALIDA SE PROFISSIONAL ESTÁ COM CREDENCIAMENTO OK
  //            if (this.verificaCredenciamentoVencido(cronogramaED.getSamdAgenda(), ativProfsED)) {
  //              credenciamentoVencido = true;
  //            }
  //
  //            // VALIDA SE PROFISSIONAL NÃO ESTÁ NO PERÍODO DE AFASTAMENTO
  //            if (bd.verificaProfissionalAfastado(Util.paraCalendar(cronogramaED.getSamdAgenda()), ativProfsED)) {
  //              logger.warn("PROFISSIONAL EM PERÍODO DE AFASTAMENTO: NOME: " + ativProfsED.getNome() + " DATA: " + cronogramaED.getSamdAgendaFormatada());
  //              profAfastado = true;
  //            }
  //
  //            // VALIDA SE PROFISSIONAL NÃO ESTÃO ATUANDO EM UMA JUNTA DO INTERIOR
  //            if (this.verificaProfissionalAlocadoEmOutraJuntaInterior(listaCronograma, ativProfsED, cronogramaED.getSamdAgenda())) {
  //              profEmOutraJunta = true;
  //            }
  //
  //            if (!credenciamentoVencido && !profAfastado && !profEmOutraJunta && !naoTemTurnoAReceber && !profAlocadoNoTurno) {
  //              // ADICIONA O PROFISSIONAL NO TURNO
  //              JuntaCronogramaProfsED agendaProfisED = new JuntaCronogramaProfsED();
  //              agendaProfisED.setNroIntAgendaJunta(cronogramaED.getId());
  //              agendaProfisED.setTipoProfis(29);
  //              agendaProfisED.setDthAgenda(Util.paraCalendar(cronogramaED.getSamdAgenda()));
  //              agendaProfisED.setTurno(cronogramaED.getTurno());
  //              agendaProfisED.setSituacao(0);
  //              agendaProfisED.setProfissionalED(ativProfsED.getAtividadeProfissionalPK().getProfissionalED());
  //
  //              cronogramaED.setListaCronogramaProfs(agendaProfisED);
  //
  //              ativProfsED.setTurnosAReceber(ativProfsED.getTurnosAReceber() - 1);
  //            }
  //
  //          }
  //        } else {
  //          break; // PARA DE PERCORRER A LISTA DE CRONOGRAMA. PROFISSIONAL JÁ RECEBEU SEU TURNOS.
  //        }
  //      }
  //    }
  //
  //  }
  //
  //  private List<AtividadeProfissionalED> ordenaListaProfissionaisPeloTurnoAReceber(List<AtividadeProfissionalED> listaProfsDistribuirPoa) {
  //    Collections.sort(listaProfsDistribuirPoa, new ComparatorListaProfissionais());
  //    return listaProfsDistribuirPoa;
  //  }
  //
  //  private List<AtividadeProfissionalED> montaListaDistribuicaoProfissionaisPOA(List<AtividadeProfissionalED> profsDisponiveis) {
  //    List<AtividadeProfissionalED> listaProfs = new ArrayList<AtividadeProfissionalED>();
  //    int contadorProfs3Turnos = 0;
  //
  //    for (AtividadeProfissionalED ativProfisED : profsDisponiveis) {
  //
  //      if (ativProfisED.getTurnosAReceber() != null) {
  //
  //        Integer turnoRestante = ativProfisED.getTurnosAReceber();
  //
  //        if (turnoRestante > 4) {
  //          int divTurno = turnoRestante / 2;
  //
  //          ativProfisED.setTurnosAReceber(turnoRestante - divTurno);
  //          listaProfs.add(ativProfisED);
  //
  //          AtividadeProfissionalED prof2 = new AtividadeProfissionalED();
  //          prof2 = (AtividadeProfissionalED) ativProfisED.clone();
  //          prof2.setTurnosAReceber(divTurno);
  //          listaProfs.add(prof2);
  //
  //        } else if (turnoRestante == 4) { // RECEBE OS 4 TURNOS NA SEMANA
  //          listaProfs.add(ativProfisED);
  //        } else if (turnoRestante == 3) { // PROFS COM 3 TURNOS - DUAS REGRAS DIFERENTES
  //          if (contadorProfs3Turnos < 5) {
  //
  //            ativProfisED.setTurnosAReceber(2);
  //            listaProfs.add(ativProfisED);
  //
  //            AtividadeProfissionalED prof2 = new AtividadeProfissionalED();
  //            prof2 = (AtividadeProfissionalED) ativProfisED.clone();
  //            prof2.setTurnosAReceber(1);
  //            listaProfs.add(prof2);
  //
  //            contadorProfs3Turnos++;
  //          } else {
  //            listaProfs.add(ativProfisED);
  //          }
  //        } else if (turnoRestante == 2) {
  //          ativProfisED.setTurnosAReceber(2);
  //          listaProfs.add(ativProfisED);
  //        } else if (turnoRestante == 1) {
  //          ativProfisED.setTurnosAReceber(1);
  //          listaProfs.add(ativProfisED);
  //        }
  //      }
  //    }
  //    return listaProfs;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public void distribuiProfissionaisNoInterior(List<JuntaCronogramaED> listaCronograma, List<AtividadeProfissionalED> profsDisponiveis) {
  //
  //    Integer codMunicAnterior = 0;
  //    List<JuntaCronogramaProfsED> listaAgendaProfissional = null;
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      // DIFERENTE DE PORTO ALEGRE
  //      if (!cronogramaED.getMunicipioED().getCodMunicipio().equals(90000)) {
  //
  //        if (!codMunicAnterior.equals(cronogramaED.getMunicipioED().getCodMunicipio())) {
  //          listaAgendaProfissional = this.getProfissionaisInterior(listaCronograma, cronogramaED, profsDisponiveis);
  //        }
  //
  //        this.decrementaUmTurnoDeCadaProfissional(listaAgendaProfissional, profsDisponiveis);
  //
  //        cronogramaED.setListaCronogramaProfs(this.clonaListaProfs(listaAgendaProfissional));
  //        codMunicAnterior = cronogramaED.getMunicipioED().getCodMunicipio();
  //      }
  //    }
  //  }
  //
  //  private void decrementaUmTurnoDeCadaProfissional(List<JuntaCronogramaProfsED> listaAgendaProfissional, List<AtividadeProfissionalED> profsDisponiveis) {
  //    for (AtividadeProfissionalED profDisponivel : profsDisponiveis) {
  //      for (JuntaCronogramaProfsED ativProfED : listaAgendaProfissional) {
  //        if (ativProfED.getProfissionalED().getProfissionaisPK().getDocIdent().equals(profDisponivel.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent())) {
  //          // DECREMENTA UM TURNO DO PROFISSIONAL
  //          profDisponivel.setTurnosAReceber(profDisponivel.getTurnosAReceber() - 1);
  //        }
  //      }
  //    }
  //  }
  //
  //  private List<JuntaCronogramaProfsED> getProfissionaisInterior(List<JuntaCronogramaED> listaCronograma, JuntaCronogramaED cronogramaED, List<AtividadeProfissionalED> profsDisponiveis) {
  //
  //    Integer anoAtual = HabWebUtil.getAnoAtual();
  //    String mesAtual = HabWebUtil.getMesAtual();
  //
  //    // LISTA OS PROFISSIONAIS QUE ATUARAM NA JUNTA NO MES ANTERIOR
  //    List<CronogramaDistribuidoMunicMesED> profsDistribuidosMesAnterior = bd.getProfSDistribuidosMesAnterior(anoAtual, Integer.parseInt(mesAtual), cronogramaED.getMunicipioED().getCodMunicipio());
  //    Integer idProxProfissional = 0;
  //
  //    // IDENTIFICA NA LISTA DE PROFISSIONAIS O PRÓXIMO.
  //    // TRATAMENTO PARA O CASO DE POSSUIR REGISTROS NO MES ANTERIOR
  //    if (profsDistribuidosMesAnterior.size() > 0) {
  //      String ultimoProfsMesAnterior = profsDistribuidosMesAnterior.get(0).getDocIdent();
  //
  //      // PERCORRE A LISTA DOS PROFISSIONAIS DISPONÍVEIS PARA ENCONTRAR O PRÓXIMO
  //      for (int i = 0; i < profsDisponiveis.size(); i++) {
  //        if (profsDisponiveis.get(i).getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent().equals(ultimoProfsMesAnterior)) {
  //          idProxProfissional = ++i; // Pega o próximo da lista
  //          break;
  //        }
  //      }
  //    }
  //
  //    if (idProxProfissional >= profsDisponiveis.size())
  //      idProxProfissional = 0;
  //
  //    List<JuntaCronogramaProfsED> listaAgendaProfissional = new ArrayList<JuntaCronogramaProfsED>();
  //    // Seleciona os 3 profissionais para atuar
  //    for (int i = 0; i < 3; i++) {
  //      JuntaCronogramaProfsED agendaProfisED = new JuntaCronogramaProfsED();
  //      if (idProxProfissional >= profsDisponiveis.size())
  //        idProxProfissional = 0;
  //
  //      Boolean cond;
  //      AtividadeProfissionalED profDisponivel;
  //      int qtdTentivas = 0;
  //      do {
  //        qtdTentivas++;
  //        //interrompe rotina em caso de loop
  //        if(qtdTentivas>1000){
  //          throw new RNException("Excedido o limite de tentativas");
  //        }
  //        profDisponivel = profsDisponiveis.get(idProxProfissional);
  //        cond = validacoesProfissionalDisponivel(listaCronograma, cronogramaED, profDisponivel);
  //
  //        // Incrementa a variável
  //        idProxProfissional++;
  //        if (idProxProfissional >= profsDisponiveis.size())
  //          idProxProfissional = 0;
  //
  //      } while (cond);
  //
  //      agendaProfisED.setProfissionalED(profDisponivel.getAtividadeProfissionalPK().getProfissionalED());
  //
  //      listaAgendaProfissional.add(agendaProfisED);
  //    }
  //
  //    return listaAgendaProfissional;
  //  }
  //
  //  private Boolean validacoesProfissionalDisponivel(List<JuntaCronogramaED> listaCronograma, JuntaCronogramaED cronogramaED, AtividadeProfissionalED profDisponivel) {
  //    // VERIFICA SE PROFISSIONAL TEM TURNO A RECEBER.
  //    if (profDisponivel.getTurnosAReceber() == null) {
  //      return true;
  //    }
  //
  //    // VERIFICA SE PROFISSIONAL NÃO ESTÁ ATUANDO EM OUTRA JUNTA NO MESMO DIA
  //    if (verificaProfissionalAlocadoEmOutraJuntaInterior(listaCronograma, profDisponivel, cronogramaED.getSamdAgenda())) {
  //      return true;
  //    }
  //
  //    // VERIFICA SE PROFISSIONAL ESTÁ EM PERÍODO DE AFASTAMENTO
  //    if (bd.verificaProfissionalAfastado(Util.paraCalendar(cronogramaED.getSamdAgenda()), profDisponivel)) {
  //      logger.warn("PROFISSIONAL EM PERÍODO DE AFASTAMENTO: NOME: " + profDisponivel.getNome() + " DATA: " + cronogramaED.getSamdAgendaFormatada());
  //      return true;
  //    }
  //
  //    // VERIFICA SE PROFISSIONAL ESTÁ COM O CREDENCIAMENTO VENCIDO NO DIA
  //    if (this.verificaCredenciamentoVencido(cronogramaED.getSamdAgenda(), profDisponivel)) {
  //      return true;
  //    }
  //
  //    return false;
  //  }
  //
  //  private Boolean verificaProfissionalAlocadoEmOutraJuntaInterior(List<JuntaCronogramaED> listaCronograma, AtividadeProfissionalED ativProfsED, Integer diaDoAgendamento) {
  //
  //    Integer dia24Menos, dia48Menos, dia72Menos;
  //    Integer dia24Mais, dia48Mais, dia72Mais;
  //    Boolean retorno = false;
  //
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      // PEGA SOMENTE AS JUNTAS DO INTERIOR
  //      if (!cronogramaED.getMunicipioED().getCodMunicipio().equals(90000)) {
  //        List<JuntaCronogramaProfsED> listaProfsAgenda = cronogramaED.getListaCronogramaProfs();
  //        for (JuntaCronogramaProfsED juntaAgProfs : listaProfsAgenda) {
  //
  //          /////////////////////////////////////////
  //          dia24Menos = HabWebUtil.getSamdMenosDias(diaDoAgendamento, 1);
  //          dia48Menos = HabWebUtil.getSamdMenosDias(diaDoAgendamento, 2);
  //          //dia72Menos = HabWebUtil.getSamdMenosDias(diaDoAgendamento, 3);
  //
  //          /////////////////////////////////////////
  //          dia24Mais = HabWebUtil.getSamdMaisSomaDeDias(diaDoAgendamento, 1);
  //          dia48Mais = HabWebUtil.getSamdMaisSomaDeDias(diaDoAgendamento, 2);
  //          //dia72Mais = HabWebUtil.getSamdMaisSomaDeDias(diaDoAgendamento, 3);
  //
  //          // VALIDA SE O PERITO ESTÁ NO TURNO NO DIA
  //          String docIdent = ativProfsED.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent();
  //          if (diaDoAgendamento.equals(cronogramaED.getSamdAgenda())) {
  //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //              retorno = true;
  //
  //          } else if (dia24Mais.equals(cronogramaED.getSamdAgenda())) { // Valida +24 horas
  //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //              retorno = true;
  //
  //          } else if (dia48Mais.equals(cronogramaED.getSamdAgenda())) { // Valida +48 horas
  //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //              retorno = true;
  //
  //            //          } else if (dia72Mais.equals(cronogramaED.getSamdAgenda())) { // Valida +72 horas
  //            //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //            //              return true;
  //
  //          } else if (dia24Menos.equals(cronogramaED.getSamdAgenda())) { // Valida -24 horas
  //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //              retorno = true;
  //
  //          } else if (dia48Menos.equals(cronogramaED.getSamdAgenda())) { // Valida -48 horas
  //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //              retorno = true;
  //
  //          } //else if (dia72Menos.equals(cronogramaED.getSamdAgenda())) { // Valida -72 horas
  //          //            if (docIdent.equals(juntaAgProfs.getDocIdentProfis()))
  //          //              return true;
  //          //          }
  //        }
  //      }
  //    }
  //
  //    if (retorno)
  //      logger.warn("*** PROFISSIONAL: " + ativProfsED.getNome() + " ALOCADO EM OUTRA JUNTA ***");
  //
  //    return retorno;
  //  }
  //
  //  private void geraLogListaProfs(List<AtividadeProfissionalED> listaAtividadeProfissional) {
  //    for (AtividadeProfissionalED ed : listaAtividadeProfissional) {
  //      logger.warn("*** PROFISSIONAL: " + ed.getNome() + " - VENCIMENTO GAD: " + ed.getSamdVen() + "***");
  //    }
  //  }
  //
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  private void enviaEmailRegeracaoCronograma(List<JuntaCronogramaED> listaCronograma) {
  //    String htmlCronograma = this.criaHtmlEnvioEmail(listaCronograma, "REDISTRIBUIÇÃO PROFISSIONAIS CRONOGRAMA - AMBIENTE: " + propriedades.getAmbiente());
  //
  //    logger.warn("\n\nHTML DA GERAÇÃO:" + htmlCronograma);
  //
  //    //List<String> destinoPara = new ArrayList<String>();
  //    //destinoPara.add("diego-feijo@procergs.rs.gov.br");
  //    //HabWebUtil.enviaEmail(destinoPara, null, null, "REDISTRIBUIÇÃO PROFISSIONAIS CRONOGRAMA - AMBIENTE: " + propriedades.getAmbiente(), htmlCronograma, null);
  //  }
  //
  //  public String criaHtmlEnvioEmail(List<JuntaCronogramaED> listaCronograma, String titulo) {
  //    StringBuilder msg = new StringBuilder();
  //
  //    msg.append("<html> <style> dt { color: blue; } p.rodape {color: blue; font-style: italic;} dd.tab{padding: 0 120px;} dd.bold{font-weight:bold;}</style> ");
  //    msg.append("<head> <meta charset='UTF-8'> </head>");
  //    msg.append("<body> <h2>Cronograma Junta Médica Especial - " + titulo + "</h2> <hr SIZE=1> ");
  //    msg.append("<dl>");
  //
  //    for (JuntaCronogramaED ed : listaCronograma) {
  //      msg.append("<br>");
  //      msg.append("<dt>" + ed.getMunicipioED().getNomeMunicipio() + " - COD_MUNIC: " + ed.getMunicipioED().getCodMunicipio() + "</dt>");
  //      msg.append("<dd class='bold'>Dia: " + ed.getSamdAgendaFormatada() + " - " + ed.getTurnoFormatado());
  //      for (JuntaCronogramaProfsED profED : ed.getListaCronogramaProfs()) {
  //        msg.append("<dd class='tab'> RG: " + profED.getDocIdentProfis() + " - Nome: " + profED.getProfissionalED().getNome() + "</dd>");
  //      }
  //      msg.append("</dd>");
  //    }
  //
  //    msg.append("</dl>");
  //    msg.append("<hr SIZE=1> <p class='rodape'>PROCERGS - Sistema GID Habilitação. </p> </body>  </html>");
  //
  //    return msg.toString();
  //  }
  //
  //  private List<JuntaCronogramaProfsED> clonaListaProfs(List<JuntaCronogramaProfsED> listaAgendaProfissional) {
  //    List<JuntaCronogramaProfsED> listaAgProfs = new ArrayList<JuntaCronogramaProfsED>();
  //    for (JuntaCronogramaProfsED juntaAgProfs : listaAgendaProfissional) {
  //      JuntaCronogramaProfsED ed = new JuntaCronogramaProfsED();
  //      ed.setProfissionalED(juntaAgProfs.getProfissionalED());
  //      listaAgProfs.add(ed);
  //    }
  //    return listaAgProfs;
  //  }
  //
  //  // AtividadeProfissionalED = KGID206D
  //  private boolean verificaCredenciamentoVencido(Integer samdAgenda, AtividadeProfissionalED atividadeProfissionalED) {
  //    if (samdAgenda >= atividadeProfissionalED.getSamdVen()) {
  //      logger.warn("*** PROFISSIONAL: " + atividadeProfissionalED.getNome() + " COM CREDENCIAMENTO VENCIDO NA DATA: " + samdAgenda + " ***");
  //      return true;
  //    } else {
  //      return false;
  //    }
  //  }
  //
  //  /** 
  //   * ESSE MÉTODO RECEBE UMA LISTA DE PROFISSIONAIS DA COMISSÃO ATIVA E UMA LISTA DE PROFISSIONAIS QUE PODEM ATUAR NO MES (K206_SAMD_VEN > data)
  //   * O MÉTODO SÓ VAI SETAR TURNOS NA LISTA profsDisponiveisComissaoAtiva SE O PROFISSIONAL ESTIVER NA LISTA profsDisponiveisNoMes
  //   * */
  //  private void montaQuantidadeDeTurnosQueSeraRecebidoPorProfs(List<JuntaCronogramaED> listaCronograma, List<AtividadeProfissionalED> profsDisponiveisComissaoAtiva, List<AtividadeProfissionalED> profsDisponiveisNoMes) {
  //    Integer quantMinimaTurnoPorProfs = this.getQuantMinimaTurnoPorProfissional(listaCronograma.size(), profsDisponiveisNoMes.size());
  //    logger.warn("\n*** QUANTIDADE MÍNIMA QUE CADA PROFISSIONAL IRÁ RECEBER: " + quantMinimaTurnoPorProfs + "***");
  //
  //    // SETA A QUANTIDADE MÍNIMA DE TURNOS PARA OS PROFISSIONAIS
  //    this.setQuantidadeMinimaDeTurnos(profsDisponiveisNoMes, quantMinimaTurnoPorProfs);
  //
  //    // SETA OS TURNOS EXTRA PARA OS PROFISSIONAIS QUE TEM DIREITO
  //    this.setaTurnosExtrasParaProfs(listaCronograma, profsDisponiveisNoMes);
  //
  //    // SETA OS TURNOS NA LISTA profsDisponiveisComissaoAtiva SOMENTE PARA OS PROFISSIONAIS QUE ESTIVEREM NA LISTA profsDisponiveisNoMes
  //    this.setaTurnosProfsComissaoAtiva(profsDisponiveisComissaoAtiva, profsDisponiveisNoMes);
  //  }
  //
  //  private void setaTurnosProfsComissaoAtiva(List<AtividadeProfissionalED> profsDisponiveisComissaoAtiva, List<AtividadeProfissionalED> profsDisponiveisNoMes) {
  //    for (AtividadeProfissionalED profComAtiva : profsDisponiveisComissaoAtiva) {
  //      String docProfComAtiva = profComAtiva.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent();
  //      for (AtividadeProfissionalED profDispMes : profsDisponiveisNoMes) {
  //        String docProfDispMes = profDispMes.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent();
  //
  //        // VERIFICO SE FOI ENCONTRADO NA LISTA
  //        if (docProfComAtiva.equals(docProfDispMes)) {
  //          profComAtiva.setTurnosAReceber(profDispMes.getTurnosAReceber());
  //        }
  //
  //      }
  //    }
  //  }
  //
  //  private void setaTurnosExtrasParaProfs(List<JuntaCronogramaED> listaCronograma, List<AtividadeProfissionalED> profsDisponiveis) {
  //    // LISTA DOS PROFISSIONAIS QUE DEVEM RECEBER O TURNO EXTRA
  //    List<CronogramaDistribuicaoMinimaProfisED> lstProfsTurnoExtra = bd.getProfQueRecebemUmTurnoAMais();
  //    int quantProfsTurnoExtra = this.getQuantProfsRecebemTurnoExtra(listaCronograma.size(), profsDisponiveis.size());
  //    logger.warn("\n\n*** QUANTIDADE DE PROFISSIONAIS QUE RECEBE TURNO EXTRA: " + quantProfsTurnoExtra + "***");
  //
  //    if (lstProfsTurnoExtra.size() > 0) {
  //      if (quantProfsTurnoExtra > 0) {
  //        for (int i = 0; i < quantProfsTurnoExtra; i++) {
  //          for (int j = 0; j < profsDisponiveis.size(); j++) {
  //            if (lstProfsTurnoExtra.size() > i) {
  //              if (lstProfsTurnoExtra.get(i).getDocIdent().equals(profsDisponiveis.get(j).getId().getProfissionalED().getProfissionaisPK().getDocIdent())) {
  //                profsDisponiveis.get(j).setTurnosAReceber(profsDisponiveis.get(j).getTurnosAReceber() + 1);
  //                logger.warn("*** PROFISSIONAL RECEBENDO TURNO EXTRA: " + profsDisponiveis.get(j).getNome() + "***");
  //              }
  //            }
  //          }
  //        }
  //      }
  //    }
  //  }
  //
  //  private void setQuantidadeMinimaDeTurnos(List<AtividadeProfissionalED> profsDisponiveis, Integer quantMinimaTurnoPorProfs) {
  //    // SETA A QUANTIDADE MÍNIMA QUE CADA PROFISSIONAL IRÁ RECEBER
  //    for (AtividadeProfissionalED ativProfisED : profsDisponiveis) {
  //      ativProfisED.setTurnosAReceber(quantMinimaTurnoPorProfs);
  //    }
  //  }
  //
  //  private Integer getQuantMinimaTurnoPorProfissional(Integer totalTurnos, Integer quantProfsDisponiveis) {
  //    return (totalTurnos * 3) / quantProfsDisponiveis;
  //  }
  //
  //  // RETORNA A QUANTIDADE DE PERITOS QUE IRÃO RECEBER OS TURNOS EXTRA
  //  private Integer getQuantProfsRecebemTurnoExtra(Integer totalTurnos, Integer quantProfsDisponiveis) {
  //    return (totalTurnos * 3) - (getQuantMinimaTurnoPorProfissional(totalTurnos, quantProfsDisponiveis) * quantProfsDisponiveis);
  //  }
  //
  //  // ORDENA A LISTA DE PROFISSIONAIS PELOS TURNOS RESTANTES (MAIOR -> MENOR)
  //  class ComparatorListaProfissionais implements Comparator<AtividadeProfissionalED> {
  //    public int compare(AtividadeProfissionalED p1, AtividadeProfissionalED p2) {
  //      return (p1.getTurnosAReceber() > p2.getTurnosAReceber() ? -1 : (p1.getTurnosAReceber() == p2.getTurnosAReceber() ? 0 : 1));
  //      // return p1.getTurnosAReceber().compareTo(p2.getTurnosAReceber());
  //    }
  //
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public void atualizaParametrosRedistribuicao(CronogramaParametrosED ed) {
  //    bd.atualizaParametros(ed);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public JuntaCronogramaED profissionalJahPossuiCompromisso(String docIdent, String ufIdent, Calendar dthAgenda, Integer turno) {
  //    JuntaCronogramaProfsED prof = agendaProfRN.jahPossuiCompromisso(docIdent, ufIdent, dthAgenda, turno);
  //
  //    if (prof == null)
  //      return null;
  //
  //    JuntaCronogramaED ed = this.consulta(prof.getNroIntAgendaJunta());
  //    return ed;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getListaCronogramaASerEncerrado(Calendar dthInicio, Calendar dthFim) {
  //    return bd.getListaCronogramaASerEncerrado(dthInicio, dthFim);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getCronogramaInterior(Integer situacao) {
  //    List<JuntaCronogramaED> listaCronograma = bd.getCronogramaInterior(situacao);
  //    for (JuntaCronogramaED cronogramaED : listaCronograma) {
  //      cronogramaED.setListaCronogramaProfs(agendaProfRN.getListaProfissionaisVinculadosAoCronograma(cronogramaED.getId()));
  //    }
  //
  //    return listaCronograma;
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  //  public void atualizaTurnosParaSituacaoDisponivel(List<JuntaCronogramaED> listaTurnosTodos) {
  //    for (JuntaCronogramaED ed : listaTurnosTodos) {
  //      ed.setSituacao(SituacaoCronogramaEnum.DISPONIVEL.getCodigo());
  //      this.altera(ed);
  //    }
  //
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  public List<String> getEmailProfissionaisVigente() {
  //    return bd.getEmailProfissionaisVigente();
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> buscaListaCronogramasComTurnosLotadosHoje(Calendar dthHoje) {
  //    return bd.buscaListaCronogramasComTurnosLotadosHoje(dthHoje);
  //  }
  //
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getTurnosEmAnaliseNoPeriodo(int samdInicio, int samdFim) {
  //    List<JuntaCronogramaED> listaCronograma = bd.getTurnosEmAnaliseNoPeriodo(HabWebUtil.samdToCalendar(samdInicio), HabWebUtil.samdToCalendar(samdFim));
  //    for (JuntaCronogramaED ed : listaCronograma) {
  //      ed.setListaCronogramaProfs(agendaProfRN.getListaProfissionaisVinculadosAoCronograma(ed.getId()));
  //    }
  //    return listaCronograma;
  //  }
  //
  //  //Busca todos os registros na HAB_JUNTA_CRONOGRAMA FILTRANDO PELO INTERVALO DE DATAS
  //  // E OS TURNOS QUE NÃO FORAM CANCELADOS
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public List<JuntaCronogramaED> getCronograma(int samdInicio, int samdFim) {
  //    return bd.getCronograma(HabWebUtil.samdToCalendar(samdInicio), HabWebUtil.samdToCalendar(samdFim));
  //  }
  //
  //  // RETORNA O TOTAL DE AGENDAMENTOS PARA O CRONOGRAMA
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public Integer getQuantAgendamentos(Long nroIntJuntaCronograma) {
  //    return bd.getQuantAgendamentos(nroIntJuntaCronograma);
  //  }
  //
  //  // VALIDA SE PROFISSIONAL ESTÁ ATUANDO EM ALGUM TURNO
  //  @Permissao(desabilitada = true)
  //  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  //  public Boolean validaProfTurnoSituacao(String docIdent, String ufDocIdent, SituacaoCronogramaEnum situacaoEnum) {
  //    if (docIdent == null || ufDocIdent == null || situacaoEnum == null)
  //      throw new RNException("Atenção! Favor informar DOC_IDENT, UF_DOC_IDENT E SITUACAO_ENUM");
  //
  //    return bd.validaProfTurnoSituacao(docIdent, ufDocIdent, situacaoEnum);
  //  }
  //  
  //  //-------------------------
  //  /** 
  //   * Valida quantidade de profissionais no turno(deve ser sempre 3), e tenta preencher as vagas 
  //   * 
  //   * @param  turnosSorteio
  //   * @return List<JuntaCronogramaED>
  //   */
  //  
  //  public List<JuntaCronogramaED> validaQuantProfsTurnECompletaProfis(List<JuntaCronogramaED> turnosSorteio){
  //       
  //    //... gera lista de profissionais com a quantidade de turnos
  //    List<ProfissionalED> listaProfsQtdTurno = new LinkedList<ProfissionalED>();         
  //    for(JuntaCronogramaED j: turnosSorteio){
  //      List<JuntaCronogramaProfsED> lJ =  j.getListaCronogramaProfs();
  //      ProfissionalED p;
  //      for(JuntaCronogramaProfsED cP: lJ ){
  //        p = cP.getProfissionalED();
  //        if(p.getQtdTurnos()==null){
  //          p.setQtdTurnos(0);            
  //        }
  //        //se nao existe inclui
  //        if(!listaProfsQtdTurno.contains(p)){
  //          p.setQtdTurnos(1);
  //          listaProfsQtdTurno.add(p);            
  //        }else{//se ja existe incrementa qtdTurnos do existente 
  //          int posi = listaProfsQtdTurno.indexOf(p); 
  //          ProfissionalED profAux = listaProfsQtdTurno.get(posi); 
  //          profAux.setQtdTurnos(profAux.getQtdTurnos()+1);            
  //        }
  //      }
  //    }
  //    
  //    logger.warn("### (inicio) verificando turnos incompletos...");
  //    
  //    //VERIFICA SE OS TURNOS ESTAO COMPLETOS (com 3 profissionais) -  completando se possivel
  //    for(JuntaCronogramaED  jCronSort : turnosSorteio){
  //      List<JuntaCronogramaProfsED> listJc =  jCronSort.getListaCronogramaProfs();
  //      int qtdPreenchidas = listJc.size();
  //      int qtdCicloTentativa = 0;
  //      while(qtdPreenchidas < 3 && qtdCicloTentativa<3/*limitado para nao entrar em loop infinito caso nao consiga preencher*/){
  //        qtdCicloTentativa++;
  //        logger.warn("## turno "+jCronSort.getNroIntJuntaCronograma()+" com "+qtdPreenchidas+" turnos. Completando...");
  //         this.reordena(listaProfsQtdTurno);
  //          for(ProfissionalED profis : listaProfsQtdTurno){ //lista ordenada pela quantidade de turnos recebidos
  //            boolean flagIncluir = false;
  //            
  //            //se profissional ja existe no turno, tenta com o proximo
  //            boolean jahPossuiProfTurn = false;
  //            for(JuntaCronogramaProfsED iJcp: listJc){
  //              if(iJcp.getDocIdentProfis().equals(profis.getProfissionaisPK().getDocIdent())){
  //                jahPossuiProfTurn = true;
  //              }              
  //            }
  //            if(jahPossuiProfTurn){
  //              continue;
  //            }            
  //            
  //            //obtem regitro da atividade profissional                       
  //            AtividadeProfissionalED  atividProfiED  = atividadeProfissionalRN.obtemRegAtividadeProfisMedPorDocIdent(profis.getProfissionaisPK().getDocIdent(), profis.getProfissionaisPK().getUfIdent());
  //            
  //            //valida se o profissional pode ser incluido neste turno
  //            flagIncluir = !this.validacoesProfissionalDisponivel(turnosSorteio, jCronSort, atividProfiED);
  //            
  //            if( flagIncluir == true){
  //              JuntaCronogramaProfsED jcpED = new JuntaCronogramaProfsED();
  //               jcpED.setProfissionalED(profis);
  //               jCronSort.getListaCronogramaProfs().add(jcpED);
  //               
  //               logger.warn("## incluido profissional codIndiv:"+ profis.getCodIndiv() ); 
  //               profis.setQtdTurnos(profis.getQtdTurnos()+1);
  //               qtdPreenchidas++;
  //               break; //
  //            }else{
  //              //tenta com o proximo medico profis
  //               continue;
  //            }
  //          } //profis           
  //      } //lista profis       
  //    } // turnos
  //    
  //    logger.warn("### (fim) verificando turnos incompletos...");
  //    
  //    return turnosSorteio;
  //    
  //  }
  //  
  //  //------------------- ordena lista pela qtd turnos
  //  
  //  private void reordena(List<ProfissionalED> listaProfsQtdTurno){
  //  
  //    Collections.sort(listaProfsQtdTurno,new Comparator(){
  //      
  //      public int compare(Object oa, Object ob){
  //        ProfissionalED pa = (ProfissionalED)oa;
  //        ProfissionalED pb = (ProfissionalED)ob;
  //        if(pa.getQtdTurnos()<pb.getQtdTurnos()){
  //          return -1;
  //        }else 
  //          if(pa.getQtdTurnos()>pb.getQtdTurnos()){
  //            return 1;
  //          }else 
  //            return 0;
  //      }
  //    } );
  //  
  //  }
  //  
  //  //-------------------            

}