package com.fullcycle.admim.catalogo.domain.category;

import com.fullcycle.admim.catalogo.domain.validation.Error;
import com.fullcycle.admim.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admim.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(
            final Category aCategory,
            final ValidationHandler aHandler
    ) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        if(this.category.getName() == null){
            this.validationHandler().append(new Error("'name' should be not null"));
        }
    }
}
