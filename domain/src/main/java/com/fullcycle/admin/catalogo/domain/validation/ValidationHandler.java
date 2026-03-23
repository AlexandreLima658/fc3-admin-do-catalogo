package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(final Error anError);
    ValidationHandler append(final ValidationHandler anHandler);
    ValidationHandler validate(final Validation anValidation);

    List<Error> getErrors();

    default boolean hasError(){
        return getErrors() != null && !(getErrors().isEmpty());
    }
    public interface Validation {
        void validate();
    }

}
