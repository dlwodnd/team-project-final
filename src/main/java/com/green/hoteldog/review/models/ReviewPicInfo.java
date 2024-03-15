package com.green.hoteldog.review.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewPicInfo {
    private long reviewPk;
    private String reviewPic;
}
