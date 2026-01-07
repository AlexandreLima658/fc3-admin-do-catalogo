package com.fullcycle.admim.catalogo.domain.category;

import com.fullcycle.admim.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admim.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory(){

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());

        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());


    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final String expectedName = null;
        final var expectedDescription = "some description";
        final var expectedErrorMessage = "'name' should be not null";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException =  Assertions.assertThrows(
                DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));


        Assertions.assertEquals(expectedErrorMessage,actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,actualException.getErrors().size());

    }


}