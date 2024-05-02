package com.example.onebox.product;

import com.example.onebox.config.mapper.ProductEntityMapper;
import com.example.onebox.product.model.ProductDto;
import com.example.onebox.product.model.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/products")
@RestController
@Tag(name = "Product", description = "Product API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Operation(summary = "Find all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "No products found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public List<ProductDto> findAllProducts() {
        List<ProductEntity> productEntities = this.productService.getAllProducts();
        return productEntities.stream()
                .map(this.productEntityMapper::toDto)
                .toList();
    }

    @Operation(summary = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product with id not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@Parameter(name = "id", description = "product id", example = "1", required = true) @PathVariable Long id) {
        this.productService.delete(id);
    }

    @Operation(summary = "Save or modigy a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ProductDto save(@RequestBody ProductDto productDto) {
        return this.productEntityMapper.toDto(this.productService.save(productDto));
    }

}
