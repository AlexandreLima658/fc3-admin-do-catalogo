package com.fullcycle.admin.catalogo.infra.category.presenters;

import com.fullcycle.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.fullcycle.admin.catalogo.infra.category.models.CategoryApiOutput;

public interface CategoryApiPresenters {

    static CategoryApiOutput present(final CategoryOutput output){
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
