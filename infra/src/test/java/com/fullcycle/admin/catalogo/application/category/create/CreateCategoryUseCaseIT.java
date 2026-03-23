package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.IntegrationTest;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.infra.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = true;

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var aCommand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Assertions.assertEquals(1, categoryJpaRepository.count());

        final var actualCategory =
                categoryJpaRepository.findById(actualOutput.id().getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenInvalidName_whenCallsCreateCategory_thanShouldReturnDomainException(){

        final String expectedName = null;
        final var expectedDescription = "some description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should be not null";
        final var expectedErrorCount = 1;

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var aCommand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        Mockito.verify(categoryGateway, Mockito.times(0)).create(any());

    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = false;

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Assertions.assertEquals(1, categoryJpaRepository.count());

        final var actualCategory =
                categoryJpaRepository.findById(actualOutput.id().getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException(){

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.doThrow(new IllegalStateException(expectedErrorMessage))
                        .when(categoryGateway).create(any());

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());


    }
}
