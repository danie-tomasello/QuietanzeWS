package com.innovat.quietanzeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innovat.quietanzeservice.model.Quietanza;

@Repository
public interface QuietanzaRepository extends JpaRepository<Quietanza, Long> {

	public Quietanza findByIdDelega(String codiceIdentificativoDelegaF24ZZ);
}


