package com.fullcycle.admim.catalogo.application.category.create;

import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admim.catalogo.domain.validation.handler.Notification;
import com.fullcycle.admim.catalogo.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }
    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand aCommand) {

        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aCategory = Category.newCategory(
                aName,
                aDescription,
                isActive
        );

        aCategory.validate(notification);

        return CreateCategoryOutput.from(categoryGateway.create(aCategory));
    }
}
