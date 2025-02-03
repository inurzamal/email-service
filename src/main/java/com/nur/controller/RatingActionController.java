package com.nur.controller;

import com.nur.dto.RatingDTO;
import com.nur.service.RatingActionService;
import com.nur.service.impl.RatingActionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@CrossOrigin
public class RatingActionController {

    private final RatingActionService service;

    @Autowired
    public RatingActionController(RatingActionServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RatingDTO> addRatingAction(@Valid @RequestBody RatingDTO dto) {
        return ResponseEntity.ok(service.addRatingAction(dto));
    }

}
