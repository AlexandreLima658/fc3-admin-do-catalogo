package com.fullcycle.admim.catalogo.application.category.retrieve.list;

import com.fullcycle.admim.catalogo.application.UseCase;
import com.fullcycle.admim.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admim.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
