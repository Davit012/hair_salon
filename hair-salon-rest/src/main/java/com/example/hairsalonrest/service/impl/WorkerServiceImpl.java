package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.WorkerRepository;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    @Override
    public Worker findWorkerById(int id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.orElse(null);
    }
}
