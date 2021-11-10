package com.example.hairsalonrest.service;

import com.example.hairsalonrest.repository.ServiceRepository;
import com.hairsaloncommon.model.Service;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceServiceTest {


    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService;

    @Test
    public void findAll() {

        Service service = Service.builder()
                .id(5)
                .name("Test")
                .description("test")
                .duration(50)
                .build();

        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        List<Service> allServices = serviceService.findAll();
        assertThat(allServices.size()).isEqualTo(1);

    }

    @Test
    public void addServiceTest() {
        Service service = Service.builder()
                .id(1)
                .name("Service")
                .price(11)
                .duration(15)
                .build();

        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        Service addService = serviceService.addService(service);

        assertThat(addService.getName()).isEqualTo(service.getName());
    }


    @Test
    public void findById() {
        Service service = Service.builder()
                .id(1)
                .name("Service")
                .price(11)
                .duration(15)
                .build();

        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        Service save = serviceRepository.save(service);
        Optional<Service> foundService = serviceService.findById(service.getId());
        assertEquals(foundService.get().getId(), save.getId());
    }

    @Test
    public void editService() {
        Service service = Service.builder()
                .id(1)
                .name("Service")
                .price(11)
                .duration(15)
                .build();


        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        Service save = serviceRepository.save(service);
        save.setName("newServiceName");
        Service editService = serviceService.editService(save.getId(), save);
        assertEquals(editService.getName(), is("newServiceName"));
    }

    @Test
    public void deleteService() {
        int id = 5;
        Service service = Service.builder()
                .id(id)
                .name("Service")
                .price(11)
                .duration(15)
                .build();

        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        serviceService.deleteService(id);
        verify(serviceRepository).deleteById(id);
    }

}