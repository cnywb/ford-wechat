package com.ford.wechat.batch.item.reader.common;


import com.ford.wechat.batch.model.DataFileStatistic;
import org.hibernate.internal.QueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wanglijun on 16/11/3.
 */
public class DataFileStatisticItemReader<T>  extends AbstractPagingItemReader<T> {

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private final Map<String, Object> jpaPropertyMap = new HashMap ();

    private String queryString;

    private JpaQueryProvider queryProvider;

    private Map<String, Object> parameterValues;

    private boolean transacted = true;






    public DataFileStatisticItemReader() {
        this.setName(ClassUtils.getShortName(DataFileStatisticItemReader.class));
    }

    private Query createQuery() {
        return this.queryProvider == null?this.entityManager.createQuery(this.queryString):this.queryProvider.createQuery();
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if(this.queryProvider == null) {
            Assert.notNull(this.entityManagerFactory);
            Assert.hasLength(this.queryString);
        } else {
            Assert.isTrue(this.queryProvider != null, "JPA query provider must be set");
        }

    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setQueryProvider(JpaQueryProvider queryProvider) {
        this.queryProvider = queryProvider;
    }

    protected void doOpen() throws Exception {
        super.doOpen();
        this.entityManager = this.entityManagerFactory.createEntityManager(this.jpaPropertyMap);
        if(this.entityManager == null) {
            throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
        } else {
            if(this.queryProvider != null) {
                this.queryProvider.setEntityManager(this.entityManager);
            }

        }
    }

    protected void doReadPage() {
        EntityTransaction tx = null;
        if(this.transacted) {
            tx = this.entityManager.getTransaction();
            tx.begin();
            this.entityManager.flush();
            this.entityManager.clear();
        }

        Query query = this.createQuery().setFirstResult(this.getPage() * this.getPageSize()).setMaxResults(this.getPageSize());
        if(this.parameterValues != null) {
            Iterator queryResult = this.parameterValues.entrySet().iterator();

            while(queryResult.hasNext()) {
                Map.Entry i$ = (Map.Entry)queryResult.next();
                query.setParameter((String)i$.getKey(), i$.getValue());
            }
        }

        if(this.results == null) {
            this.results = new CopyOnWriteArrayList ();
        } else {
            this.results.clear();
        }

        if(!this.transacted) {

            ResultTransformer transformer= Transformers.aliasToBean (DataFileStatistic.class);

            List queryResult1 = ((QueryImpl)query.unwrap(QueryImpl.class)).setResultTransformer(transformer).list();

            if(!CollectionUtils.isEmpty (queryResult1)){
                this.results.addAll (queryResult1);
            }
        } else {
            ResultTransformer transformerClass= Transformers.aliasToBean (DataFileStatistic.class);
            List queryResult1 =((QueryImpl)query.unwrap(QueryImpl.class)).setResultTransformer(transformerClass).list();
            if(!CollectionUtils.isEmpty (queryResult1)){
                this.results.addAll (queryResult1);
            }
            tx.commit();
        }

    }

    protected void doJumpToPage(int itemIndex) {
    }

    protected void doClose() throws Exception {
        this.entityManager.close();
        super.doClose();
    }
}
