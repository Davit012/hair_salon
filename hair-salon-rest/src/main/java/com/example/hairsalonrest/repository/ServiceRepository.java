package com.example.hairsalonrest.repository;

import com.hairsaloncommon.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
