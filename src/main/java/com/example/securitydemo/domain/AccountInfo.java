package com.example.securitydemo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "account_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"mail_address"})})
@Data
public class AccountInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "FK_corporation_info_id", nullable = false)
    private Long corporationInfoId;


    @Column(name = "mail_address", nullable = false, unique = true)
    private String mailAddress;

    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "invited_dt")
    private Timestamp invitedDt;

    @Column(name = "invited_user_id")
    private Long invitedUserId;
    @Column(name = "registered_dt")
    private Timestamp registeredDt;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "created_user_id", nullable = false)
    private Long createdUserId;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;

    @Column(name = "updated_user_id")
    private Long updatedUserId;

    @Column(name = "last_login_dt")
    private Timestamp lastLoginDt;

    @ManyToOne(targetEntity = CorporationInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_corporation_info_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CorporationInfo corporationInfo;


}
