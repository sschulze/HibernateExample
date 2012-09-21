package de.ckc.hibernate.bugtest.hhhxxxx;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ckc.hibernate.bugtest.hhhxxxx.entities.BarNotWorking;
import de.ckc.hibernate.bugtest.hhhxxxx.entities.BarWorking;
import de.ckc.hibernate.bugtest.hhhxxxx.entities.FooNotWorking;
import de.ckc.hibernate.bugtest.hhhxxxx.entities.FooWorking;

public class HHHxxxx {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setup(){
        emf = Persistence.createEntityManagerFactory("sample");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        FooWorking foo=new FooWorking();
        foo.setaId1(5);
        foo.setbId3("foo3");
        foo.setcId2(new Date());
        em.persist(foo);
        
        BarWorking bar1=new BarWorking();
        bar1.setId(1);
        bar1.setFoo(foo);
        em.persist(bar1);
        
        BarWorking bar2=new BarWorking();
        bar2.setId(2);
        bar2.setFoo(foo);
        em.persist(bar2);
        tx.commit();
        em.close();
        
        this.em=emf.createEntityManager();
    }
    
    @After
    public void tearDown(){
        em.close();
        emf.close();
    }
    
    @Test
    public void testInsertBarNotWorking(){
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        TypedQuery<FooNotWorking> query=em.createQuery("select f from FooNotWorking f", FooNotWorking.class);
        FooNotWorking foo = query.getSingleResult();
        Assert.assertNotNull(foo);
        
        BarNotWorking bar=new BarNotWorking();
        bar.setId(1);
        bar.setFoo(foo);
        em.persist(bar);
        em.flush();
        tx.rollback();
    }

//During "drop table BAR if exists" HSQLDB seems to get into a deadlock for any reason.
//    @Test
//    public void testInsertBarWorking(){
//        EntityTransaction tx=em.getTransaction();
//        tx.begin();
//        TypedQuery<FooWorking> query=em.createQuery("select f from FooWorking f", FooWorking.class);
//        FooWorking foo = query.getSingleResult();
//        Assert.assertNotNull(foo);
//        
//        BarWorking bar=new BarWorking();
//        bar.setId(1);
//        bar.setFoo(foo);
//        em.persist(bar);
//        em.flush();
//        tx.rollback();
//    }
    
    @Test
    public void testSelectFooWorking(){
        TypedQuery<FooWorking> query=em.createQuery("select f from FooWorking f", FooWorking.class);
        FooWorking foo = query.getSingleResult();
        Assert.assertNotNull(foo);
        Assert.assertEquals(foo.getbId3(), "foo3");
    }
    
    @Test
    public void testSelectBarWorking(){
        TypedQuery<BarWorking> query=em.createQuery("select b from BarWorking b", BarWorking.class);
        List<BarWorking> barList = query.getResultList();
        Assert.assertEquals(2,barList.size());
        Assert.assertNotNull(barList);
        BarWorking bar=barList.get(0);
        Assert.assertNotNull(bar.getFoo());
        Assert.assertEquals(bar.getFoo().getbId3(), "foo3");
    }
    
    @Test
    public void testSelectBarByFooWorking(){
        TypedQuery<FooWorking> fooQuery = em.createQuery("select f from FooWorking f",FooWorking.class);
        FooWorking foo = fooQuery.getSingleResult();
        
        TypedQuery<BarWorking> barQuery = em.createQuery("select b from BarWorking b where foo=:foo",BarWorking.class);
        barQuery.setParameter("foo", foo);
        List<BarWorking> barList = barQuery.getResultList();
        
        Assert.assertEquals(2,barList.size());
        Assert.assertNotNull(barList);
        BarWorking bar=barList.get(0);
        Assert.assertNotNull(bar.getFoo());
        Assert.assertEquals(bar.getFoo().getbId3(), "foo3");
    }
    
    @Test
    public void testSelectFooNotWorking(){
        TypedQuery<FooNotWorking> query=em.createQuery("select f from FooNotWorking f", FooNotWorking.class);
        FooNotWorking foo = query.getSingleResult();
        Assert.assertNotNull(foo);
        Assert.assertEquals(foo.getbId3(), "foo3");
    }
    
    @Test
    public void testSelectBarNotWorking(){
        TypedQuery<BarNotWorking> query=em.createQuery("select b from BarNotWorking b", BarNotWorking.class);
        List<BarNotWorking> barList = query.getResultList();
        Assert.assertEquals(2,barList.size());
        Assert.assertNotNull(barList);
        BarNotWorking bar=barList.get(0);
//        Assert.assertEquals(bar.getbId3(), "foo3");
        Assert.assertNotNull(bar.getFoo());
        Assert.assertEquals(bar.getFoo().getbId3(), "foo3");
    }
    
    @Test
    public void testSelectBarByFooNotWorking(){
        TypedQuery<FooNotWorking> fooQuery = em.createQuery("select f from FooNotWorking f",FooNotWorking.class);
        FooNotWorking foo = fooQuery.getSingleResult();
        
        TypedQuery<BarNotWorking> barQuery = em.createQuery("select b from BarNotWorking b where foo=:foo",BarNotWorking.class);
        barQuery.setParameter("foo", foo);
        List<BarNotWorking> barList = barQuery.getResultList();
        
        Assert.assertEquals(2,barList.size());
        Assert.assertNotNull(barList);
        BarNotWorking bar=barList.get(0);
//        Assert.assertEquals(bar.getbId3(), "foo3");
        Assert.assertNotNull(bar.getFoo());
        Assert.assertEquals(bar.getFoo().getbId3(), "foo3");
    }
}
