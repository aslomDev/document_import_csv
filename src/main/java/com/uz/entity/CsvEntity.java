package com.uz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "csv")
public class CsvEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer _id;

    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "doc_date")
    private Date doc_date;


}
