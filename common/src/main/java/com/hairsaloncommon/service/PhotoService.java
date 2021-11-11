package com.hairsaloncommon.service;


import com.hairsaloncommon.model.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo savePhoto(MultipartFile multipartFile, int id);

    void deletePhoto(int id);


}
