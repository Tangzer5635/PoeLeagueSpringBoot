package net.ent.entc.poeleague.models.facade.exception;

public class FacadeMetierException extends Exception {
    public FacadeMetierException(String message) {
        super(message);
    }

    public FacadeMetierException(String message, Throwable cause) {
        super(message, cause);
    }
}
