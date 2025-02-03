package com.nur.dto;

import lombok.Data;

@Data
public class EmailDTO {
    private String emailSubject;
    private String emailBody;
    private String emailFrom;
    private String emailTo;
    private String emailSignature;

    private String currentCRR;
    private String proposedCRR;
    private String currentCRROutlook;
    private String proposedCRROutlook;

}
