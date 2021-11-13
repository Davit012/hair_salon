package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.hairsaloncommon.model.Worker;
import com.hairsaloncommon.repository.WorkerRepository;
import com.hairsaloncommon.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class WorkerServiceTest {

    @MockBean
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerService workerService;

    @Test
    public void findAll() {
        Worker worker = Worker.builder()
                .id(4)
                .name("test")
                .surname("test")
                .phoneNumber("test")
                .build();

        when(workerRepository.findAll()).thenReturn(Arrays.asList(worker));
        List<Worker> all = workerService.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void saveWorker() {
        Worker worker = Worker.builder()
                .id(4)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        when(workerRepository.findAll()).thenReturn(Arrays.asList(worker));
        assertEquals(1, workerRepository.findAll().size());
    }


    @Test
    public void findById() {
        int id = 4;
        Worker worker = Worker.builder()
                .id(id)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        Worker foundWorker = workerService.findWorkerById(worker.getId());
        assertEquals(foundWorker.getId(), worker.getId());
    }


    @Test
    public void editWorkerTest() {
        Worker worker = Worker.builder()
                .id(4)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        Worker save = workerRepository.save(worker);
        save.setName("newFirstName");
        Worker editWorker = workerService.editWorker(worker.getId(), save);
        assertEquals(editWorker.getName(), "newFirstName");

    }


    @Test
    public void deleteWorker() {
        int id = 4;
        Worker worker = Worker.builder()
                .id(id)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.findById(id)).thenReturn(Optional.of(worker));
        workerService.deleteWorkerById(id);
        verify(workerRepository).deleteById(id);

    }


}