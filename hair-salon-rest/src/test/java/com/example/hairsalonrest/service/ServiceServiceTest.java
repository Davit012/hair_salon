package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.hairsaloncommon.model.Service;
import com.hairsaloncommon.repository.ServiceRepository;
import com.hairsaloncommon.service.ServiceService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class ServiceServiceTest {


    @MockBean
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

    @Test
    public void findAll() {

        Service service = Service.builder()
                .id(5)
                .name("Test")
                .description("test")
                .price(50)
                .duration(50)
                .build();

        when(serviceRepository.findAll()).thenReturn(Arrays.asList(service));
        List<Service> all = serviceService.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void addServiceTest() {
        Service service = Service.builder()
                .id(5)
                .name("Test")
                .description("test")
                .price(50)
                .duration(50)
                .build();

        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        when(serviceRepository.findAll()).thenReturn(Arrays.asList(service));
        Service addService = serviceService.addService(service);

        assertEquals(addService.getId(), service.getId());
        assertEquals(1, serviceRepository.findAll().size());
    }


    @Test
    public void findById() {
        Service service = Service.builder()
                .id(5)
                .name("Test")
                .description("test")
                .price(50)
                .duration(50)
                .build();

        when(serviceRepository.findById(service.getId())).thenReturn(Optional.of(service));
        Optional<Service> foundService = serviceService.findById(service.getId());
        assertTrue(foundService.isPresent());
        assertEquals(foundService.get().getId(), service.getId());
    }

    @Test
    public void editService() {
        Service service = Service.builder()
                .id(5)
                .name("Test")
                .description("test")
                .price(50)
                .duration(50)
                .build();

        when(serviceRepository.findById(service.getId())).thenReturn(Optional.of(service));
        when(serviceRepository.save(Mockito.any())).thenReturn(service);
        Service save = serviceRepository.save(service);
        save.setName("newServiceName");
        Service editService = serviceService.editService(service.getId(), save);
        assertEquals(editService.getName(), ("newServiceName"));

    }

    @Test
    public void deleteService() {
        int id = 5;
        Service service = Service.builder()
                .id(id)
                .name("Test")
                .description("test")
                .price(50)
                .duration(50)
                .build();

        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        serviceService.deleteService(id);
        verify(serviceRepository).deleteById(id);
    }

}