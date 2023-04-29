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
@Table(schema = "OMS_ADVANCE", name = "USER_ROLE_MANAGER")
public class UserRoleManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false )
    private Long userId;

    @OneToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "USER_ID", nullable = false )
    private UserDetailsEntity userDetailsEntity;


@OneToOne
    @JoinColumn(name = "ROLE_ID", nullable = false )
    private UserRoleEntity userRoleEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_DATE", nullable = false)
    private Date beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;


    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRoleManagerEntity that = (UserRoleManagerEntity) o;
        return  userDetailsEntity.getId()!= null && Objects.equals(userDetailsEntity.getId(), that.userDetailsEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
