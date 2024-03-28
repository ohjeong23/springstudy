package com.gdu.prj08.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.Dto.FileDto;

public interface UploadService {
  int upload1(MultipartHttpServletRequest multipartRequest);
  Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest);
  List<FileDto> getFileList();
  FileDto getFileByNo(int fileNo);

}