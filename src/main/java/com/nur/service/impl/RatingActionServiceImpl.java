package com.nur.service.impl;

import com.nur.dto.EmailDTO;
import com.nur.dto.RatingDTO;
import com.nur.entity.CrrEmailNotificationEntity;
import com.nur.entity.RatingActionEntity;
import com.nur.entity.id.RatingActionId;
import com.nur.repository.CrrEmailNotificationRepository;
import com.nur.repository.RatingActionRepository;
import com.nur.service.EmailService;
import com.nur.service.RatingActionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingActionServiceImpl implements RatingActionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingActionServiceImpl.class);

    private final RatingActionRepository repository;
    private final CrrEmailNotificationRepository crrEmailNotificationRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public RatingActionServiceImpl(RatingActionRepository repository, CrrEmailNotificationRepository crrEmailNotificationRepository, ModelMapper modelMapper, EmailService emailService) {
        this.repository = repository;
        this.crrEmailNotificationRepository = crrEmailNotificationRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    public RatingDTO addRatingAction(RatingDTO dto) {
        RatingActionEntity entity = modelMapper.map(dto, RatingActionEntity.class);
        entity.setId(new RatingActionId(dto.getCountry(), dto.getRatingDate()));
        RatingActionEntity savedAction = repository.save(entity);
        RatingDTO responseDto = modelMapper.map(savedAction, RatingDTO.class);

        emailService.sendEmail(createEmailDetails(dto));

        return responseDto;
    }

    private EmailDTO createEmailDetails(RatingDTO dto) {
        CrrEmailNotificationEntity emailDetails = crrEmailNotificationRepository.findAll().get(0);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCountry(dto.getCountry());
        emailDTO.setEmailFrom(emailDetails.getEmailFrom());
        emailDTO.setEmailTo(emailDetails.getEmailTo());
        emailDTO.setEmailSubject(emailDetails.getEmailSubject());
        emailDTO.setEmailBody(emailDetails.getEmailBody()+ "-" + emailDTO.getCountry() + String.format("%n%n") + getEmailContent(dto));
        emailDTO.setEmailSignature(emailDetails.getEmailSignature());
        emailDTO.setCurrentCRR(String.valueOf(dto.getOldCrr()));
        emailDTO.setProposedCRR(String.valueOf(dto.getNewCrr()));
        emailDTO.setCurrentCRROutlook(dto.getOldCrrOutlook());
        emailDTO.setProposedCRROutlook(dto.getNewCrrOutlook());
        return emailDTO;
    }

    private String getEmailContent(RatingDTO dto) {
        return "<html>" +
                "<body> <br>" +
                "<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\" width=\"70%\" style=\"border-collapse: collapse; border: 1px solid gray;\">" +
                "<tr style=\"text-align: left;\"><th>Field Name</th><th>Old Value</th><th>New Value</th></tr>" +
                String.format("<tr><td>CRR</td><td>%s</td><td>%s</td></tr>%n", dto.getOldCrr(), dto.getNewCrr()) +
                String.format("<tr><td>CRR Outlook</td><td>%s</td><td>%s</td></tr>%n", dto.getOldCrrOutlook(), dto.getNewCrrOutlook()) +
                "</table>" +
                "</body>" +
                "</html>";
    }


}

