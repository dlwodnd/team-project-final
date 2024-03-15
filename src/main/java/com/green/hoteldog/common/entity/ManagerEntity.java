package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@Table(name = "t_manager")
@AllArgsConstructor
@NoArgsConstructor
public class ManagerEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerPk;

    @Column(nullable = false)
    private String managerId;

    @Column(nullable = false)
    private String managerPw;

    @Column(nullable = false)
    private String managerName;

    @ColumnDefault("'ADMIN'")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

}
