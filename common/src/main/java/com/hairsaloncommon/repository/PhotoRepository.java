package com.hairsaloncommon.repository;

import com.hairsaloncommon.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Optional<List<Photo>> getAllByWorker_Id(int id);

}
