package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.CreatedAtBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "t_res_dog_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDogInfoEntity extends CreatedAtBaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resDogPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "size_pk",referencedColumnName = "sizePk",columnDefinition = "BIGINT UNSIGNED")
    private DogSizeEntity dogSizeEntity;

    @Column(nullable = false)
    private String dogNm;

    @Column
    private Long age;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String information;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToMany(mappedBy = "resDogInfoEntity",fetch = FetchType.LAZY,orphanRemoval = true)
    List<ResComprehensiveInfoEntity> resComprehensiveInfoEntity;
    //재웅
}
