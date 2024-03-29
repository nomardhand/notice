package com.im.demo.notice.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
public class PageVO {

	public static final int DEFAULT_SIZE = 20; // 한 화면에 표시할 리스트의 수
	public static final int DEFAULT_MAX_SIZE = 50;

	private int page;
	private int size;

	private String keytype; // keyword 타입
	private String keyword;

	public PageVO() {
		this.page = 1;
		this.size = DEFAULT_SIZE;
	}

	public PageVO(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public void setPage(int page) {
		this.page = page < 0 ? 1 : page;
	}

	public void setSize(int size) {
		this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE : size;
	}

	public Pageable makePageable(int direction, String... props) {
		Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
		return PageRequest.of(this.page - 1, this.size, dir, props);
	}
}
