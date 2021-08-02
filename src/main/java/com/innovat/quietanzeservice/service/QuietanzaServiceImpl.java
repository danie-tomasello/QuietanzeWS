package com.innovat.quietanzeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.innovat.quietanzeservice.dto.RicezioneQuietanzaRequest;
import com.innovat.quietanzeservice.model.Quietanza;
import com.innovat.quietanzeservice.repository.QuietanzaRepository;
import com.innovat.quietanzeservice.utility.QuietanzaFactory;

import lombok.extern.java.Log;
	

@Log
@Service
public class QuietanzaServiceImpl implements QuietanzaService {
	
	@Autowired
	private QuietanzaRepository repo;

	@Override
	@Cacheable(value="quietanza", key ="#idDelega")
	public Quietanza loadQuietanzaByIdDelega(String idDelega) {
		log.info("---------loadQuietanzaByIdDelega richiesta non cachata---------");
		return repo.findByIdDelega(idDelega);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "quietanze", allEntries = true),
			@CacheEvict(cacheNames = "quietanza", allEntries = true)
	})
	public void save(RicezioneQuietanzaRequest requestBody) {
		log.info("---------start save---------");
		
		repo.save(QuietanzaFactory.createQuietanza(requestBody));
	}

}
