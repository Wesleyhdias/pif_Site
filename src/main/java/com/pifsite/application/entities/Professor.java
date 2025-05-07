package com.pifsite.application.entities;

import jakarta.persistence.PrimaryKeyJoinColumn;


import jakarta.persistence.Entity;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends User {


}
