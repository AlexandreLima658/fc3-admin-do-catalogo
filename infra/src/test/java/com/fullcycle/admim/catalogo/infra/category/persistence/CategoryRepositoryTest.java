package com.fullcycle.admim.catalogo.infra.category.persistence;


import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.infra.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenAValidNullName_whenCallsSave_shouldReturnError () {

        final var expectedMessage = "not-null property references a null or transient value: com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaEntity.name";
        final var expectedPropertyName = "name";

        final var aCategory = Category.newCategory("Movies", "Some description", true);

        final var anEntity = CategoryJpaMapper.toJpaEntity(aCategory);
        anEntity.setName(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAValidNullCreatedAt_whenCallsSave_shouldReturnError () {

        final var expectedMessage = "not-null property references a null or transient value: com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaEntity.createdAt";
        final var expectedPropertyName = "createdAt";

        final var aCategory = Category.newCategory("Movies", "Some description", true);

        final var anEntity = CategoryJpaMapper.toJpaEntity(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAValidNullUpdatedAt_whenCallsSave_shouldReturnError () {

        final var expectedMessage = "not-null property references a null or transient value: com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaEntity.updatedAt";
        final var expectedPropertyName = "updatedAt";

        final var aCategory = Category.newCategory("Movies", "Some description", true);

        final var anEntity = CategoryJpaMapper.toJpaEntity(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }
}
