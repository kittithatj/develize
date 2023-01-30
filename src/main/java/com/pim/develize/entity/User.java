package com.pim.develize.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long user_id;

    @Column(name="USERNAME", length=20, nullable=false, unique=true)
    private String username;

    @Column(name="PASSWORD", nullable=false)
    private String password;

    @Column(name="ROLE", nullable=false)
    private String role;
}
