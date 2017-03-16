package com.obdread.ed;

import com.procergs.arqjava4.faces.FacesUtil;
import com.procergs.det.hab.ed.CronogramaParametrosED;
import com.procergs.det.hab.ed.JuntaCronogramaED;
import com.procergs.det.hab.ed.enums.ParametrosCronogramaJuntaEnum;
import com.procergs.det.hab.ed.enums.SituacaoCronogramaEnum;
import com.procergs.det.hab.infra.AppListMB;
import com.procergs.det.hab.util.HabWebUtil;
import com.procergs.hab.ed.kgid.MunicipioED;
import com.procergs.hab.junta.cronograma.JuntaCronogramaRN;
import com.procergs.hab.municipio.MunicipioRN;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class CronogramaListMB extends AppListMB<JuntaCronogramaED, JuntaCronogramaED> {

  private static final long      serialVersionUID = 1L;

  @Inject
  JuntaCronogramaRN              cronogramaRN;

  @Inject
  MunicipioRN                    municipioRN;

  private List<SelectItem>       listaSituacaoCronograma;

  private List<MunicipioED>      listaMunicipiosCronograma;

  // VARIÁVEL USADA PARA CONTROLAR A ABERTURA DO DIALOG DE PESQUISA
  private int                    abreTelaPesquisa;

  private Boolean                desligaRedistribuicao;

  private CronogramaParametrosED parametrosRegenerarED;
  private CronogramaParametrosED parametrosRedistribuirProfsED;
  private CronogramaParametrosED parametrosRedistribuirProfsDiaMaxED;

  //primeira vez que monta a tela
  private boolean ehPrimeira = true;
  
  @PostConstruct
  void initRN() {
    // Limpa a pesquisa
    // limpa();
    super.setRN(cronogramaRN);
    parametrosRedistribuirProfsED = cronogramaRN.getParametros(ParametrosCronogramaJuntaEnum.DESLIGA_REDISTRIBUICAO.getDescricao());
    parametrosRedistribuirProfsDiaMaxED = cronogramaRN.getParametros(ParametrosCronogramaJuntaEnum.DIA_MAX_REDISTRIBUIR.getDescricao());
  }

  @Override
  public void init() {
    super.init();
    super.getPed().setUsaItensTela(true);
    //se for primeira vez que monta a tela deixar selecionada as situacoes 1-Disponivel e 2-Lotado
    if(ehPrimeira){
      super.getPed().setItensSituacaoSelecionados( new Integer[SituacaoCronogramaEnum.getAll().size()]);
      super.getPed().getItensSituacaoSelecionados()[0] = 1;
      super.getPed().getItensSituacaoSelecionados()[1] = 2;
      ehPrimeira=false;
    }
    parametrosRegenerarED = cronogramaRN.getParametros(ParametrosCronogramaJuntaEnum.REGERAR_CRONOGRAMA.getDescricao());
  }

  public void salvarParametros() {
    cronogramaRN.atualizaParametrosRedistribuicao(parametrosRedistribuirProfsED);
    cronogramaRN.atualizaParametrosRedistribuicao(parametrosRedistribuirProfsDiaMaxED);
    FacesUtil.addInfoMessage("Parâmetros atualizados com sucesso.");
  }

  public List<SituacaoCronogramaEnum> getListaSituacaoCronograma() {
    return SituacaoCronogramaEnum.getAll();
  }

  public List<MunicipioED> getListaMunicipiosCronograma() {

    // SERÁ REVISADO COM O CLIENTE
    //return cronogramaRN.getMunicipiosQueJaTiveramJunta();
    return municipioRN.buscaListaMunicipiosCadastrados();

  }

  @Override
  public void pesquisa() {
    super.pesquisa();
  }

  /**
   * Redirecionamento para página de Editar/Detalhes.
   * 
   * @param cronogramaED
   * @return
   */
  public String redirectCronogramaEditar(JuntaCronogramaED cronogramaED) {
    return HabWebUtil.JUNTA_CRONOGRAMA_FORM;
  }

  public int getAbreTelaPesquisa() {
    return abreTelaPesquisa;
  }

  public void setAbreTelaPesquisa(int abreTelaPesquisa) {
    this.abreTelaPesquisa = abreTelaPesquisa;
  }

  public CronogramaParametrosED getParametrosRegenerarED() {
    return parametrosRegenerarED;
  }

  public CronogramaParametrosED getParametrosRedistribuirProfsED() {
    return parametrosRedistribuirProfsED;
  }

  public CronogramaParametrosED getParametrosRedistribuirProfsDiaMaxED() {
    return parametrosRedistribuirProfsDiaMaxED;
  }

  public Boolean getDesligaRedistribuicao() {
    if (parametrosRedistribuirProfsED != null)
      if (parametrosRedistribuirProfsED.getValorNum().equals(1)) // 0 = LIGADO
        desligaRedistribuicao = true;
      else
        desligaRedistribuicao = false;

    return desligaRedistribuicao;
  }

  public void setDesligaRedistribuicao(Boolean desligaRedistribuicao) {
    this.desligaRedistribuicao = desligaRedistribuicao;

    if (desligaRedistribuicao)
      parametrosRedistribuirProfsED.setValorNum(1);
    else
      parametrosRedistribuirProfsED.setValorNum(0);

  }

  public void ativaFlagRegerarCronograma() {
    if (cronogramaRN.ativaFlagRegerarCronograma())
      FacesUtil.addInfoMessage("Atenção! O cronograma será regerado nas próximas 24 horas.");

  }


  @Override
  public void limpa(){    
    super.getPed().setUsaItensTela(true);
    super.getPed().setItensSituacaoSelecionados( new Integer[SituacaoCronogramaEnum.getAll().size()]);   
    super.getPed().getItensSituacaoSelecionados()[0] = 1;
    super.getPed().getItensSituacaoSelecionados()[1] = 2;
    super.limpa();    
  }  
 
    
}
