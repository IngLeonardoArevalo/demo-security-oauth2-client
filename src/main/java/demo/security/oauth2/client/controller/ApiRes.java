package demo.security.oauth2.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api")
public class ApiRes {

	@Autowired
	@Qualifier("my-datafabri")
	public WebClient webClient;

	@RequestMapping(value = "/sdk", method = RequestMethod.GET) 
	public Mono<String> Prueba() {
		return webClient.get()
			    .uri("http://localhost:8080/api/privado").retrieve()
			    .bodyToMono(String.class);
}
	
	
	
}
