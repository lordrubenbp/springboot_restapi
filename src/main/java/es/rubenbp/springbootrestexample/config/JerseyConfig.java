package es.rubenbp.springbootrestexample.config;

import es.rubenbp.springbootrestexample.controller.ModeloController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(ModeloController.class);


    }

}