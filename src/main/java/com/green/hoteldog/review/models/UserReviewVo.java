package com.green.hoteldog.review.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewVo {
    private long reviewPk;
    private long resPk;
    private List<ReviewPicInfo> reviewPics = new ArrayList<>();
    private List<String> roomNm = new ArrayList<>();
    private long score;
    private String comment;
    private String hotelNm;
    private String createdAt;
}
