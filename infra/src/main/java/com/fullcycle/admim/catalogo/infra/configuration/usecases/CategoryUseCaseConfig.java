package com.fullcycle.admim.catalogo.infra.configuration.usecases;


import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.fullcycle.admim.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.fullcycle.admim.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway gateway;

    public CategoryUseCaseConfig(final CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(){
        return new DefaultCreateCategoryUseCase(gateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(){
        return new DefaultUpdateCategoryUseCase(gateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase(){
        return new DefaultGetCategoryByIdUseCase(gateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(){
        return new DefaultListCategoriesUseCase(gateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(){
        return new DefaultDeleteCategoryUseCase(gateway);
    }
}
