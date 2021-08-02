package com.innovat.quietanzeservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.innovat.quietanzeservice.dto.MessageResponse;
import com.innovat.quietanzeservice.dto.RicezioneQuietanzaRequest;
import com.innovat.quietanzeservice.exception.BindingException;
import com.innovat.quietanzeservice.exception.DuplicateException;
import com.innovat.quietanzeservice.exception.NotFoundException;
import com.innovat.quietanzeservice.model.Quietanza;
import com.innovat.quietanzeservice.service.QuietanzaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;

@RestController
@RequestMapping(value = "${quietanze.uri}")
@Api(value="service", tags="Controller operazioni di gestione quietanze")
@Log
public class QuietanzeController {
	
	@Autowired
	private ResourceBundleMessageSource msg;
	
	@Autowired
    private QuietanzaService service;

	
	@ApiOperation(
		      value = "Ricezione quietanze", 
		      notes = "Salvataggio di una quietanza a seguito di un pagamento F24",
		      response = MessageResponse.class, 
		      produces = "application/json")
	@ApiResponses(value =
	{   @ApiResponse(code = 201, message = "La quietanza è stata salvata con successo"),
	    @ApiResponse(code = 400, message = "I dati della richiesta non sono corretti"),
	    @ApiResponse(code = 406, message = "Questa quietanza è già presente")
	})
	@RequestMapping(value = "${quietanze.save}", method = RequestMethod.POST)
	public ResponseEntity<?> ricezioneQuietanza(@ApiParam("Dati richiesta salvataggio quietanza") @Valid @RequestBody RicezioneQuietanzaRequest requestBody,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws  BindingException, DuplicateException {
		log.info("===========================Start api/quietanza-service/save=="+requestBody.toString()+"=============================");
		
		MessageResponse res = new MessageResponse();
		//Input validation
		if(bindingResult.hasErrors()) {
			String error = msg.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());			
			log.warning(error);
			throw new BindingException(error);
		}
		log.info("-----------dati input corretti-------------");
		log.info("-----------start salvataggio quietanza-------------");
		
		if(service.loadQuietanzaByIdDelega(requestBody.getCodiceIdentificativoDelegaF24ZZ())!=null) {
  		String errMsg = msg.getMessage("exc.duplicate.Quietanza", null, LocaleContextHolder.getLocale());
  		log.warning(errMsg);
  		throw new DuplicateException(errMsg);	    	 
	    }
		
		service.save(requestBody); 
		
		res.setCod(HttpStatus.CREATED.value());
		res.setMsg(msg.getMessage("success.ricezioneQuietanza", null, LocaleContextHolder.getLocale()));
  	
		log.info("===========================End api/quietanza-service/save=============");
		return ResponseEntity.ok(res);
	}
	
	
	
	@ApiOperation(
		      value = "Get quietanza", 
		      notes = "Restituisce la quietanza a seguito del pagamento f24",
		      response = Quietanza.class, 
		      produces = "application/json")
	@ApiResponses(value =
	{   @ApiResponse(code = 200, message = "La quietanza è stato trovato"),
	    @ApiResponse(code = 404, message = "Nessuna quietanza trovata")
	})
	@RequestMapping(value = "${quietanze.get}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@ApiParam("Id delega") @PathVariable (value="idDelega") String idDelega) throws NotFoundException {
		log.info("===========================Start quietanza-service/ get/=="+idDelega+"=============================");
		Quietanza quiet = service.loadQuietanzaByIdDelega(idDelega);   
		
		if(quiet==null) {
			String errMsg = msg.getMessage("exc.notFound", null, LocaleContextHolder.getLocale());
			log.warning(errMsg);
			throw new NotFoundException(errMsg);
		}
		
		return ResponseEntity.ok(quiet);
	}
}
