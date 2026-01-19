package com.fullcycle.admim.catalogo.application.category.update;

import com.fullcycle.admim.catalogo.application.UseCase;
import com.fullcycle.admim.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
