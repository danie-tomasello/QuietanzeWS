package com.innovat.quietanzeservice.service;


import com.innovat.quietanzeservice.dto.RicezioneQuietanzaRequest;
import com.innovat.quietanzeservice.model.Quietanza;

public interface QuietanzaService {

	
	public Quietanza loadQuietanzaByIdDelega(String idDelega);

	public void save(RicezioneQuietanzaRequest requestBody);
}
