package com.innovat.quietanzeservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.innovat.quietanzeservice.QuietanzeServiceApplication;
import com.innovat.quietanzeservice.model.Quietanza;
import com.innovat.quietanzeservice.repository.QuietanzaRepository;

@SpringBootTest()
@ContextConfiguration(classes = QuietanzeServiceApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class RicezioneQuietanzaTest
{
	 
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private QuietanzaRepository repo;
	
	@BeforeEach
	public void setup() throws JSONException, IOException
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();	
	}
	
	private String ApiBaseUrl = "/api/quietanze-service";
	
	String JsonData =  
			"{" + 
			"    \"codiceIdentificativoDelegaF24ZZ\":\"idDelegaTest\"," +
			"    \"codiceContrattoPagamentoOnline\":\"codicamentoOnline\"," + 
			"    \"quietanza\":[1,12,5]" + 
			"}";
	

	
	@Test
	@Order(1)
	public void testSuccess() throws Exception
	{

		mockMvc.perform(MockMvcRequestBuilders.post(ApiBaseUrl + "/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cod").value(201))
				.andExpect(jsonPath("$.msg").value("Ricezione quietanza eseguita con successo"))

				.andDo(print());

		assertThat(repo.findByIdDelega("idDelegaTest"))
			.extracting(Quietanza::getIdDelega)
			.isEqualTo("idDelegaTest");
		
		
	}
	
	@Test
	@Order(2)
	public void testDuplicateExc() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post(ApiBaseUrl + "/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.cod").value(406))
				.andExpect(jsonPath("$.msg").value("Quietanza già esistente! Impossibile utilizzare il metodo POST"))
				.andDo(print());
	}
	
	String ErrJsonData =  
			"{" + 
			"    \"codiceIdentificativoDelegaF24ZZ\":\"\"," + //idDelega vuoto
			"    \"codiceContrattoPagamentoOnline\":\"codicamentoOnline\"," + 
			"    \"quietanza\":[1,12,5]" + 
			"}";
	
	@Test
	@Order(3)
	public void testBindingExc() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post(ApiBaseUrl + "/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ErrJsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.cod").value(400))
				.andExpect(jsonPath("$.msg").value("Il campo codiceIdentificativoDelegaF24ZZ deve essere composto da 50 caratteri"))
				.andDo(print());
		
		repo.delete(repo.findByIdDelega("idDelegaTest"));
	}
	
	
	
	
}
