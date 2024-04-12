package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
  boolean registerUpload(MultipartHttpServletRequest multipartRequest);
  void loadUploadList(Model model);
  void loadUploadByNo(int uploadNo, Model model);
  ResponseEntity<Resource> download(HttpServletRequest request); //번호만 있어도 다운로드 가능하지만 리퀘스트가 있는게 좋다. 
  ResponseEntity<Resource> downloadAll(HttpServletRequest request);
  void removeTempFiles();
}