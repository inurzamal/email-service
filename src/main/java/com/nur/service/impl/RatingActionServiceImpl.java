package com.nur.service.impl;

import com.nur.dto.RatingDTO;
import com.nur.entity.RatingActionEntity;
import com.nur.entity.id.RatingActionId;
import com.nur.repository.RatingActionRepository;
import com.nur.service.EmailService;
import com.nur.service.RatingActionService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingActionServiceImpl implements RatingActionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingActionServiceImpl.class);

    private final RatingActionRepository repository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public RatingActionServiceImpl(RatingActionRepository repository, ModelMapper modelMapper, EmailService emailService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    public RatingDTO addRatingAction(RatingDTO dto) {
        // Convert DTO to Entity
        RatingActionEntity entity = modelMapper.map(dto, RatingActionEntity.class);
        entity.setId(new RatingActionId(dto.getCountry(), dto.getRatingDate()));

        // Save entity
        RatingActionEntity savedAction = repository.save(entity);

        // Convert back to DTO
        RatingDTO responseDto = modelMapper.map(savedAction, RatingDTO.class);

        // Send Email Notification
        String recipientEmail = "nurzamal0077@gmail.com"; // Fetch dynamically if needed
        String subject = "New Rating Action Added";
        String emailContent = getEmailContent(responseDto);

        try {
            emailService.sendHtmlEmail(recipientEmail, subject, emailContent);
        } catch (MessagingException e) {
            LOGGER.error("Error sending email notification: {}", e.getMessage());
        }

        return responseDto;
    }

    // Method to generate email content in HTML table format
    private String getEmailContent(RatingDTO dto) {
        return """
            <html>
            <body>
                <h2>New Rating Action Added</h2>
                <table border="1" cellpadding="5" cellspacing="0">
                    <tr><th>Country</th><td>%s</td></tr>
                    <tr><th>Rating Date</th><td>%s</td></tr>
                    <tr><th>Old CRG</th><td>%s</td></tr>
                    <tr><th>Old CRR Outlook</th><td>%s</td></tr>
                    <tr><th>New CRG</th><td>%s</td></tr>
                    <tr><th>New CRR Outlook</th><td>%s</td></tr>
                    <tr><th>Rating Comment</th><td>%s</td></tr>
                    <tr><th>Old CRR</th><td>%s</td></tr>
                    <tr><th>New CRR</th><td>%s</td></tr>
                </table>
                <p>Thank you!</p>
            </body>
            </html>
            """.formatted(
                dto.getCountry(),
                dto.getRatingDate(),
                dto.getOldCrg(),
                dto.getOldCrrOutlook(),
                dto.getNewCrg(),
                dto.getNewCrrOutlook(),
                dto.getRattingComment(),
                dto.getOldCrr(),
                dto.getNewCrr()
        );
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

