package de.ckc.hibernate.bugtest.hhhxxxx.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BAR")
public class BarNotWorking {
    
    @Id
    @Column(name="ID")
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="A_ID_1"), 
            @JoinColumn(name="C_ID_2"),
            @JoinColumn(name="B_ID_3") 
    })
    private FooNotWorking foo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FooNotWorking getFoo() {
        return foo;
    }

    public void setFoo(FooNotWorking foo) {
        this.foo = foo;
    }    
}
