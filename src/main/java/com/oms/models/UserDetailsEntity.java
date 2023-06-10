package com.oms.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(schema = "OMS_ADVANCE", name = "USER_DETAILS")
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long id;
    @Column(name = "USER_FIRST_NAME", nullable = false)
    private String userFirstName;
    @Column(name = "USER_LAST_NAME", nullable = false)
    private String userLastName;
    @Column(name = "SUPERVISOR_USER_ID", nullable = false)
    private Integer supervisorId;
    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;
    @Column(name = "CONTACT_NUMBER", nullable = false)
    private String contactNumber;
    @Column(name = "USER_PASS", nullable = false)
    private String userPass;
    @Column(name = "IS_ACTIVE_USER", nullable = false)
    private boolean activeUser;

    @OneToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private UserRoleEntity userRoleEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_DATE", nullable = false)
    private Date beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "CREATED_BY_USER_ID")
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFIED_BY_USER_ID")
    private Integer modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;


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
