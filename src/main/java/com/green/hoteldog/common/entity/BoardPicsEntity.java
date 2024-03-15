package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.CreatedAtBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_board_pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardPicsEntity extends CreatedAtBaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPicsPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "board_pk",referencedColumnName = "boardPk",columnDefinition = "BIGINT UNSIGNED")
    private BoardEntity boardEntity;

    @Column(name = "board_pic" ,nullable = false)
    private String pic;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
