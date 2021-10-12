package com.example.hairsalonweb.service.impl;

import com.example.hairsalonweb.repository.WorkerRepository;
import com.example.hairsalonweb.service.WorkerService;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    public Worker add(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker getWorkerById(int id) {
        return workerRepository.getById(id);
    }

    @Override
    public void deleteWorker(int id) {
        workerRepository.deleteById(id);
    }

    @Override
    public Worker updateWorker(int id, Worker worker) {
        Worker workerById = workerRepository.findById(id).get();
        workerById.setId(worker.getId());
        workerById.setName(worker.getName());
        workerById.setSurname(worker.getSurname());
        workerById.setPhoneNumber(worker.getPhoneNumber());
        workerById.setServices(worker.getServices());
        return workerRepository.save(workerById);
    }
}
