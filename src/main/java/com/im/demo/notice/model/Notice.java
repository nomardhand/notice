package com.im.demo.notice.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_notice")
public class Notice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer", length = 100)
    private String writer; // 작성자

    @Column(name = "regdate", nullable = false)
    @CreationTimestamp
    private Date regdate;

    @Column(name = "moddate", nullable = true)
    @UpdateTimestamp // MySQL 버전에 따라서 지원하지 않음
    private Date moddate;
}
