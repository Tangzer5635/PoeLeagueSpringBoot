package net.ent.entc.poeleague.models.entities.communs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.NoArgsConstructor;
import net.ent.entc.poeleague.models.entities.communs.exceptions.ValidException;

import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidUtils {

    public static <T> void validate(T objet) throws ValidException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> erreurs = validator.validate(objet);
        if (!erreurs.isEmpty()) {
            throw new ValidException(erreurs);
        }
    }
}
