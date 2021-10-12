package com.example.hairsalonweb.service;

import com.hairsaloncommon.model.Worker;

import java.util.List;

public interface WorkerService {

    Worker add(Worker worker);

    List<Worker> getAllWorkers();

    void deleteWorker(int id);

    Worker updateWorker(int id, Worker worker);

    Worker getWorkerById(int id);


}
