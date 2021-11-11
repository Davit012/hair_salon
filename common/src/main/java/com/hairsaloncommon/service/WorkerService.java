package com.hairsaloncommon.service;


import com.hairsaloncommon.model.Worker;

import java.util.List;

public interface WorkerService {

    Worker findWorkerById(int id);

    List<Worker> findAll();

    Worker save(Worker worker);

    Worker editWorker(int id, Worker worker);

    void deleteWorkerById(int id);

}
