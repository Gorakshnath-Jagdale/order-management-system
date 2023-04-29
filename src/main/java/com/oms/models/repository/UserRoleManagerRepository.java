package com.oms.models.repository;

import com.oms.models.UserRoleEntity;
import com.oms.models.UserRoleManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRoleManagerRepository extends JpaRepository<UserRoleManagerEntity, Long> {
    boolean existsByUserDetailsEntity_IdAndUserDetailsEntity_ActiveUserAndUserRoleEntity_ActiveRoleAndEndDateGreaterThanEqualAndBeginDateLessThanEqualAllIgnoreCase(Long id, boolean activeUser, boolean activeRole, Date endDate, Date beginDate);


}
