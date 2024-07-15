package com.restapis.expensetracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Dev server");

        Contact myContact = new Contact();
        myContact.setName("Arpit Kumar");
        myContact.setEmail("arpitkumar4000@gmail.com");

        Info information = new Info()
                .title("Expense Tracker REST APIs")
                .version("1.0")
                .description("This APIs exposes endpoints for managing expenses.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
