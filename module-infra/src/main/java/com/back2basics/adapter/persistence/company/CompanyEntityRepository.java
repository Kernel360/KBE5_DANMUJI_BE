package com.back2basics.adapter.persistence.company;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByNameContaining(String keyword);
}