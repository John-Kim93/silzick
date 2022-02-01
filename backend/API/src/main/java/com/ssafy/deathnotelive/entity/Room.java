package com.ssafy.deathnotelive.entity;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@Table(name = "room",
        indexes = {
                @Index(columnList = "roomId"),
                @Index(columnList = "code"),
                @Index(columnList = "participants"),
                @Index(columnList = "authority"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "modifiedAt"),
        })
@Entity
public class Room {
}
