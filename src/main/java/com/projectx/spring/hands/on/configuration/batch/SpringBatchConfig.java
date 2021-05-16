package com.projectx.spring.hands.on.configuration.batch;

import com.projectx.spring.hands.on.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

  @Bean
  public Job job(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory,
      ItemReader<User> itemReader,
      ItemProcessor<User, User> itemProcessor,
      ItemWriter<User> itemWriter) {

    Step step =
        stepBuilderFactory
            .get("Process_Users")
            .<User, User>chunk(1000)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();

    return jobBuilderFactory
        .get("Load_Users")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }

  @Bean
  public FlatFileItemReader<User> flatFileItemReader(
      @Value("${usersFileInput}") Resource resource) {
    FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
    flatFileItemReader.setResource(resource);
    flatFileItemReader.setName("Users-Reader");
    flatFileItemReader.setLinesToSkip(1);
    flatFileItemReader.setLineMapper(lineMapper());
    return flatFileItemReader;
  }

  @Bean
  public LineMapper lineMapper() {
    DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames(
        "email",
        "userName",
        "password",
        "firstName",
        "lastName",
        "active",
        "designation",
        "salary");
    defaultLineMapper.setLineTokenizer(lineTokenizer);

    BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
    beanWrapperFieldSetMapper.setTargetType(User.class);
    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

    return defaultLineMapper;
  }
}
