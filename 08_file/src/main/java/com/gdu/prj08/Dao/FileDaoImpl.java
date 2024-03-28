package com.gdu.prj08.Dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.gdu.prj08.Dto.FileDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class FileDaoImpl implements FileDao {

  private final SqlSessionTemplate sqlSessionTemplate;
 
  public final static String NS="com.gdu.prj08.mybatis.mapper.file_t";
  
  
  @Override
  public int insertHistory(HistoryDto history) {
    return sqlSessionTemplate.insert("com.gdu.prj08.mybatis.mapper.file_t.insertHistory", history);
  }

  @Override
  public int insertFile(FileDto file) {
    return sqlSessionTemplate.insert("com.gdu.prj08.mybatis.mapper.file_t.insertFile", file);
  }

  @Override
  public int modifyFile(FileDto file) {
  
    int updateCount = sqlSessionTemplate.update(NS+"modifyFile",file);
    return updateCount;
  }

  @Override
  public int removeFile(int fileNo) {
  
    int deleteCount = sqlSessionTemplate.delete(NS+"removeFile", fileNo);
    
    return deleteCount;
  }

  @Override
  public List<FileDto> getFileList() {
 
    List<FileDto> fileList = sqlSessionTemplate.selectList(NS+"getFileList");
    return fileList;
  }

  @Override
  public FileDto getFileByNo(int fileNo) {

    FileDto file = sqlSessionTemplate.selectOne(NS+"getFileByNo", fileNo);
    
    return file;
  }

}
