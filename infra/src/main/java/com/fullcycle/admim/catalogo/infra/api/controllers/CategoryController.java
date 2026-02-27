package com.fullcycle.admim.catalogo.infra.api.controllers;

import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.pagination.Pagination;
import com.fullcycle.admim.catalogo.domain.validation.handler.Notification;
import com.fullcycle.admim.catalogo.infra.api.CategoryAPI;
import com.fullcycle.admim.catalogo.infra.category.models.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;


@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {

        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError =
                ResponseEntity.unprocessableEntity()::body;

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id().getValue())).build();

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError,onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
