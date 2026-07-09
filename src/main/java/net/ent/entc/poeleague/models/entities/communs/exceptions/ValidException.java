package net.ent.entc.poeleague.models.entities.communs.exceptions;

import jakarta.validation.ConstraintViolation;

import java.util.*;

public class ValidException extends Exception {

    Map<String, List<String>> recuperationContrainte = new HashMap<>();

    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidException(Set<? extends ConstraintViolation<?>> contrainteViolee) {

        for (ConstraintViolation<?> violation : contrainteViolee) {
            String leMsg = violation.getMessage();
            String attribut = violation.getPropertyPath().toString();
            recuperationContrainte.computeIfAbsent(attribut, k -> new ArrayList<>()).add(leMsg);
        }
    }

    public String affichageConstraintViolation(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : recuperationContrainte.entrySet()) {
            sb.append(String.format("%s :", entry.getKey())).append(System.lineSeparator());
            for (String msg : entry.getValue()) {
                sb.append(String.format(" - %s", msg)).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public void testAffichage(Map<String, List<String>> recuperationContrainte){
        for (Map.Entry<String, List<String>> entry : recuperationContrainte.entrySet()) {
            System.out.println(entry.getKey() + " : ");
            for (String msg : entry.getValue()) {
                System.out.println(" - " + msg);
            }
        }
    }
}
