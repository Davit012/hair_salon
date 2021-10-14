package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.ServiceRepository;
import com.example.hairsalonrest.service.ServiceService;
import com.hairsaloncommon.model.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service addService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Optional<Service> findById(int id) {
        Optional<Service> byId = serviceRepository.findById(id);
        return byId;
    }

    @Override
    public Service putService(int id, Service service) {
        Service byId = serviceRepository.findById(id).get();

        byId.setName(service.getName());
        byId.setDescription(service.getDescription());
        byId.setPrice(service.getPrice());

        return byId;
    }

    @Override
    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }
}
