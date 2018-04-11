package org.nix.dao.base;

import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 *
 * TODO: 获取hibernate的session对象以扩展jpa不能完成的功能
 */
public class HibernateSession {

    @PersistenceContext
    protected EntityManager entityManager;

    public Session getSession(){

        HibernateEntityManager manager = (HibernateEntityManager) entityManager;

        return manager.getSession();
    }

}
