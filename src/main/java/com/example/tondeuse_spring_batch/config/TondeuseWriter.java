package com.example.tondeuse_spring_batch.config;

import com.example.tondeuse_spring_batch.entities.Tondeuse;
import com.example.tondeuse_spring_batch.repository.TondeuseRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class TondeuseWriter implements ItemWriter<Tondeuse> {
 private TondeuseRepository tondeuseRepository;

    public TondeuseWriter(TondeuseRepository tondeuseRepository) {
        this.tondeuseRepository = tondeuseRepository;
    }

    @Override
    public void write(Chunk<? extends Tondeuse> chunk) throws Exception {
        for (Tondeuse tondeuse : chunk) {
            // Custom logic to store the article in the database
            tondeuseRepository.save(tondeuse);

            // Additional business logic or post-processing steps if needed
            // For example, updating related tables or triggering events
        }
    }
}
