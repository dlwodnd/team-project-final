package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "t_review")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPk;

    @OneToOne
    @JoinColumn(name = "res_pk",referencedColumnName = "resPk",columnDefinition = "BIGINT UNSIGNED")
    private ReservationEntity reservationEntity;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private String reviewNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToMany(mappedBy = "reviewEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    List<ReviewFavEntity> reviewFavEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "reviewEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    List<ReviewPicEntity> reviewPicEntity;
    //재웅
}
