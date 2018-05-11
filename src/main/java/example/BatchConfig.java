package example;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<BookingExtract> reader() {
        return new FlatFileItemReaderBuilder<BookingExtract>()
            .name("bookingExtractItemReader")
            .resource(new ClassPathResource("SpringBatchTest.csv"))
            .delimited()
            .names(new String[]{"street","city","zip","state","beds","baths","sqft","accomodationType","holidayDate","price","latitude","longitude"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<BookingExtract>() {{
                setTargetType(BookingExtract.class);
               setStrict(false);
            }})
            .build();
    }



    @Bean
    public JdbcBatchItemWriter<BookingExtract> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookingExtract>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO BookingExtract (street,city,zip,state,beds,baths,sqft,accomodationType,holidayDate,price,latitude,longitude) VALUES (:street,:city,:zip,:state,:beds,:baths,:sqft,:accomodationType,:holidayDate,:price,:latitude,:longitude)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<BookingExtract> writer) {
        return stepBuilderFactory.get("step1")
            .<BookingExtract, BookingExtract> chunk(10)
            .reader(reader())
            .writer(writer)
            .build();
    }
    // end::jobstep[]
}