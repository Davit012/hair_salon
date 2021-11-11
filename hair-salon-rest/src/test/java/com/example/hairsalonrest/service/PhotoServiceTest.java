package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.repository.PhotoRepository;
import com.example.hairsalonrest.service.impl.PhotoServiceImpl;
import com.hairsaloncommon.model.Photo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class PhotoServiceTest {
    @Mock
    PhotoRepository photoRepository;

    @Mock
    private Photo photo;

    @InjectMocks
    PhotoServiceImpl photoService;

    @Test
    public void savePhotoTest() {
        photoService.savePhoto((MultipartFile) photo, anyInt());
        verify(photoRepository, times(1)).save(photo);
    }

    @Test
    public void deletePhotoTest() {
        photoService.deletePhoto(anyInt());
        verify(photoRepository, times(1)).deleteById(anyInt());
    }
}