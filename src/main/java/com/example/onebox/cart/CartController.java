package com.example.onebox.cart;

import com.example.onebox.cart.model.CartDto;
import com.example.onebox.cart.model.CartEntity;
import com.example.onebox.config.mapper.CartEntityMapper;
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

@RequestMapping(value = "/carts")
@RestController
@Tag(name = "Cart", description = "Cart API")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartEntityMapper cartEntityMapper;

    @Operation(summary = "Find all carts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "No carts found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public List<CartDto> findAllCarts() {
        List<CartEntity> cartEntities = this.cartService.getAllCarts();
        return cartEntities.stream()
                .map(this.cartEntityMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get carts by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "No carts found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path="/findByCustomer/{id}", method = RequestMethod.GET)
    public List<CartDto> getCartsByCustomerId(@Parameter(name = "id", description = "customer id", example = "1", required = true) @PathVariable("id") Long id) {
        List<CartEntity> cartEntities = this.cartService.getCartsByCustomerId(id);
        return cartEntities.stream()
                .map(this.cartEntityMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get last active cart by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "No cart found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path="/customer/{id}", method = RequestMethod.GET)
    public CartDto getLastActiveCartByCustomerId(@Parameter(name = "id", description = "customer id", example = "1", required = true) @PathVariable("id") Long id) {
        return this.cartEntityMapper.toDto(this.cartService.getLastActiveCartByCustomerId(id));
    }

    @Operation(summary = "Delete cart by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cart with id not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public void delete(@Parameter(name = "id", description = "cart id", example = "1", required = true) @PathVariable Long id) {
        this.cartService.delete(id);
    }

    @Operation(summary = "Save or modify a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path="", method = RequestMethod.PUT)
    public CartDto save(@RequestBody CartDto cartDto) {
        return this.cartEntityMapper.toDto(this.cartService.save(cartDto));
    }

}

