package com.shop31cm.domain;

import com.shop31cm.domain.common.BaseTimeEntity;
import com.shop31cm.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nickname")
    private String nickname;

    @NotNull
    @Email
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "user_password")
    private String userPassword;

    @NotNull
    @Column(name = "user_phone")
    private String userPhone;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;


//    @OneToOne(mappedBy = "storeOwner")
//    private Store store;

}
