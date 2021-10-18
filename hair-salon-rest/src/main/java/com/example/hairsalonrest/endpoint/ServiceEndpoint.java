package com.example.hairsalonrest.endpoint;

import com.example.hairsalonrest.dto.servicedtos.ServiceCreateDto;
import com.example.hairsalonrest.dto.servicedtos.ServiceDto;
import com.example.hairsalonrest.dto.servicedtos.ServicePutDto;
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
@RequestMapping(value = "/services")
public class ServiceEndpoint {
    private final ServiceService serviceService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        List<Service> all = serviceService.findAll();
        List<ServiceDto> serviceDtos = new ArrayList<>();
        if (all.isEmpty()){
            ResponseEntity.noContent().build();
        }
        for (Service service : all) {
            ServiceDto serviceDto = mapper.map(service, ServiceDto.class);
            serviceDtos.add(serviceDto);
        }
        return ResponseEntity.ok(serviceDtos);
    }

    @PostMapping
    public ResponseEntity<ServiceDto> addService(@RequestBody ServiceCreateDto service) {
        Service byId = serviceService.addService(mapper.map(service, Service.class));
        if (byId.getId() != 0) {
            return ResponseEntity.ok(mapper.map(byId, ServiceDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable("id") int id) {
        Optional<Service> byId = serviceService.findById(id);
        return byId.map(service ->
                ResponseEntity.ok(mapper.map(service, ServiceDto.class))).orElseGet(() ->
                ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable(name = "id") int id, @RequestBody ServicePutDto service) {
        if (serviceService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Service serviceFromDb = serviceService.editService(id, mapper.map(service, Service.class));
        return ResponseEntity.ok(mapper.map(serviceFromDb, ServiceDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable(name = "id") int id) {
        if (serviceService.findById(id).isPresent()){
            serviceService.deleteService(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
