package com.hairsaloncommon.service;

import com.hairsaloncommon.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    List<Service> findAll();

    Service addService(Service service);

    Optional<Service> findById(int id);

    Service editService(int id, Service service);

    void deleteService(int id);
}