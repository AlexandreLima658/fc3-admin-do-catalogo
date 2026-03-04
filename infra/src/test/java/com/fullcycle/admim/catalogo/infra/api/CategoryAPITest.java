package com.fullcycle.admim.catalogo.infra.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admim.catalogo.ControllerTest;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryOutput;
import com.fullcycle.admim.catalogo.application.category.update.UpdateCategoryUseCase;
import com.fullcycle.admim.catalogo.domain.category.CategoryID;
import com.fullcycle.admim.catalogo.infra.category.models.CreateCategoryApiInput;
import com.fullcycle.admim.catalogo.infra.category.models.UpdateCategoryApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockitoBean
    private UpdateCategoryUseCase updateCategoryUseCase;

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
                        status().isCreated(),
                        MockMvcResultMatchers.header().string("Location", "/categories/123")

                );

    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {

        final var expectedId = "123";
        final var expectedName = "Movies";
        final var expectedDescription = "some description";
        final var expectedIsActive = true;

        final var aCommand =
                new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(updateCategoryUseCase.execute(any()))
                .thenReturn(API.Right(
                        UpdateCategoryOutput.from(expectedId)
                ));

        final var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));


        final var response = this.mvc.perform(request)
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
               Objects.equals(expectedName, cmd.name())
                       && Objects.equals(expectedDescription, cmd.description())
                       && Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }

}
