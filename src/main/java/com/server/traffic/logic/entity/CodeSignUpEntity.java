package com.server.traffic.logic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CodeSignUpEntity
 *
 * @author DatDV
 */
@Entity
@Table(name = "code_signup")
public class CodeSignUpEntity extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
