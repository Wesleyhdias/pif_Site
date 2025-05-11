package com.pifsite.application.entities;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@Table(name = "professors")
@PrimaryKeyJoinColumn(name = "professor_id")
public class Professor extends User {

}
