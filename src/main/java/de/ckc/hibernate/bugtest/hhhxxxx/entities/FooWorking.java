package de.ckc.hibernate.bugtest.hhhxxxx.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import de.ckc.hibernate.bugtest.hhhxxxx.entities.FooWorking.FooId;

@Entity
@IdClass(FooId.class)
@Table(name="FOO")
public class FooWorking {

    static class FooId implements Serializable{
        private Integer aId1;
        private Date bId2;
        private String cId3;
    }
    
    @Id
    @Column(name="A_ID_1")
    private Integer aId1;
    
    @Id
    @Column(name="C_ID_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bId2;
    
    @Id
    @Column(name="B_ID_3")
    private String cId3;

    public Integer getaId1() {
        return aId1;
    }

    public void setaId1(Integer aId1) {
        this.aId1 = aId1;
    }

    public Date getcId2() {
        return bId2;
    }

    public void setcId2(Date cId2) {
        this.bId2 = cId2;
    }

    public String getbId3() {
        return cId3;
    }

    public void setbId3(String bId3) {
        this.cId3 = bId3;
    }
    
    
}
