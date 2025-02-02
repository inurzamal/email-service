package com.nur.controller;

import com.nur.dto.RatingDTO;
import com.nur.service.RatingActionService;
import com.nur.service.impl.RatingActionServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@CrossOrigin
public class RatingActionController {

    private final RatingActionService service;
    private final ModelMapper modelMapper;

    @Autowired
    public RatingActionController(RatingActionServiceImpl service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RatingDTO> addRatingAction(@Valid @RequestBody RatingDTO dto) {
        return ResponseEntity.ok(service.addRatingAction(dto));
    }


    @GetMapping("/fetchAll")
    public ResponseEntity<List<RatingDTO>> getRatingActions() {
        return ResponseEntity.ok(service.getAllRatingActions());
    }

}
