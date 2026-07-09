package net.ent.entc.poeleague;

import net.ent.entc.poeleague.models.facade.*;
import net.ent.entc.poeleague.models.facade.exception.FacadeMetierException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PoeleagueApplication {

    public static void main(String[] args) throws FacadeMetierException {
        ApplicationContext context = SpringApplication.run(PoeleagueApplication.class, args);
        IFacade facadeMetier = context.getBean(IFacade.class);
        facadeMetier.initialisation();
    }
}