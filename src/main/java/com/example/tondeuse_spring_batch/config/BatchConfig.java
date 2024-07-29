package com.example.tondeuse_spring_batch.config;


import com.example.tondeuse_spring_batch.entities.Pelouse;
import com.example.tondeuse_spring_batch.entities.Tondeuse;
import com.example.tondeuse_spring_batch.repository.TondeuseRepository;
import com.example.tondeuse_spring_batch.service.TondeuseService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    private TondeuseService tondeuseService;
    @Autowired
    private TondeuseRepository tondeuseRepository;

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("job",jobRepository)
                .start(step)
                //.flow(step).end()
                .incrementer(new RunIdIncrementer())
                .build();
    }


    @Bean
    public Step Step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws IOException {
        return new StepBuilder("step", jobRepository)
                .<String, String>chunk(5,platformTransactionManager)

                .reader(reader())

                .processor(processor())

                .writer(writer())

                //.taskExecutor(taskExecutor())

                //.transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public ItemReader<String> reader(){
        return new FlatFileItemReaderBuilder<String>()
                .name("tondeuseItemReader")
                .resource(new ClassPathResource("input.txt"))

                .lineMapper(new LineMapper<String>() {
                    @Override
                    public String mapLine(String line, int lineNumber) throws Exception {
                        return line;
                    }
                })
                .build();

    }



    @Bean
    public ItemProcessor<String, String> processor() {
        return new ItemProcessor<String, String>() {
            private Pelouse pelouse;
            private List<Tondeuse> tondeuses = new ArrayList<>();
            private boolean initialized = false;
            private int lineIndex = 0;

            @Override
            public String process(String line) throws Exception {
                if (!initialized) {
                    initialized = true;
                    String[] pelouseDimensions = line.split(" ");
                    pelouse = new Pelouse(Integer.parseInt(pelouseDimensions[0]), Integer.parseInt(pelouseDimensions[1]));
                    return null; // Skip the first line
                }

                if (lineIndex % 2 == 0) {
                    String[] tondeuseInfo = line.split(" ");
                    Tondeuse tondeuse = new Tondeuse(
                            Integer.parseInt(tondeuseInfo[0]),
                            Integer.parseInt(tondeuseInfo[1]),
                            tondeuseInfo[2].charAt(0)
                    );
                    tondeuses.add(tondeuse);
                    System.out.println("--------------------------SORTIE SOUHAITEE------------------------------------");
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$"+tondeuses+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println("------------------------------------------------------------------------");
                } else {
                    Tondeuse tondeuse = tondeuses.get(tondeuses.size() - 1);
                    tondeuseService.executerInstructions(tondeuse, pelouse, line);

                }

                lineIndex++;
                return null; // We are not interested in returning lines, just processing
            }

            /*@Override
            public void open(ExecutionContext executionContext) throws ItemStreamException {
                super.open(executionContext);
                tondeuses.clear();
                initialized = false;
                lineIndex = 0;
            }*/
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (String item : items) {
            }
        };
    }

    @Bean
    public ItemWriter<Tondeuse> tondeuseWriter() {
        return items -> {
            for (Tondeuse tondeuse : items) {
            }
        };
    }


}
