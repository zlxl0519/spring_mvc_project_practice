package com.seung.spring.file.dao;

import java.util.List;

import com.seung.spring.file.dto.FileDto;

public interface FileDao {
	public List<FileDto> getList(FileDto dto);
	public int getCount(FileDto dto);
}