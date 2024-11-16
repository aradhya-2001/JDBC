package com.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // tells the following class will be used as table in db
@Table(name = "jpaTable") // explicitly defined the table name instead of the default class name
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JpaPerson
{

    @Id // coz JpaRepository needs primary key i.e. id of the table
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator( name = "user_seq_gen",
            sequenceName = "custom_seq_gen_table",
            initialValue = 4,
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "jpa_person_name", length = 20, nullable = false) // constraints on name column in db table
    private String name;

    private int serial_num;

    @Transient // not making a column named age into the table
    private int age;

    public JpaPerson(String name, int serial_num, int age) {
        this.serial_num = serial_num;
        this.name = name;
        this.age = age;
    }
}
