package com.fullcycle.admim.catalogo.infra.category;

import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;
import com.fullcycle.admim.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admim.catalogo.domain.pagination.Pagination;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaEntity;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaMapper;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryJpaRepository repository;

    public CategoryMySQLGateway(final CategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
       final var category = repository.save(CategoryJpaMapper.toJpaEntity(aCategory));
       return CategoryJpaMapper.toAggregate(category);
    }

    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }
}
