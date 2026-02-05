package com.fullcycle.admim.catalogo.infra.category.persistence;

import com.fullcycle.admim.catalogo.domain.category.Category;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;

public interface CategoryJpaMapper {

    static CategoryJpaEntity toJpaEntity(final Category aCategory) {

        return CategoryJpaEntity.from(
                aCategory.getId().getValue(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt()
        );
    }

    static Category toAggregate(final CategoryJpaEntity jpa) {

        final var id = CategoryID.from(jpa.getId());
        return Category.with(
                id,
                jpa.getName(),
                jpa.getDescription(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt(),
                jpa.getDeletedAt()
        );
    }

}
