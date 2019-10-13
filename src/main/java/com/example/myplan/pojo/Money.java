package com.example.myplan.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "s_money")
public class Money {

    final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "imoney")
    private double imoney ;
    @Column(name = "itype")
    private Integer itype ;
    @Column(name = "itime")
    private Date itime;


}
