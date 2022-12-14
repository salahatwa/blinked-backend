package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
