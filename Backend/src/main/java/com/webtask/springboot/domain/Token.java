package com.webtask.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tokens")
public class Token {
    @Id
    @GeneratedValue()
    private long id;
    private String jwtToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
