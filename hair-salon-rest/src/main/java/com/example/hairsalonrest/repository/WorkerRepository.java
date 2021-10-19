package com.example.hairsalonrest.repository;

import com.hairsaloncommon.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

}
