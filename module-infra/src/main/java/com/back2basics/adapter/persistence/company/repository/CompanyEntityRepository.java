package com.back2basics.adapter.persistence.company.repository;

import com.back2basics.adapter.persistence.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

}