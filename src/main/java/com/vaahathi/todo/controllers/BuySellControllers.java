package com.vaahathi.todo.controllers;

import com.vaahathi.todo.repository.BuySellRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BuySellControllers {
    @Autowired
    BuySellRepository buySellRepository;
    // @Operation(
    //  summary = "storing buysell",
    //    description = "Id will be automatically generated in UUID format"
    //  )
    //  @ApiResponses(value = {
    //         @ApiResponse(responseCode = "200", description = "Appointment created successfully",
    //             content = @Content(schema = @Schema(implementation = BuySell.class))),
    //      @ApiResponse(responseCode = "404", description = "Resource not found"),
    //   @ApiResponse(responseCode = "500", description = "Internal server error")})
}
