package com.fullcycle.admim.catalogo.application.category.create;

import com.fullcycle.admim.catalogo.application.UseCase;
import com.fullcycle.admim.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification,  CreateCategoryOutput>> {
}
