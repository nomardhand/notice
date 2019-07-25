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