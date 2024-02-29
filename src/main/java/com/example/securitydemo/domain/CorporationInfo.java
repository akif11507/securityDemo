package com.example.securitydemo.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 法人管理
 *
 * @author chujo
 */
@Entity
@Table(name = "corporation_info")
@Data
@ToString(exclude = {"accountInfos"})
public class CorporationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "corp_id", nullable = false, unique = true)
    private String corpId;

    @Column(name = "company", nullable = false)
    private String company;


    @Column(name = "address")
    private String address;

    @Column(name = "tel")
    private String tel;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "created_user_id", nullable = false)
    private Long createdUserId;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;

    @Column(name = "updated_user_id")
    private String updatedUserId;

    @Column(name = "delete_flag")
    private Boolean delete_flag;

    @OneToMany(targetEntity = AccountInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "corporationInfo", orphanRemoval = true)
    private List<AccountInfo> accountInfos;

}
