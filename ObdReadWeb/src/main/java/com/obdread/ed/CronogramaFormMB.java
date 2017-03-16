package com.obdread.ed;

import com.procergs.arqjava4.context.MessageProvider;
import com.procergs.arqjava4.exception.RNException;
import com.procergs.arqjava4.faces.FacesUtil;
import com.procergs.arqjava4.report.JasperReportsExporter;
import com.procergs.arqjava4.report.ReportED;
import com.procergs.det.hab.ed.*;
import com.procergs.det.hab.ed.enums.SituacaoCronogramaEnum;
import com.procergs.det.hab.ed.enums.TipoComissaoEnum;
import com.procergs.det.hab.ed.gci.GID958RetornoED;
import com.procergs.det.hab.infra.AppFormMB;
import com.procergs.det.hab.util.HabWebUtil;
import com.procergs.det.util.DadosSessaoUtil;
import com.procergs.hab.credenciado.CredenciadoBD;
import com.procergs.hab.credenciado.CredenciadoRN;
import com.procergs.hab.ed.kgid.MunicipioED;
import com.procergs.hab.junta.agendahist.JuntaAgendaHistoricoRN;
import com.procergs.hab.junta.agendamento.JuntaAgendamentoCondutorRN;
import com.procergs.hab.junta.agendaprofs.JuntaAgendaProfissionalRN;
import com.procergs.hab.junta.comissao.JuntaComissaoRN;
import com.procergs.hab.junta.cronograma.JuntaCronogramaRN;
import com.procergs.hab.junta.localatuacao.ComissaoLocalAtuacaoRN;
import com.procergs.hab.renach.RenachRN;
import com.procergs.util.djs.Util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Named
@ViewScoped
public class CronogramaFormMB extends AppFormMB<JuntaCronogramaED, Long> {

  private static final long                        serialVersionUID     = 1L;

  @Inject
  JuntaCronogramaRN                                cronogramaRN;

  @Inject
  JuntaComissaoRN                                  juntaComissaoRN;

  @Inject
  RenachRN                                         renachRN;

  @Inject
  ComissaoLocalAtuacaoRN                           comissaoLocalAtuacaoRN;

  @Inject
  JuntaAgendaProfissionalRN                        agendaProfissionalRN;

  @Inject
  JuntaAgendamentoCondutorRN                       juntaAgendamentoCondutorRN;

  @Inject
  JuntaAgendaHistoricoRN                           juntaAgHistRN;

  @Inject
  MessageProvider                                  provider;

  @Inject
  JasperReportsExporter                            exporter;
  
  @Inject
  CredenciadoRN                                    credenciadoRN;

  private List<MunicipioED>                        listaMunicipio;
  private List<SelectItem>                         listaTurno;
  private List<SelectItem>                         listaTipoComissao;
  private List<SelectItem>                         listaSituacao;
  private AtividadeProfissionalED                  profs01;
  private AtividadeProfissionalED                  profs02;
  private AtividadeProfissionalED                  profs03;

  // LABEL DA MENSAGEM 'SUBSTITUIDO'
  private String                                   labelProfs01         = " ";
  private String                                   labelProfs02         = " ";
  private String                                   labelProfs03         = " ";
  private List<JuntaCronogramaHistoricoED>         listaHistoricoJunta;

  // LISTA COM AS SUBSTITUIÇÕES
  private List<CronogramaAgendaProfissionalSubsED> listaSubstituicoes;
  private List<JuntaAgendamentoCondutorED>         listaAgendamentos;
  private List<JuntaAgendamentoCondutorED>         listaAgendamentosSelecionados;
  private Boolean                                  btnSubstituicoes;
  private String                                   motivoCancelamento;

  // VALIDAÇÃO DIA UTIL E FERIADO
  private Boolean                                  diaUtilFeriado       = true;

  // MENSAGEM APRESENTADA AO USUÁRIO QUANDO O PROFISSIONAL ESTÁ ALOCADO EM
  // OUTRA JUNTA EM MENOS DE 72 HORAS
  private String                                   mensagemValidaJunta;

  // VALIDAÇÃO JUNTA
  private Boolean                                  validaJunta          = true;
  private String                                   mensagemTransferenciaCronograma;

  @Inject
  DadosSessaoUtil                                  dadosSessaoUtil;

  boolean                                          carregouProfs        = true;

  // LISTA DOS PROFISSIONAIS
  List<JuntaCronogramaProfsED>                     listaProfsCronograma = new ArrayList<JuntaCronogramaProfsED>();

  public String getMensagemValidaJunta() {
    return mensagemValidaJunta;
  }

  @PostConstruct
  void initRN() {
    super.setRN(cronogramaRN);

    listaTurno = this.getListaTurnos();
    listaTipoComissao = this.getListaTipoComissao();
    listaSituacao = this.getListaSituacao();

    profs01 = new AtividadeProfissionalED();
    profs02 = new AtividadeProfissionalED();
    profs03 = new AtividadeProfissionalED();

  }

  public List<AtividadeProfissionalED> completeText(String query) {
    return this.cronogramaRN.getProfissionaisPorNome(query.toUpperCase());
  }

  @Override
  public void init() {
    // TODO Auto-generated method stub
    super.init();

    // NÃO EXECUTA SE FOR AJAX
    if (!FacesContext.getCurrentInstance().isPostback()) {
      if (!isInclusao()) {
        this.listaHistoricoJunta = juntaAgHistRN.getHistoricoJunta(getEd().getId());
      }

      this.preencheListaMunicipio();
    }

    if (isInclusao()) {
      if (super.getEd().getTurno() == null) {
        super.getEd().setTurno(1); // SETA MANHÃ
      }
      // SETA A COMISSAO ATIVA
      this.setaComissaoInclusao();
    }

    if (!isInclusao()) {
      this.habilitaBotaoSubstituicoes();
    }

    if (isInclusao()) {

      if (super.getEd().getCredenciadoED() == null) {

        ComissaoLocalAtuacaoED localAtuacaoED;
        if (super.getEd().getTurno() == 1)
          localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "M");
        else
          localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "T");

        if (localAtuacaoED != null) {
          super.getEd().setCredenciadoED(new CredenciadoED());
          super.getEd().getCredenciadoED().setCodCliente(localAtuacaoED.getCredenciadoED().getCodCliente());

          this.setaHoraTurno(localAtuacaoED);

          // SETA O MAX ATENDIMENTO
          super.getEd().setAtendMax(localAtuacaoED.getMaxAtendimento());
        } else {
          super.getEd().setCredenciadoED(new CredenciadoED());
        }
      }

      if (carregouProfs) {
        this.sugestaoProfsNovoCronograma(super.getEd().getMunicipioED().getCodMunicipio());
      }

    }

    if (carregouProfs) {
      this.setProfissionaisCronograma();
    }

  }

  private void habilitaBotaoSubstituicoes() {
    // LISTA COM AS SUBSTITUIÇÕES
    this.listaSubstituicoes = cronogramaRN.getListaSubstituicoes(super.getEd().getId());

    // VERIFICA SE JÁ HOUVERAM SUBSTITUIÇÕES, CASO POSITIVO HABILITA O BOTÃO
    // SUBSTITUIÇÕES NA TELA
    if (this.listaSubstituicoes != null) {
      this.btnSubstituicoes = this.listaSubstituicoes.size() > 0;

      // RENDERIZA O LABEL 'SUBSTITUTO'
      for (CronogramaAgendaProfissionalSubsED ed : this.listaSubstituicoes) {
        if (profs01.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent() != null)
          if ((profs01.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent().equals(ed.getProfissionalAntED().getProfissionaisPK().getDocIdent()))) {
            labelProfs01 = "Substituto";
          }
        if (profs02.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent() != null)
          if ((profs02.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent().equals(ed.getProfissionalAntED().getProfissionaisPK().getDocIdent()))) {
            labelProfs02 = "Substituto";
          }
        if (profs03.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent() != null)
          if ((profs03.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent().equals(ed.getProfissionalAntED().getProfissionaisPK().getDocIdent()))) {
            labelProfs03 = "Substituto";
          }
      }
    }
  }

  private void preencheListaMunicipio() {
    if (!FacesContext.getCurrentInstance().isPostback()) {
      if (isInclusao()) {
        listaMunicipio = cronogramaRN.getMunicipiosDeJunta();
        if (super.getEd().getMunicipioED() == null)
          super.getEd().setMunicipioED(listaMunicipio.get(0));
      } else // EDIÇÃO/DETALHES
        listaMunicipio = cronogramaRN.getMunicipiosQueJaTiveramJunta();
    }
  }

  private void setaComissaoInclusao() {
    JuntaComissaoED juntaComissaoED = juntaComissaoRN.getComissaoAtiva(TipoComissaoEnum.MEDICA_ESPECIAL_DETRAN.getCodigo());
    if (juntaComissaoED != null)
      super.getEd().setJuntaComissaoED(juntaComissaoED);
    else
      FacesUtil.addErrorMessage("Atenção! Não existe uma comissão ativa para Junta Médica Especial. Favor verificar.");
  }

  private void setaHoraTurno(ComissaoLocalAtuacaoED localAtuacaoED) {
    // HORÁRIO DO TURNO DEPENDE DA MANHÃ OU TARDE
    if (localAtuacaoED != null) {
      if (super.getEd().getTurno() == null) {
        // CONSIDERA MANHÃ
        super.getEd().setHmInicio(localAtuacaoED.getHmInicioManha());
      } else {
        // MANHÃ
        if (super.getEd().getTurno() == 1)
          if (localAtuacaoED.getTurnoAtend().indexOf('M') == 0) {
            super.getEd().setHmInicio(localAtuacaoED.getHmInicioManha());
          } else {
            super.getEd().setHmInicio(0);
            throw new RNException("Atenção! Município não permite atendimento no turno da manhã.");
          }

        // TARDE
        if (super.getEd().getTurno() == 2)
          if (localAtuacaoED.getTurnoAtend().length() > 1) {
            if (localAtuacaoED.getTurnoAtend().indexOf('T') == 1) {
              super.getEd().setHmInicio(localAtuacaoED.getHmInicioTarde());
            } else {
              super.getEd().setHmInicio(0);
              throw new RNException("Atenção! Município não permite atendimento no turno da tarde.");
            }
          } else {
            if (localAtuacaoED.getTurnoAtend().indexOf('T') == 0) {
              super.getEd().setHmInicio(localAtuacaoED.getHmInicioTarde());
            } else {
              super.getEd().setHmInicio(0);
              throw new RNException("Atenção! Município não permite atendimento no turno da tarde.");
            }

          }

      }
    } else {
      super.getEd().setHmInicio(0);
    }
  }

  private void setProfissionaisCronograma() {
    if (!isInclusao())
      listaProfsCronograma = agendaProfissionalRN.getListaProfissionaisVinculadosAoCronograma(super.getEd().getId());

    if (carregouProfs && listaProfsCronograma.size() == 0) {
      this.anulaProfissionais();
    }

    ProfissionalED edProfs;
    if (listaProfsCronograma.size() > 0 && carregouProfs) {
      if (listaProfsCronograma.size() > 0) {
        edProfs = listaProfsCronograma.get(0).getProfissionalED();

        profs01.getAtividadeProfissionalPK().setProfissionalED(edProfs);
        profs01.getAtividadeProfissionalPK().setTpProfis(29);
        profs01.setNome(edProfs.getNome());
      }

      if (listaProfsCronograma.size() > 1) {

        edProfs = listaProfsCronograma.get(1).getProfissionalED();

        profs02.getAtividadeProfissionalPK().setProfissionalED(edProfs);
        profs02.getAtividadeProfissionalPK().setTpProfis(29);
        profs02.setNome(edProfs.getNome());
      }

      if (listaProfsCronograma.size() > 2) {

        edProfs = listaProfsCronograma.get(2).getProfissionalED();

        profs03.getAtividadeProfissionalPK().setProfissionalED(edProfs);
        profs03.getAtividadeProfissionalPK().setTpProfis(29);
        profs03.setNome(edProfs.getNome());
      }

    }

    carregouProfs = false;

  }

  private void anulaProfissionais() {

    profs01.setNome(null);
    profs01.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setDocIdent("0");
    profs01.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setUfIdent("0");
    profs01.getAtividadeProfissionalPK().setTpProfis(0);

    profs02.setNome(null);
    profs02.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setDocIdent("0");
    profs02.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setUfIdent("0");
    profs02.getAtividadeProfissionalPK().setTpProfis(0);

    profs03.setNome(null);
    profs03.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setDocIdent("0");
    profs03.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().setUfIdent("0");
    profs03.getAtividadeProfissionalPK().setTpProfis(0);

  }

  @Override
  public JuntaCronogramaED criaED() {
    JuntaCronogramaED ed = new JuntaCronogramaED();
    return ed;
  }

  private List<SelectItem> getListaSituacao() {

    List<SelectItem> lstReturn = new LinkedList<SelectItem>();

    for (SituacaoCronogramaEnum e : SituacaoCronogramaEnum.getAll()) {
      lstReturn.add(new SelectItem(e.getCodigo(), e.getDescricao()));
    }

    return lstReturn;
  }

  /**
   * EXECUTA A PUBLICAÇÃO DO CRONOGRAMA.
   */
  public void publicarCronograma() {
    if (super.getEd().getSituacao() == 7) {
      
      if(listaProfsCronograma.size() > 0)
        super.getEd().setListaCronogramaProfs(listaProfsCronograma);
      
      this.cronogramaRN.publicarCronograma(super.getEd());
      FacesUtil.addInfoMessage("Atenção! Cronograma Publicado com Sucesso!");
    } else {
      FacesUtil.addErrorMessage("Atenção! Publicação deve ocorrer somente para cronograma com situação 'EM ANÁLISE'");
    }
  }

  private List<SelectItem> getListaTipoComissao() {
    List<SelectItem> lstReturn = new LinkedList<SelectItem>();
    for (TipoComissaoEnum e : TipoComissaoEnum.getAll()) {
      lstReturn.add(new SelectItem(e.getCodigo(), e.getDescricao()));
    }
    return lstReturn;
  }

  private List<SelectItem> getListaTurnos() {
    List<SelectItem> lstReturn = new LinkedList<SelectItem>();
    lstReturn.add(new SelectItem(1, "Manhã"));
    lstReturn.add(new SelectItem(2, "Tarde"));
    return lstReturn;
  }

  /**
   * Método para Cancelar o Cronograma.
   */
  public String cancelar() {
    cronogramaRN.cancelaTurno(getEd(), motivoCancelamento);
    FacesUtil.addInfoMessage("Atenção! Cancelamento do turno executado com sucesso!");
    this.retornaTelaLista();

    return "junta-cronograma-list?faces-redirect=trueflagPesquisa=1";
  }

  /**
   * Método que monta mensagm de confrmação de cancelamento, considerando se o
   * cronograma a ser cancelado possui atendimentos agendados ou não.
   * 
   * @return String
   */
  public String exibeMensagemCancelamento() {
    if (getEd().getAtendAgend() != null && getEd().getAtendAgend() > 0) {
      return "Existem " + getEd().getAtendAgend() + " agendamentos. Confirma cancelamento?";
    } else {
      return "Confirma cancelamento?";
    }
  }

  
  @Override
  public void salva() {
    salvaInterno(true);  
  }
   
  public void confirmaSalvar(){
    
    String codCred = this.getEd().getCredenciadoED().getCodCliente();
    CredenciadoED credED = credenciadoRN.buscarCredenciado(codCred);
    
    if(credED==null){
      FacesUtil.addErrorMessage("Credenciado inexistente. Favor verificar."); 
      return;
    }
    
    salvaInterno(false);  
  }
  
  
  private void salvaInterno(boolean valCreden) {
    
    
     if(valCreden){ 
      if (this.validaCredenciadoInformado()){
        return;
      }
     }
    
      

    JuntaCronogramaED ed = super.getEd();

    //   ed.setSamdAgenda(Util.calendarParaYYYYMMDD(ed.getDataAgenda()));
    ed.setHmInicio(HabWebUtil.horaParaHM(ed.getHoraInicio()));

    // VALIDA SE A DATA É DIA UTIL E FERIADO
    if (this.diaUtilFeriado)
      if (cronogramaRN.verifidaDiaUtileFeriado(HabWebUtil.samdToString(ed.getSamdAgenda()))) {
        /*
         * FacesUtil.addInfoMessage(provider.getMessage(
         * "A data informada não é um dia útil. Favor verificar."));
         */
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('diaUtilFeriadoDialog').show();");
        return;
      }

    // VALIDA SE A DATA DO AGENDAMENTO NÃO É MAIOR QUE A DATA DE VENCIMENTO
    // DA JUNTA
    if (cronogramaRN.validaDataEncerramentoComissao(ed.getJuntaComissaoED(), ed.getSamdAgenda())) {
      FacesUtil.addInfoMessage("A data de agendamento informada é maior que a data de vencimento da Junta. Favor verificar.");
      return;
    }

    // VERIFICA SE OS 3 PROFISSIONAIS FORAM INFORMADOS E SE SAO DIFERENTES
    if (this.validaProfissionais())
      return;

    // VALIDA 72 horas: "O perito <nome perito>, RG <RG perito> está
    // escalado para atuar em menos de 72 horas na cidade <cidade>. Deseja
    // continuar?
    if (validaJunta) {

      String municipio = null;

      // TESTA PROFS01
      municipio = this.validaPeritoAtuandoEmOutraJunta(profs01, ed.getSamdAgenda());
      if (municipio != null) {

        // SOMENTE SE MUNICIPIO DIFERENTE
        if (!municipio.equals(ed.getMunicipioED().getNomeMunicipio())) {
          this.showMessageValidacaoJunta(profs01, municipio);
          return;
        }

      }

      // TESTA PROFS02
      municipio = this.validaPeritoAtuandoEmOutraJunta(profs02, ed.getSamdAgenda());
      if (municipio != null) {

        // SOMENTE SE MUNICIPIO DIFERENTE
        if (!municipio.equals(ed.getMunicipioED().getNomeMunicipio())) {
          this.showMessageValidacaoJunta(profs02, municipio);
          return;
        }
      }

      // TESTA PROFS03
      municipio = this.validaPeritoAtuandoEmOutraJunta(profs03, ed.getSamdAgenda());
      if (municipio != null) {

        // SOMENTE SE MUNICIPIO DIFERENTE
        if (!municipio.equals(ed.getMunicipioED().getNomeMunicipio())) {
          this.showMessageValidacaoJunta(profs03, municipio);
          return;
        }
      }

    }

    // SETA OS 3 PROFISSIONAIS SELECIONADOS
    ed.setListaCronogramaProfs(this.getAgendaProfissionaisCronograma(profs01));
    ed.setListaCronogramaProfs(this.getAgendaProfissionaisCronograma(profs02));
    ed.setListaCronogramaProfs(this.getAgendaProfissionaisCronograma(profs03));

    super.setEd(ed);
    super.salva();
    this.retornaTelaLista();
  }

  private void retornaTelaLista() {
    try {
      FacesContext.getCurrentInstance().getExternalContext().redirect("junta-cronograma-list.xhtml?flagPesquisa=1");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showMessageValidacaoJunta(AtividadeProfissionalED prof, String municipio) {

    mensagemValidaJunta = "O perito <" + prof.getNome() + ">, RG<" + prof.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent() + ">" + " está escalado para atuar em menos de 72 horas na cidade <" + municipio + ">. Deseja continuar?";

    RequestContext context = RequestContext.getCurrentInstance();
    context.update("atuacaoDialog");
    context.execute("PF('atuacaoDialog').show();");
  }

  private JuntaCronogramaProfsED getAgendaProfissionaisCronograma(AtividadeProfissionalED prof) {
    JuntaCronogramaProfsED agendaProf = new JuntaCronogramaProfsED();
    //agendaProf.setNome(prof.getNome());
    ProfissionalED edProfs = new ProfissionalED();

    edProfs.getProfissionaisPK().setDocIdent(prof.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent());
    edProfs.getProfissionaisPK().setUfIdent(prof.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getUfIdent());

    // SETA O PROFISSIONAL
    agendaProf.setProfissionalED(edProfs);
    agendaProf.setTipoProfis(prof.getAtividadeProfissionalPK().getTpProfis());
    agendaProf.setNroIntAgendaJunta(super.getEd().getId());

    return agendaProf;
  }

  private String validaPeritoAtuandoEmOutraJunta(AtividadeProfissionalED profs, Integer samd) {
    return agendaProfissionalRN.validaProfissionalEmOutraJunta(profs.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getDocIdent(), profs.getAtividadeProfissionalPK().getProfissionalED().getProfissionaisPK().getUfIdent(), Util.paraCalendar(samd - 3));
  }

  // VERIFICA SE OS 3 PROFISSIONAIS FORAM INFORMADOS E SE SAO DIFERENTES
  private Boolean validaProfissionais() {

    // VERIFICA SE OS 3 PROFISSIONAIS FORAM INFORMADOS
    if (this.profs01 == null || this.profs01.getNome() == null) {
      FacesUtil.addInfoMessage("Favor informar os 3 profissionais.");
      return true;
    }

    // VERIFICA SE OS 3 PROFISSIONAIS FORAM INFORMADOS
    if (this.profs02 == null || this.profs02.getNome() == null) {
      FacesUtil.addInfoMessage("Favor informar os 3 profissionais.");
      return true;
    }

    // VERIFICA SE OS 3 PROFISSIONAIS FORAM INFORMADOS
    if (this.profs03 == null || this.profs03.getNome() == null) {
      FacesUtil.addInfoMessage("Favor informar os 3 profissionais.");
      return true;
    }

    // VERIFICA SE SÃO DIFERENTES
    if (this.profs01.getNome().equals(this.profs02.getNome())) {
      FacesUtil.addInfoMessage("Atenção! Os 3 profissionais informados devem ser diferentes.");
      return true;
    }

    // VERIFICA SE SÃO DIFERENTES
    if (this.profs01.getNome().equals(this.profs03.getNome())) {
      FacesUtil.addInfoMessage("Atenção! Os 3 profissionais informados devem ser diferentes.");
      return true;
    }

    // VERIFICA SE SÃO DIFERENTES
    if (this.profs02.getNome().equals(this.profs03.getNome())) {
      FacesUtil.addInfoMessage("Atenção! Os 3 profissionais informados devem ser diferentes.");
      return true;
    }

    return false;

  }

  public void validaDiaUtileFeriado() {
    this.diaUtilFeriado = false;
    this.salva();
  }

  public void validaProfEmOutraJunta() {
    this.validaJunta = false;
    this.salva();
  }

  @Override
  public String exclui() {

    // Apresenta a Mensagem do Dialog antes de executar o Redirect
    FacesContext context = FacesContext.getCurrentInstance();
    context.getExternalContext().getFlash().setKeepMessages(true);

    return super.exclui();
  }

  // ALTERA O CREDENCIADO VIA AJAX
  public void alteraCredenciado(AjaxBehaviorEvent vce) {

    if (isInclusao()) {

      ComissaoLocalAtuacaoED localAtuacaoED;
      if (super.getEd().getTurno() == 1)
        localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "M");
      else
        localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "T");

      if (localAtuacaoED != null) {
        super.getEd().setCredenciadoED(localAtuacaoED.getCredenciadoED());
        this.setaHoraTurno(localAtuacaoED);
        super.getEd().setAtendMax(localAtuacaoED.getMaxAtendimento());
      } else {
        super.getEd().setCredenciadoED(null);
        super.getEd().setAtendMax(0);
        super.getEd().setHmInicio(0);
      }

      // ADICIONA COMO SUGESTÃO OS 3 PROFISSIONAIS
      this.sugestaoProfsNovoCronograma(super.getEd().getMunicipioED().getCodMunicipio());
    }

  }

  public void alteraCredenciadoTurno(AjaxBehaviorEvent vce) {

    if (isInclusao()) {

      ComissaoLocalAtuacaoED localAtuacaoED;
      if (super.getEd().getTurno() == 1)
        localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "M");
      else
        localAtuacaoED = cronogramaRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), "T");

      if (localAtuacaoED != null) {
        super.getEd().setCredenciadoED(localAtuacaoED.getCredenciadoED());
      } else
        super.getEd().setCredenciadoED(null);

      this.setaHoraTurno(localAtuacaoED);
    }

  }

  private void sugestaoProfsNovoCronograma(Integer codMunicipio) {
    listaProfsCronograma = cronogramaRN.sugestaoProfsNovoCronograma(codMunicipio);
    carregouProfs = true;
    this.setProfissionaisCronograma();
  }

  public void regerarCronograma() {
    if (cronogramaRN.regerarCronograma())
      FacesUtil.addInfoMessage("Atenção! O cronograma será regerado nas próximas 24 horas.");
  }

  // ALTERA O CAMPO DATA AGENDA DO CRONOGRAMA
  public void validaTransferenciaCronograma() {

    // TICKET #87348
    if (cronogramaRN.verificaTurnoMesmoDia(super.getEd()).size() > 0) {
      mensagemTransferenciaCronograma = "Já existe cronograma para a mesma data, turno e município escolhido. Confirma a alteração de data para o dia " + super.getEd().getDthAgenda() + "? Lembrete: O aviso aos condutores agendados, profissionais e CFC deverá ser feito manualmente pelo DETRAN.";
    } else {

      // VERIFICA OS 3 PROFISSIONAIS
      ProfissionalED profED01 = listaProfsCronograma.get(0).getProfissionalED();
      ProfissionalED profED02 = listaProfsCronograma.get(1).getProfissionalED();
      ProfissionalED profED03 = listaProfsCronograma.get(2).getProfissionalED();

      String municipio01 = agendaProfissionalRN.validaProfissionalEmOutraJunta(profED01.getProfissionaisPK().getDocIdent(), profED01.getProfissionaisPK().getUfIdent(), super.getEd().getDthAgenda());
      String municipio02 = agendaProfissionalRN.validaProfissionalEmOutraJunta(profED02.getProfissionaisPK().getDocIdent(), profED02.getProfissionaisPK().getUfIdent(), super.getEd().getDthAgenda());
      String municipio03 = agendaProfissionalRN.validaProfissionalEmOutraJunta(profED03.getProfissionaisPK().getDocIdent(), profED03.getProfissionaisPK().getUfIdent(), super.getEd().getDthAgenda());

      if (municipio01 != null) {
        mensagemTransferenciaCronograma = "O profissional '" + profED01.getNome() + "' encontra-se na junta de '" + municipio01 + "' no mesmo dia escolhido. Confirma a alteração de data para o dia " + super.getEd().getDthAgenda() + "? Lembrete: O aviso aos condutores agendados, profissionais e CFC deverá ser feito manualmente pelo DETRAN.";
      } else if (municipio02 != null) {
        mensagemTransferenciaCronograma = "O profissional '" + profED02.getNome() + "' encontra-se na junta de '" + municipio02 + "' no mesmo dia escolhido. Confirma a alteração de data para o dia " + super.getEd().getDthAgenda() + "? Lembrete: O aviso aos condutores agendados, profissionais e CFC deverá ser feito manualmente pelo DETRAN.";
      } else if (municipio03 != null) {
        mensagemTransferenciaCronograma = "O profissional '" + profED03.getNome() + "' encontra-se na junta de '" + municipio03 + "' no mesmo dia escolhido. Confirma a alteração de data para o dia " + super.getEd().getDthAgenda() + "? Lembrete: O aviso aos condutores agendados, profissionais e CFC deverá ser feito manualmente pelo DETRAN.";
      } else {
        mensagemTransferenciaCronograma = "Confirma a alteração de data para o dia " + super.getEd().getSamdAgendaFormatada() + "? Lembrete: O aviso aos condutores agendados, profissionais e CFC deverá ser feito manualmente pelo DETRAN.";
      }
    }

    RequestContext context = RequestContext.getCurrentInstance();
    context.update("validaTransferencia");
    context.execute("PF('validaTransferencia').show();");

  }

  // ATUALIZA A DATA DO CRONOGRAMA
  public void transfereCronograma() {
    cronogramaRN.transfereCronograma(super.getEd());
    FacesUtil.addInfoMessage("Atenção! A data de agendamento foi alterada com sucesso.");
  }

  public boolean validaCredenciadoInformado() {
    if (super.getEd().getCredenciadoED().getCodCliente() != null) {
      ComissaoLocalAtuacaoED ed = comissaoLocalAtuacaoRN.getLocalAtuacaoPorCredenciado(super.getEd().getCredenciadoED().getCodCliente());

      if (ed == null) {
        
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("confirmaTrocaCredenciadoDialog");
        context.execute("PF('confirmaTrocaCredenciadoDialog').show();");
        
        return true;
      }
    }
    return false;
  }

  public StreamedContent relQuestionarioAnamnese() {
    
    if (listaAgendamentosSelecionados.size() == 0) {
      FacesUtil.addErrorMessage("Atenção! Selecionar ao menos um registro para imprimir o Questionário de Anamnese");
      return null;
    }
    
    List<String> lista = new ArrayList<String>();
    lista.add("Teste");
    
    ReportED report = null;
    byte[] pdf = null;
    JasperPrint jasperPrint = null;
    FacesContext context = FacesContext.getCurrentInstance();
    
    for (int i = 0; i < listaAgendamentosSelecionados.size(); i++) {
    
      JuntaAgendamentoCondutorED agCondED = listaAgendamentosSelecionados.get(i);
      JuntaCronogramaED cronogramaED = listaAgendamentosSelecionados.get(i).getJuntaCronogramaED();
      IndividuoED individuoED = listaAgendamentosSelecionados.get(i).getIndividuoED();
    
      report = new ReportED("/reports/QuestionarioAnammese.jasper", lista);
      report.addParameter("P_NOME_CANDIDATO", individuoED.getNome());
      report.addParameter("P_SEXO", HabWebUtil.converteSexo(individuoED.getSexo()));
      report.addParameter("P_IDADE", HabWebUtil.calculaIdade(individuoED.getSamdNasc()).toString() + " anos");
      report.addParameter("P_IDENTIDADE", individuoED.getIdentidade());
      report.addParameter("P_UF", individuoED.getUfIdent());
      report.addParameter("P_CATEGORIA_ATUAL", individuoED.getCategoriaAtual());
    
      // Dados do agendamento
      report.addParameter("P_LOCAL_AGEND", cronogramaED.getMunicipioED().getNomeMunicipio() + " - " + cronogramaED.getCredenciadoED().getRazaoSocial());
      report.addParameter("P_DATA_AGEND", cronogramaED.getSamdAgendaFormatada() + " - " + agCondED.getHmAgendaFormatada());
      report.addParameter("P_TURNO_AGEND", cronogramaED.getTurno() == 1 ? "Manhã" : "Tarde");
    
      // Busca Renach ativo
      RenachED renachED = renachRN.getRenachAtivo(individuoED.getCodIndiv());
    
      report.addParameter("P_CATEG_PRETEN", this.getCategoriaPretendida(renachED));
    
      // SETA OS SERVIÇOS
      report.addParameter("P_SERVICOS", renachRN.getServicosRenach(renachED));
    
      if (i == 0)
        jasperPrint = exporter.getJasperPrint(report);
    
      InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/classes/reports/QuestionarioAnammese.jasper");
    
      JRPrintPage paginasGerada = null;
      try {
        paginasGerada = (JRPrintPage) JasperFillManager.fillReport(reportStream, report.getParameters(), new JREmptyDataSource()).getPages().get(0);
      } catch (JRException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    
      if (i > 0)
        jasperPrint.addPage(paginasGerada);
    }
    
    try {
      pdf = JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    InputStream is = new ByteArrayInputStream(pdf);
    return new DefaultStreamedContent(is, "application/pdf", "QuestionarioAnammese.pdf");
  }

  private String getCategoriaPretendida(RenachED renachED) {

    if (renachED == null || renachED.getCategPret() == null)
      return null;

    String cat2R, cat4R, ret;

    ret = "ACC( ), ";
    if (renachED.getCategPret().indexOf("A") >= 0)
      ret += "A(X), ";
    else
      ret += "A( ), ";
    if (renachED.getCategPret().indexOf("B") >= 0)
      ret += "B(X), ";
    else
      ret += "B( ), ";
    if (renachED.getCategPret().indexOf("C") >= 0)
      ret += "C(X), ";
    else
      ret += "C( ), ";
    if (renachED.getCategPret().indexOf("D") >= 0)
      ret += "D(X), ";
    else
      ret += "D( ), ";
    if (renachED.getCategPret().indexOf("E") >= 0)
      ret += "E(X)";
    else
      ret += "E( )";

    return ret;
  }

  public StreamedContent relAgendamentosCronograma() {

    List<String> lista = new ArrayList<String>();
    lista.add("Teste");
    
    ReportED report = new ReportED("/reports/RelAgendamentosCronograma.jasper", lista);
    ComissaoLocalAtuacaoED localAtuacaoED = comissaoLocalAtuacaoRN.getLocalAtuacao(super.getEd().getMunicipioED().getCodMunicipio(), super.getEd().getTurno());
    
    byte[] pdf = null;
    JasperPrint jasperPrint = null;
    FacesContext context = FacesContext.getCurrentInstance();
    
    String totalAgendamentos = listaAgendamentos.size() + " agendamentos.";
    String titulo = "AGENDAMENTOS: " + super.getEd().getMunicipioED().getNomeMunicipio() + " - " + localAtuacaoED.getCredenciadoED().getRazaoSocial() + " - " + super.getEd().getSamdAgendaFormatada() + " - " + super.getEd().getHmInicioFormatado();
    
    report.addParameter("P_TITULO", titulo);
    report.addParameter("P_RODAPE", dadosSessaoUtil.montaDadosSessao().getSOE_SiglaOrganizacao() + " - " + dadosSessaoUtil.montaDadosSessao().getSOE_Matricula());
    report.addParameter("P_TOTAL_AGENDAMENTOS", totalAgendamentos);
    report.addParameter("P_PERITO1", listaProfsCronograma.get(0).getProfissionalED().getNome());
    report.addParameter("P_PERITO2", listaProfsCronograma.get(1).getProfissionalED().getNome());
    report.addParameter("P_PERITO3", listaProfsCronograma.get(2).getProfissionalED().getNome());
    
    StringBuilder textoObs = new StringBuilder();
    Boolean obs1 = false, obs2 = false, obs3 = false;
    
    List<JuntaAgendamentoCondutorED> listaAgendamentosRel = new ArrayList<JuntaAgendamentoCondutorED>();
    for (JuntaAgendamentoCondutorED ed : listaAgendamentos) {
      IndividuoED individuoED = ed.getIndividuoED();
    
      // Seta a combinação de serviço ao lado do renach
      ed.setUltimoRenach(ed.getUltimoRenach() + " " + this.getCombServico(individuoED));
    
      GID958RetornoED retorno958ED = juntaAgendamentoCondutorRN.validadeCNH(ed);
    
      if (retorno958ED != null) {
        if (retorno958ED.getIndRecolhe() == 1) {
          individuoED.setNome(ed.getIndividuoED().getNome() + " (2)");
          obs2 = true;
        } else if (retorno958ED.getIndRecolhe() == 2) {
          individuoED.setNome(ed.getIndividuoED().getNome() + " (3)");
          obs3 = true;
        } else if (individuoED.getSamdValid() > 0) {
          // Adiciona 30 dias na valida da CNH
          Integer varDataValidadeCNH = HabWebUtil.getSamdMaisSomaDeDias(individuoED.getSamdValid(), 30);
          if (varDataValidadeCNH > super.getEd().getSamdAgenda()) {
            individuoED.setNome(ed.getIndividuoED().getNome() + " (1)");
            obs1 = true;
          }
        }
      }
      ed.setIndividuoED(individuoED);
      listaAgendamentosRel.add(ed);
    }
    
    if (obs1)
      textoObs.append("(1) Condutor com documento válido e passível de recolhimento na data do exame.\n");
    if (obs2)
      textoObs.append("(2) Condutor com documento já recolhido.\n");
    if (obs3)
      textoObs.append("(3) Documento com recolhimento pendente.\n");
    
    textoObs.append("Índices de serviços: (1º) Primeira Habilitação, (R) Renovação, (D) CNH Definitiva, (M) Mudança de categoria, (A) Adição de Categoria, (O) Outros Serviços.");
    report.addParameter("P_TEXTO_OBS", textoObs.toString());
    
    // Datasource para o relatório criado a partir de uma lista de Beans
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaAgendamentosRel);
    
    InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/classes/reports/RelAgendamentosCronograma.jasper");
    try {
      jasperPrint = JasperFillManager.fillReport(reportStream, report.getParameters(), ds);
      pdf = JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e1) {
      e1.printStackTrace();
    }
    
    InputStream is = new ByteArrayInputStream(pdf);
    return new DefaultStreamedContent(is, "application/pdf", "RelatorioAgendamentosCronograma.pdf");
  }

  private String getCombServico(IndividuoED individuoED) {
    // Busca Renach ativo
    RenachED renachED = renachRN.getRenachAtivo(individuoED.getCodIndiv());

    if (renachED != null)
      switch (renachED.getCombServ()) {
        case 55:
          return "(1a)";
        case 78:
          return "(R)";
        case 5:
          return "(D)";
        case 79:
          return "(M)";
        case 19:
          return "(A)";
        default:
          return "(O)";
      }

    return null;
  }

  // IMPRIME INDIVIDUOS AGENDADOS
  // ABRE UMA NOVA ABA COM O RELATÓRIO
  // RELATORIO SERÁ ABERTO NO VB ENTÃO PRECISAMOS FAZER O DONWLOAD DO ARQUIVO
  /* NO XHTML USAMOS:
   * <h:commandLink  
                 value="Teste"  
                 target="_blank"  
                 actionListener="#{cronogramaFormMB.emitirRelatorioAgendamentos}" >
                 </h:commandLink>
   * */
  public void emitirRelatorioAgendamentosNovaAba(ActionEvent action) {

    List<String> lista = new ArrayList<String>();
    lista.add("Teste");

    ReportED report = null;
    byte[] pdf = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    JasperPrint jasperPrint = null;

    report = new ReportED("/reports/QuestionarioAnammese.jasper", lista);
    report.addParameter("P_NOME_CANDIDATO", "JOÃO DA SILVA: ");
    jasperPrint = exporter.getJasperPrint(report);

    for (int i = 0; i < 10; i++) {

      report = new ReportED("/reports/QuestionarioAnammese.jasper", lista);
      report.addParameter("P_NOME_CANDIDATO", "JOÃO DA SILVA: " + i);

      FacesContext context = FacesContext.getCurrentInstance();
      InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/classes/reports/QuestionarioAnammese.jasper");

      JRPrintPage paginasGerada = null;
      try {
        paginasGerada = (JRPrintPage) JasperFillManager.fillReport(reportStream, report.getParameters(), new JREmptyDataSource()).getPages().get(0);
      } catch (JRException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      jasperPrint.addPage(paginasGerada);
    }

    try {
      pdf = JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    FacesContext facesContext = FacesContext.getCurrentInstance(); //Get the context ONCE    
    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    try {
      ServletOutputStream servletOutputStream = response.getOutputStream();
      response.setContentType("application/pdf");
      //response.addHeader("Content-Disposition", "inline; filename=folhaPonto.pdf");    

      response.addHeader("Content-disposition", "inline; filename=\"QuestionarioAnammese.pdf\"");
      byte[] bytes = pdf;

      servletOutputStream.write(bytes, 0, bytes.length);

      servletOutputStream.flush();
      servletOutputStream.close();

      facesContext.responseComplete();
    } catch (IOException ex) {

    }

  }

  public List<MunicipioED> getlistaMunicipio() {
    return listaMunicipio;
  }

  public void setlistaMunicipio(List<MunicipioED> listaMunicipio) {
    this.listaMunicipio = listaMunicipio;
  }

  public List<SelectItem> getlistaTurno() {
    return listaTurno;
  }

  public void setlistaTurno(List<SelectItem> listaTurno) {
    this.listaTurno = listaTurno;
  }

  public List<SelectItem> getlistaTipoComissao() {
    return listaTipoComissao;
  }

  public void setlistaTipoComissao(List<SelectItem> listaTipoComissao) {
    this.listaTipoComissao = listaTipoComissao;
  }

  public List<SelectItem> getlistaSituacao() {
    return listaSituacao;
  }

  public void setlistaSituacao(List<SelectItem> listaSituacao) {
    this.listaSituacao = listaSituacao;
  }

  public List<JuntaCronogramaHistoricoED> getListaHistoricoJunta() {
    return listaHistoricoJunta;
  }

  public void carregaAgendamentos() {
    if (listaAgendamentos == null)
      listaAgendamentos = juntaAgendamentoCondutorRN.getCondutoresAgendadosNoTurno(super.getId());
    
    this.listaAgendamentosSelecionados = this.listaAgendamentos;
    RequestContext.getCurrentInstance().update("formAgdto");
  }

  public List<JuntaAgendamentoCondutorED> getListaAgendamentos() {
    return listaAgendamentos;
  }

  public void validaCampoHora(ValueChangeEvent event) {
    String hora = (String) event.getNewValue();
    FacesUtil.addInfoMessage(provider.getMessage("registro.cep.verifica-cep"));
  }

  public List<CronogramaAgendaProfissionalSubsED> getListaSubstituicoes() {
    return listaSubstituicoes;
  }

  public String getLabelProfs01() {
    return labelProfs01;
  }

  public String getLabelProfs02() {
    return labelProfs02;
  }

  public String getLabelProfs03() {
    return labelProfs03;
  }

  public String getMotivoCancelamento() {
    return motivoCancelamento;
  }

  public void setMotivoCancelamento(String motivoCancelamento) {
    this.motivoCancelamento = motivoCancelamento;
  }

  public AtividadeProfissionalED getProfs01() {
    return profs01;
  }

  public void setProfs01(AtividadeProfissionalED profs01) {
    this.profs01 = profs01;
  }

  public AtividadeProfissionalED getProfs02() {
    return profs02;
  }

  public void setProfs02(AtividadeProfissionalED profs02) {
    this.profs02 = profs02;
  }

  public AtividadeProfissionalED getProfs03() {
    return profs03;
  }

  public void setProfs03(AtividadeProfissionalED profs03) {
    this.profs03 = profs03;
  }

  public Boolean getBtnSubstituicoes() {
    return btnSubstituicoes;
  }

  public void setBtnSubstituicoes(Boolean btnSubstituicoes) {
    this.btnSubstituicoes = btnSubstituicoes;
  }

  public String getMensagemTransferenciaCronograma() {
    return mensagemTransferenciaCronograma;
  }

  public List<JuntaAgendamentoCondutorED> getListaAgendamentosSelecionados() {
    return listaAgendamentosSelecionados;
  }

  public void setListaAgendamentosSelecionados(List<JuntaAgendamentoCondutorED> listaAgendamentosSelecionados) {
    this.listaAgendamentosSelecionados = listaAgendamentosSelecionados;
  }

}