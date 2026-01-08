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

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final var expectedName = " ";
        final var expectedDescription = "some description";
        final var expectedErrorMessage = "'name' should not be empty";
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

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final var expectedName = "Fi ";
        final var expectedDescription = "some description";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
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
    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final var expectedName = """
                "Sob essa perspectiva, a promoção de um ambiente de inovação contínua impõe uma reinterpretação dos vínculos sociais da governabilidade em ambientes voláteis. 
                À medida que avançamos, a constante divulgação das informações assume importantes posições no estabelecimento da lógica predominante nas decisões estratégicas.
                 A certificação de metodologias que nos auxiliam a lidar com a percepção das dificuldades promove a alavancagem das formas de ação. 
                 Tendo em vista as transformações em curso, o entendimento das metas propostas acarreta um processo de reformulação e modernização das práticas reconhecidas internacionalmente.";
                """;
        final var expectedDescription = "some description";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
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

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final var expectedName = "Movies";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

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
    public void givenFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError(){

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());

        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }


}