package com.fullcycle.admim.catalogo.domain.exceptions;

import java.util.List;

import com.fullcycle.admim.catalogo.domain.validation.Error;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final String aMessage,List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final Error anErrors) {
        return new DomainException("",List.of(anErrors));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("",anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
