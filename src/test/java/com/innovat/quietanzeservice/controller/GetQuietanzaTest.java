package com.innovat.quietanzeservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
public class GetQuietanzaTest
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
				.andDo(print());
		
		mockMvc.perform(MockMvcRequestBuilders.get(ApiBaseUrl + "/get/idDelegaTest")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idDelega").value("idDelegaTest"))
				.andExpect(jsonPath("$.codContratto").value("codicamentoOnline"))
				.andDo(print());
		
		repo.delete(repo.findByIdDelega("idDelegaTest"));
	}
	
	@Test
	@Order(2)
	public void testNotFoundExc() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get(ApiBaseUrl + "/get/idDelegaTest")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.cod").value(404))
				.andExpect(jsonPath("$.msg").value("La quietanza non è stata trovata"))
				.andDo(print());
	}
	
		
	
}
