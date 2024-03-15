package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_withdrawal_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalUserEntity {
    @Id
    @Column(name = "user_pk")
    private Long id;

    @MapsId
    @OneToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk",referencedColumnName = "userPk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDateTime approvalDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime applyDate;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String reason;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
