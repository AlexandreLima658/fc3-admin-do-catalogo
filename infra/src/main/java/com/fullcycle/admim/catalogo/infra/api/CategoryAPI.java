package com.fullcycle.admim.catalogo.infra.api;

import com.fullcycle.admim.catalogo.domain.pagination.Pagination;
import com.fullcycle.admim.catalogo.infra.category.models.CreateCategoryApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "categories")
@Tag(name = "Categories")
public interface CategoryAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created successfully"),
        @ApiResponse(responseCode = "422", description = "Unprocessable error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryApiInput input);

    @GetMapping
    @Operation(summary = "List all category paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

}
