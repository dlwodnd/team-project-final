package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "t_board")
public class BoardEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk",referencedColumnName = "userPk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "board_category_pk",referencedColumnName = "boardCategoryPk",columnDefinition = "TINYINT UNSIGNED")
    private BoardCategoryEntity boardCategoryEntity;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long boardViewCount;

    @Column(nullable = false)
    private String boardNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToMany(mappedBy = "boardEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<BoardCommentEntity> boardCommentEntityList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "boardEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    private Set<BoardPicsEntity> boardPicsEntityList = new HashSet<>();

    //재웅
}