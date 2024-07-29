package com.example.tondeuse_spring_batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tondeuse_spring_batch.entities.Tondeuse;

public interface TondeuseRepository extends JpaRepository<Tondeuse, Long>{

}
