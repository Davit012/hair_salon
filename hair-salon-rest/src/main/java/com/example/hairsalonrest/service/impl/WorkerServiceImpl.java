package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.WorkerRepository;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final ModelMapper mapper;

    @Override
    public Worker findWorkerById(int id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.orElse(null);
    }

    @Override
    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    @Override
    public Worker save(Worker worker) {
        for (Worker fromDb : findAll()) {
            if (fromDb.getPhoneNumber().equals(worker.getPhoneNumber())) {
                return null;
            }
        }
        return workerRepository.save(worker);

    }

    @Override
    public Worker editWorker(int id, Worker worker) {
        final Optional<Worker> byId = workerRepository.findById(id);
        worker.setId(id);

        if (byId.isPresent()) {
            Worker workerFromDb = byId.get();
            mapper.map(worker, workerFromDb.getClass());
        }
        return workerRepository.save(worker);
    }

    @Override
    public void deleteWorkerById(int id) {
        workerRepository.deleteById(id);
    }
}
