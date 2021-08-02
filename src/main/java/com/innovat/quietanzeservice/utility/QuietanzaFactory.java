package com.innovat.quietanzeservice.utility;

import com.innovat.quietanzeservice.dto.RicezioneQuietanzaRequest;
import com.innovat.quietanzeservice.model.Quietanza;

public class QuietanzaFactory {
	
public static Quietanza createQuietanza(RicezioneQuietanzaRequest request) {
		
	Quietanza quietanza = new Quietanza(
			request.getCodiceIdentificativoDelegaF24ZZ(),
			request.getCodiceContrattoPagamentoOnline(),
			request.getQuietanza()
			);	
	    
	    return quietanza;
	 
	}

}
