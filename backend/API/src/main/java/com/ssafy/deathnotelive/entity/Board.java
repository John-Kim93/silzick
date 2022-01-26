package com.ssafy.deathnotelive.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board",
        indexes = {
                @Index(columnList = "id"),
                @Index(columnList = "userId"),
                @Index(columnList = "title"),
                @Index(columnList = "content"),
                @Index(columnList = "hit"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "modifiedAt"),
        })
@Entity
public class Board extends BaseEntity{

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(2000)")
    private String content;

    @Setter
    @Column(nullable = false)
    private Integer hit;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

}