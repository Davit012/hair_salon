package com.example.hairsalonrest.service;

import com.example.hairsalonrest.repository.PhotoRepository;
import com.example.hairsalonrest.service.impl.PhotoServiceImpl;
import com.hairsaloncommon.model.Photo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoServiceTest {
    @Mock
    PhotoRepository photoRepository;

    @Mock
    private ArrayList<Photo> photoList;

    @Mock
    private Photo photo;

    @InjectMocks
    PhotoServiceImpl photoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
