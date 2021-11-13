package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.hairsaloncommon.model.Photo;
import com.hairsaloncommon.model.Worker;
import com.hairsaloncommon.repository.PhotoRepository;
import com.hairsaloncommon.service.PhotoService;
import com.hairsaloncommon.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class PhotoServiceTest {

    @MockBean
    private PhotoRepository photoRepository;

    @MockBean
    private WorkerService workerService;

    @Autowired
    private PhotoService photoService;

    @Test
    public void savePhotoTest() throws IOException {

        Worker worker = Worker.builder()
                .id(1)
                .name("testWorker")
                .phoneNumber("testWorkerSurName")
                .build();

        Photo photo = Photo.builder()
                .id(1)
                .value("photo.jpg")
                .worker(worker)
                .build();

        InputStream in = new FileInputStream("src/test/resources/test-upload-dir/photo.jpg");
        MockMultipartFile file = new MockMultipartFile(photo.getValue(), in);

        when(photoRepository.save(anyObject())).thenReturn(photo);
        when(workerService.findWorkerById(anyInt())).thenReturn(worker);
        final Photo savePhoto = photoService.savePhoto(file, anyInt());
        assertEquals(photo.getValue(), savePhoto.getValue());
    }

    @Test
    public void deletePhotoTest() {
        photoService.deletePhoto(anyInt());
        verify(photoRepository, times(1)).deleteById(anyInt());
    }
}