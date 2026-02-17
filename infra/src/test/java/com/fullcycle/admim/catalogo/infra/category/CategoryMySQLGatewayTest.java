package com.fullcycle.admim.catalogo.infra.category;


import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;
import com.fullcycle.admim.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admim.catalogo.MySQLGatewayTest;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaMapper;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {

        final var expectedName = "Movies";
        final var expectedDescription = "Some description";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var actualCategory = categoryMySQLGateway.create(aCategory);

        Assertions.assertEquals(1, categoryJpaRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualEntity = categoryJpaRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(aCategory.getDeletedAt());


    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnCategoryUpdated() {

        final var expectedName = "Movies";
        final var expectedDescription = "Some description";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Movies", null, expectedIsActive);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaMapper.toJpaEntity(aCategory));

        Assertions.assertEquals(1, categoryJpaRepository.count());

        final var aUpdatedCategory = aCategory.clone()
                .update(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = categoryMySQLGateway.update(aUpdatedCategory);

        Assertions.assertEquals(1, categoryJpaRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualEntity = categoryJpaRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(aCategory.getDeletedAt());


    }

    @Test
    public void givenAPrePersistenceCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {

        final var aCategory = Category.newCategory("Movies", null, true);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaMapper.toJpaEntity(aCategory));

        Assertions.assertEquals(1, categoryJpaRepository.count());

        categoryMySQLGateway.deleteById(aCategory.getId());

        Assertions.assertEquals(0, categoryJpaRepository.count());


    }

    @Test
    public void givenAInValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryMySQLGateway.deleteById(CategoryID.from("invalid"));

        Assertions.assertEquals(0, categoryJpaRepository.count());
    }

    @Test
    public void givenAPrePersistedCategoryAndValidCategoryId_whenCallsFindById_shouldReturnCategory() {

        final var expectedName = "Movies";
        final var expectedDescription = "Some description";
        final var expectedIsActive = true;

        final var aCategory = Category
                .newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaMapper.toJpaEntity(aCategory));

        Assertions.assertEquals(1, categoryJpaRepository.count());


        final var actualCategory = categoryMySQLGateway.findById(aCategory.getId()).get();

        Assertions.assertEquals(1, categoryJpaRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(aCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCategoryIdNotStored_whenCallsFindById_shouldReturnEmpty() {

        Assertions.assertEquals(0, categoryJpaRepository.count());
        final var actualCategory = categoryMySQLGateway.findById(CategoryID.from("invalid"));

        Assertions.assertTrue(actualCategory.isEmpty());

    }


    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated(){

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category
                .newCategory("Movies", null, true);

        final var series = Category
                .newCategory("Series", null, true);

        final var documentaries = Category
                .newCategory("Documentaries", null, true);


        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaMapper.toJpaEntity(movies),
                CategoryJpaMapper.toJpaEntity(series),
                CategoryJpaMapper.toJpaEntity(documentaries)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");

        final var actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentaries.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenEmptyCategoriesTables_whenCallsFindAll_shouldReturnEmptyPage(){

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");

        final var actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());

    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated(){

        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category
                .newCategory("Movies", null, true);

        final var series = Category
                .newCategory("Series", null, true);

        final var documentaries = Category
                .newCategory("Documentaries", null, true);


        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaMapper.toJpaEntity(movies),
                CategoryJpaMapper.toJpaEntity(series),
                CategoryJpaMapper.toJpaEntity(documentaries)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        var actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentaries.getId(), actualResult.items().get(0).getId());


        expectedPage = 1;
        query = new CategorySearchQuery(1, 1, "", "name", "asc");
        actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(movies.getId(), actualResult.items().get(0).getId());


        expectedPage = 2;
        query = new CategorySearchQuery(2, 1, "", "name", "asc");
        actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllAndTermsMatchCategoryName_shouldReturnPaginated(){

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var movies = Category
                .newCategory("Movies", null, true);

        final var series = Category
                .newCategory("Series", null, true);

        final var documentaries = Category
                .newCategory("Documentaries", null, true);


        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaMapper.toJpaEntity(movies),
                CategoryJpaMapper.toJpaEntity(series),
                CategoryJpaMapper.toJpaEntity(documentaries)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "doc", "name", "asc");

        final var actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentaries.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenPrePersistedCategoriesAndSomeDescriptionAsTerms_whenCallsFindAllAndTermsMatchCategoryDescription_shouldReturnPaginated(){

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var movies = Category
                .newCategory("Movies", null, true);

        final var series = Category
                .newCategory("Series", "some description", true);

        final var documentaries = Category
                .newCategory("Documentaries", null, true);


        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaMapper.toJpaEntity(movies),
                CategoryJpaMapper.toJpaEntity(series),
                CategoryJpaMapper.toJpaEntity(documentaries)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "some description", "name", "asc");

        final var actualResult = categoryMySQLGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());

    }

}