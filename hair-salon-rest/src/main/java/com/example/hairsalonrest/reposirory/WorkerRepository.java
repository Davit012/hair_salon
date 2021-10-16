package com.example.hairsalonrest.reposirory;

import com.hairsaloncommon.model.Photo;
import com.hairsaloncommon.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {

}
