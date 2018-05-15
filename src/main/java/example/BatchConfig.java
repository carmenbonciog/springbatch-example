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
            //.includedFields(new Integer[] {0,1,2,3,4,5,6,7})
            .names(new String[]{"street","city","zip","state","beds","baths","sqft","accomodationType","holidayDate","price","latitude","longitude"})
            .linesToSkip(1)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<BookingExtract>() {{
                setTargetType(BookingExtract.class);
               setStrict(false);               
            }})
            .build();
        		
    }
/*   @Bean
    public DelimitedLineTokenizer testDelimit () {
    	
    	return new DelimiteBuilder<>
    }

    @Bean
    public JdbcBatchItemWriter<BookingExtract> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookingExtract>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO BookingExtract (street,city,zip,state,beds,baths,sqft,accomodationType,holidayDate,price,latitude,longitude) VALUES (:street,:city,:zip,:state,:beds,:baths,:sqft,:accomodationType,:holidayDate,:price,:latitude,:longitude)")
            .dataSource(dataSource)
            .build();
    }*/
    
    
    @Bean
    public JdbcBatchItemWriter<BookingExtract> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookingExtract>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO BookingTest1 (street,city,zip,state,beds,baths,sqft) VALUES (:street,:city,:zip,:state,:beds,:baths,:sqft)")
            .dataSource(dataSource)
            .build();
    }
    
    @Bean
    public JdbcBatchItemWriter<BookingExtract> writer1(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookingExtract>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO BookingTest2 (accomodationType,holidayDate,price,latitude,longitude) VALUES (:accomodationType,:holidayDate,:price,:latitude,:longitude)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .next(step2)
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
    
    @Bean
    public Step step2(JdbcBatchItemWriter<BookingExtract> writer1) {
        return stepBuilderFactory.get("step2")
            .<BookingExtract, BookingExtract> chunk(10)
            .reader(reader())
            .writer(writer1)
            .build();
    }
    // end::jobstep[]
}