package com.fullcycle.admim.catalogo.application;


import com.fullcycle.admim.catalogo.domain.category.Category;

public class UseCase {

    public Category execute(){
        return new Category();
    }
}