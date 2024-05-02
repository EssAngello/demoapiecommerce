package com.example.onebox.customer;

import com.example.onebox.config.mapper.CustomerEntityMapper;
import com.example.onebox.customer.model.CustomerDto;
import com.example.onebox.customer.model.CustomerEntity;
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

@RequestMapping(value = "/customers")
@RestController
@Tag(name = "Customer", description = "Customer API")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Operation(summary = "Find all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "No customers found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public List<CustomerDto> findAllCustomers() {
        List<CustomerEntity> customerEntities = this.customerService.getAllCustomers();
        return customerEntities.stream()
                .map(this.customerEntityMapper::toDto)
                .toList();
    }

    @Operation(summary = "Delete customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Customer with id not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@Parameter(name = "id", description = "customer id", example = "1", required = true) @PathVariable Long id) {
        this.customerService.delete(id);
    }

    @Operation(summary = "Save or modify a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public CustomerDto save(@RequestBody CustomerDto customerDto) {
        return this.customerEntityMapper.toDto(this.customerService.save(customerDto));
    }

}
