package demo.security.oauth2.client.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Oauth2ClientConfig {

    // == Oauth2 Configuration ==
    @Bean
    ReactiveClientRegistrationRepository clientRegistration() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("my-datafabri")
                .tokenUri("http://localhost:8080/oauth/token")
//                .clientId("client")
 //               .clientSecret("secret")
                .clientId("client-service")
                .clientSecret("secret")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //.clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                .scope("all")
                .build();
        
        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    @Bean
    ReactiveOAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistration());
    }

    // == WebFlux Configuration ==
    
    @Bean(name = "my-datafabri")
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations, 
    					ReactiveOAuth2AuthorizedClientService authorizedClientService) {
   
    	ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = 
        		new ServerOAuth2AuthorizedClientExchangeFilterFunction(
        				new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, 
        																authorizedClientService));
        oauth.setDefaultClientRegistrationId("my-datafabri");
        return WebClient.builder()
                .filter(oauth)
                .build();
    }
    

}
