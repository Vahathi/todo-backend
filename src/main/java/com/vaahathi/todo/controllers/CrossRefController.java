package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.CrossRef;
import com.vaahathi.todo.models.crossref.CrossRefResponse;
import com.vaahathi.todo.repository.CrossRefRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/crossRef")
@Tag(name = "crossRefs", description = "crossRef maanagement APIs")


public class CrossRefController {
    @Autowired
    CrossRefRepository crossRefRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "getting a crossref ",
            description = "Id will be automatically generated in UUID format"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "crossref found successfully",
                    content = @Content(schema = @Schema(implementation = CrossRef.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{gearKey}")
    public ResponseEntity<List<CrossRefResponse>> getrootCrossRef(
            @PathVariable("gearKey") String gearKey) {
        List<CrossRef> crossRefs = crossRefRepository.findBygearKey(gearKey);
        Type listType = new TypeToken<List<CrossRefResponse>>() {
        }.getType();
        List<CrossRefResponse> crossRefResponses = modelMapper.map(crossRefs, listType);
        return ResponseEntity.ok(crossRefResponses);
    }
}


