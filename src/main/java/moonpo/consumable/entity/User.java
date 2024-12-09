package moonpo.consumable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "usr_mstr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id", length = 8)
    private String id;
    @Column(name = "usr_name", length = 8)
    private String name;
    @Column(name = "usr_pwd", length = 8)
    private String password;
    @Column(name = "usr_group", length = 20)
    private String group;
    @Column(name = "usr_scrgrp")
    private String scrgrp;
    @Column(name = "usr_last_login")
    private Timestamp lastLogin;
    @Column(name = "usr_restrict", length = 1)
    private Character restrict;
}
