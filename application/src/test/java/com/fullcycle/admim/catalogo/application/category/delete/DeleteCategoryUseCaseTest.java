package com.fullcycle.admim.catalogo.application.category.delete;


import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK(){

        final var aCategory =
                Category.newCategory("Movies", "some description", true);

        final var expectedId = aCategory.getId();

        doNothing()
                .when(categoryGateway).deleteById(Mockito.eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);

    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK(){

        final var expectedId = CategoryID.from("123");

        doNothing()
                .when(categoryGateway).deleteById(Mockito.eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException(){

        final var aCategory =
                Category.newCategory("Movies", "some description", true);

        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(Mockito.eq(expectedId));

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);
    }
}
