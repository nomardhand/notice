# 프로젝트 명
* Notice

## 프로젝트 기능 
* 공지사항 웹 어플리케이션 구현

## 개발 환경
* JDK 1.8
* IntelliJ 2.1.6.RELEASE
* thymeleaf 3.0.10.RELEASE
* MySQL 5.7

## 주요 기능
* 사용자는 텍스트로 된 공지를 추가할 수 있다. 
* 사용자는 공지를 수정/삭제할 수 있다.
* 사용자는 공지목록을 조회할 수 있다.
* 조회시 제목, 작성일, 작성자, 최종수정일, 내용이 조회 가능하다. 
* 목록은 페이징 기능이 있다.

## MySQL 정보보

> notice.sql
```bash
------------------------------------------------
-- MySQ 연결 정보
--
-- url : 127.0.0.1
-- scheme : db_notice-db
-- username: root
-- password: 없음
------------------------------------------------

-- db_notice 스키마 생성
CREATE DATABASE `db_notice` CHARACTER SET utf8mb4;

-- tb_notice 테이블 생성
CREATE TABLE `tb_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `writer` varchar(100) DEFAULT NULL,
  `regdate` datetime NOT NULL,
  `moddate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```
