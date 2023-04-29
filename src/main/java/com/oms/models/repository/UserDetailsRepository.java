package com.oms.models.repository;

import com.oms.models.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {


    UserDetailsEntity findByLoginIdIgnoreCaseAndUserPass(String loginId, String userPass);

    @Query("select u.id from UserDetailsEntity u where u.supervisorId = ?1")
    List<Long> findBySupervisorId(String supervisorId);

    @Query("select u.id from UserDetailsEntity u where u.supervisorId in ?1 and u.activeUser = 'TRUE'")
    Set<Integer> findBySupervisorIdInAndActiveUser(Collection<Integer> supervisorIds);






}
