package com.gdu.prj08.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FileDto {
  
  
  private int fileNo;
  private String uploadPath;
  private String originalFilename;
  private String filesystemName;
  private int historyNo;
  
  

}
