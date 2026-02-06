package com.fullcycle.admim.catalogo.infra.category;


import com.fullcycle.admim.catalogo.infra.MySQLGatewayTest;
import com.fullcycle.admim.catalogo.infra.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    public void injectedDependencies() {
        Assertions.assertNotNull(categoryMySQLGateway);
        Assertions.assertNotNull(categoryJpaRepository);
    }


}