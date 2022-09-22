package com.oms.models.repository;

import com.oms.models.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository  extends JpaRepository <CustomerDetailsEntity,Long>
{

}
