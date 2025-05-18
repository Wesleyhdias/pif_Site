package com.pifsite.application.entities;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
@Getter
@Setter
@Table(name = "professors")
public class Professor{

    @Id
    private UUID professorId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "professor_id")
    private User user;

}
