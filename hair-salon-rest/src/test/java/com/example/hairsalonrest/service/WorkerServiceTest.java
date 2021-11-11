package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.repository.WorkerRepository;
import com.hairsaloncommon.model.Worker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerService workerService;

    @Test
    public void findAll() {
        Worker worker = Worker.builder()
                .id(4)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        List<Worker> allWorkers = workerService.findAll();
        assertThat(allWorkers.size()).isEqualTo(1);

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
        Worker addedWorker = workerService.save(worker);

        assertThat(addedWorker.getName()).isEqualTo(worker.getName());
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

        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        Worker save = workerService.save(worker);
        Worker foundWorker = workerService.findWorkerById(id);
        assertEquals(foundWorker.getId(), save.getId());
    }


    @Test
    public void editWorkerTest() {
        Worker worker = Worker.builder()
                .id(4)
                .name("test")
                .surname("test")
                .phoneNumber("099999999")
                .build();

        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        Worker save = workerService.save(worker);
        save.setName("newFirstName");
        Worker editWorker = workerService.editWorker(1, worker);
        assertEquals(editWorker.getName(), is("newFirstName"));

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

        when(workerRepository.save(Mockito.any())).thenReturn(worker);
        workerService.deleteWorkerById(id);
        verify(workerRepository).deleteById(id);

    }


}