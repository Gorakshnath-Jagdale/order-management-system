package com.oms.models.repository;

import com.oms.models.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {
    UserDetailsEntity findByUserNameIgnoreCaseAndUserPassIgnoreCase(String userName, String userPass);

    UserDetailsEntity findByLoginIdIgnoreCaseAndUserPassIgnoreCase(String loginId, String userPass);




}
