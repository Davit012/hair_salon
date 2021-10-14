package com.example.hairsalonrest.endpoint;

import com.example.hairsalonrest.dto.ServiceCreateDto;
import com.example.hairsalonrest.dto.ServiceDto;
import com.example.hairsalonrest.dto.ServicePutDto;
import com.example.hairsalonrest.service.ServiceService;
import com.hairsaloncommon.model.Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ServiceEndpoint {
    private final ServiceService serviceService;
    private final ModelMapper mapper;

    @GetMapping("/services")
    public List<ServiceDto> getServiceService() {
        List<Service> all = serviceService.findAll();
        List<ServiceDto> serviceDtos = new ArrayList<>();
        for (Service listing : all) {
            ServiceDto serviceDto = mapper.map(listing, ServiceDto.class);
            serviceDtos.add(serviceDto);

        }

        return serviceDtos;
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceDto> addService(@RequestBody ServiceCreateDto service) {
        Service byId = serviceService.addService(mapper.map(service, Service.class));
        if (byId.getId() != 0) {
            return ResponseEntity.ok(mapper.map(byId, ServiceDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable("id") int id) {
        Optional<Service> byId = serviceService.findById(id);
        return byId.map(service ->
                ResponseEntity.ok(mapper.map(service, ServiceDto.class))).orElseGet(() ->
                ResponseEntity.badRequest().build());
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceDto> putService(@PathVariable(name = "id") int id, @RequestBody ServicePutDto service) {
        if (serviceService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Service serviceFromDb = serviceService.putService(id, mapper.map(service, Service.class));
        return ResponseEntity.ok(mapper.map(serviceFromDb, ServiceDto.class));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable(name = "id") int id) {
        ResponseEntity<ServiceDto> service = getService(id);
        if (service.getStatusCode().is2xxSuccessful()) {
            serviceService.deleteService(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
