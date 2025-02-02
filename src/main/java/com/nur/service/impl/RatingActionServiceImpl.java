package com.nur.service.impl;

import com.nur.dto.RatingDTO;
import com.nur.entity.RatingActionEntity;
import com.nur.entity.id.RatingActionId;
import com.nur.repository.RatingActionRepository;
import com.nur.service.EmailService;
import com.nur.service.RatingActionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingActionServiceImpl implements RatingActionService {

    private final RatingActionRepository repository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public RatingActionServiceImpl(RatingActionRepository repository, ModelMapper modelMapper, EmailService emailService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public RatingDTO addRatingAction(RatingDTO dto) {
        // Convert DTO to Entity
        RatingActionEntity entity = modelMapper.map(dto, RatingActionEntity.class);
        entity.setId(new RatingActionId(dto.getCountry(), dto.getRatingDate()));

        // Save entity
        RatingActionEntity savedAction = repository.save(entity);

        // Send email asynchronously after saving
        sendEmailNotification(dto);

        return modelMapper.map(savedAction, RatingDTO.class);
    }

    private void sendEmailNotification(RatingDTO dto) {
        String recipient = "nurzamal0077@gmail.com"; // Replace with actual user email
        String subject = "New Rating Action Added";
        String message = String.format("A new rating action for %s on %s has been added successfully.",
                dto.getCountry(), dto.getRatingDate());

        emailService.sendSimpleEmail(recipient, subject, message);
    }


    @Override
    public List<RatingDTO> getAllRatingActions() {
        List<RatingActionEntity> entityList = repository.findAll();
        return entityList.stream().map(entity -> {
            RatingDTO dto = modelMapper.map(entity, RatingDTO.class);
            if (entity.getId() != null) {
                dto.setCountry(entity.getId().getCountry());
                dto.setRatingDate(entity.getId().getRatingDate());
            }
            return dto;
        }).toList();
    }



}

