package com.nur.entity;


import com.nur.entity.id.RatingActionId;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;

@Data
@Entity
public class RatingActionEntity implements BaseEntity, Persistable<RatingActionId> {

    @EmbeddedId
    private RatingActionId id;

    private Integer oldCrg;
    private String oldCrrOutlook;
    private Integer newCrg;
    private String newCrrOutlook;
    private String rattingComment;
    private BigDecimal oldCrr;
    private BigDecimal newCrr;

    @Transient
    private boolean isNewRecord = true;


    @Override
    public RatingActionId getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNewRecord;
    }

    @PostLoad
    @PrePersist
    void trackNotNew(){
        this.isNewRecord = false;
    }
}