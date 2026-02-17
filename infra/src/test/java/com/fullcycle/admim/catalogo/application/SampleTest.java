package com.fullcycle.admim.catalogo.application;

import com.fullcycle.admim.catalogo.IntegrationTest;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleTest {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Test
    public void test(){
        Assertions.assertNotNull(createCategoryUseCase);
        Assertions.assertNotNull(categoryJpaRepository);
    }

}
