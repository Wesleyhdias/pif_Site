package com.pifsite.application.entities;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Entity
@Table(name = "professors")
@PrimaryKeyJoinColumn(name = "professor_id")
public class Professor extends User {

}
