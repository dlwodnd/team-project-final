package com.green.hoteldog.common.entity.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResComprehensiveInfoComposite implements Serializable {
    private Long resPk;
    private Long hotelRoomPk;
    private Long resDogPk;
}
