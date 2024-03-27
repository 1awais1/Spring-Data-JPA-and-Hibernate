package com.mycode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
