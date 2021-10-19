package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.ServiceRepository;
import com.example.hairsalonrest.service.ServiceService;
import com.hairsaloncommon.model.Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ModelMapper mapper;

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
        return serviceRepository.findById(id);
    }

    @Override
    public Service editService(int id, Service service) {
        Service byId = serviceRepository.findById(id).get();
        service.setId(id);
        mapper.map(service, byId);
        return addService(service);
    }

    @Override
    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }
}
