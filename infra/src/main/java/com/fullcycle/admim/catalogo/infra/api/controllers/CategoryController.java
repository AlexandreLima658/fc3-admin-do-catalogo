package com.fullcycle.admim.catalogo.infra.api.controllers;

import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.pagination.Pagination;
import com.fullcycle.admim.catalogo.infra.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
