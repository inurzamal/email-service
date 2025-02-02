package com.nur.service;



import com.nur.dto.RatingDTO;

import java.util.List;

public interface RatingActionService {

    RatingDTO addRatingAction(RatingDTO dto);

    List<RatingDTO> getAllRatingActions();

}
