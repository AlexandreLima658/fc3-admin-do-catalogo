package com.fullcycle.admim.catalogo.application.category.update;

import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {

    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
