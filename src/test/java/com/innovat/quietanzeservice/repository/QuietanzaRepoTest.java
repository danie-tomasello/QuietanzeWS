package com.innovat.quietanzeservice.repository;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.innovat.quietanzeservice.QuietanzeServiceApplication;
import com.innovat.quietanzeservice.model.Quietanza;

 
@SpringBootTest()
@ContextConfiguration(classes = QuietanzeServiceApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class QuietanzaRepoTest
{
	@Autowired
	private QuietanzaRepository quietanzaRepository;
	
	
	
	@Test
	@Order(1)
	public void TestInsQuietanza()
	{
		byte[] CDRIVES = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
		
		System.out.println(CDRIVES.toString());
		
		Quietanza quietanza = new Quietanza("identificativoDelega","codiceContrattoPagamentoOnline",CDRIVES);
		
		
		
		quietanzaRepository.save(quietanza);
		
		assertThat(quietanzaRepository.findByIdDelega(quietanza.getIdDelega()))
		.extracting(Quietanza::getIdDelega)
		.isEqualTo("identificativoDelega");
	}
	
	
	
	@Test
	@Order(2)
	public void TestDelArticolo()
	{
		Quietanza quietanza = quietanzaRepository.findByIdDelega("identificativoDelega");
		
		quietanzaRepository.delete(quietanza);
		
	}
	
	public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
