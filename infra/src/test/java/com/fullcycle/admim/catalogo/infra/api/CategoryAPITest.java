package com.fullcycle.admim.catalogo.infra.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admim.catalogo.ControllerTest;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;
import com.fullcycle.admim.catalogo.infra.category.models.CreateCategoryApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase useCase;
    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {

        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = true;

        final var aCommand =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(createCategoryUseCase.execute(any()))
                .thenReturn(API.Right(
                        CreateCategoryOutput.from(CategoryID.from("123"))
                ));

        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));


        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.header().string("Location", "/categories/123")

                );

    }

}
