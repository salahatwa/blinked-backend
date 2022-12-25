package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Certification;

public interface CertificateRepository extends JpaRepository<Certification, Long>{

}
