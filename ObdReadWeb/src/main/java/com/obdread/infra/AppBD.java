package com.obdread.infra;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.obdread.infra.persistence.FrameworkBDImpl;

public class AppBD<ED extends AppED<PK>, PK> extends FrameworkBDImpl<ED, PK> {

  // DataSource do Oracle
  @PersistenceContext(unitName = "MysqlDS")
  EntityManager   em;


  @PostConstruct
  public void init() {
    super.setEntityManager(this.em);
  }

  @Override
  public ED inclui(ED ed) {
    
    ed.setCtrDthInc(Calendar.getInstance());
    ed.setCtrDthAtu(Calendar.getInstance());

    em.persist(ed);
    em.flush();

    return ed;
  }

  @Override
  public ED altera(ED ed) {  
    
    ed.setCtrDthAtu(Calendar.getInstance());
    ed = em.merge(ed);
    em.flush();
    return ed;
  }

}
