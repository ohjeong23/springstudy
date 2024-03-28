package com.gdu.prj08.Dao;

import java.util.List;

import com.gdu.prj08.Dto.FileDto;
import com.gdu.prj08.Dto.HistroyDto;

public interface FileDao {
  
  int registerFile(FileDto file);
  int modifyFile(FileDto file);
  int removeFile(int fileNo);
  List<FileDto> getFileList();
  FileDto getFileByNo(int fileNo);
  
  
  
  
  
  

}
