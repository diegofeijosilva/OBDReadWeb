package com.obdread.infra;

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

    em.persist(ed);
    em.flush();

    return ed;
  }

  @Override
  public ED altera(ED ed) {  
    ed = em.merge(ed);
    em.flush();
    return ed;
  }

}
