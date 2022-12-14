package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.Certification;

public interface CertificateRepository extends JpaRepository<Certification, Long>{

}
