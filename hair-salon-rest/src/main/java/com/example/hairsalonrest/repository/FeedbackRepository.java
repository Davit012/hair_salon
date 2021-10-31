package com.example.hairsalonrest.repository;

import com.hairsaloncommon.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
