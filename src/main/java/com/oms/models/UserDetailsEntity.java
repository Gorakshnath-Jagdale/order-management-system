package com.oms.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(schema = "OMS_ADVANCE",name = "USER_DETAILS")
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", nullable = false)
    private Long id;
    @Column(name = "USER_NAME", nullable = false)
private String userName;
    @Column(name = "LOGIN_ID", nullable = false)
private String loginId;
    @Column(name = "CONTACT_NUMBER", nullable = false)
private String contactNumber;
    @Column(name = "USER_PASS", nullable = false)
private String userPass;
    @Column(name = "IS_ACTIVE_USER", nullable = false)
private boolean activeUser;
    @Column(name = "USER_LEVEL", nullable = false)
private int userLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDetailsEntity that = (UserDetailsEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
