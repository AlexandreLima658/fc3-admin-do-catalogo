package com.fullcycle.admim.catalogo.infra.api.controllers;

import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryCommand;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admim.catalogo.domain.pagination.Pagination;
import com.fullcycle.admim.catalogo.domain.validation.handler.Notification;
import com.fullcycle.admim.catalogo.infra.api.CategoryAPI;
import com.fullcycle.admim.catalogo.infra.category.models.CategoryApiOutput;
import com.fullcycle.admim.catalogo.infra.category.models.CreateCategoryApiInput;
import com.fullcycle.admim.catalogo.infra.category.models.UpdateCategoryApiInput;
import com.fullcycle.admim.catalogo.infra.category.presenters.CategoryApiPresenters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;


@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
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
    public Pagination<?> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {

        final var output = new CategorySearchQuery(
                page,
                perPage,
                search,
                sort,
                direction
        );

        return this.listCategoriesUseCase.execute(output);
    }

    @Override
    public CategoryApiOutput getById(final String id) {
        return CategoryApiPresenters.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryApiInput input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError =
                ResponseEntity.unprocessableEntity()::body;

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError,onSuccess);
    }
}
