package com.fullcycle.admim.catalogo.domain.validation;

public abstract class Validator {

    private ValidationHandler handler;

    protected Validator(ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }

}
