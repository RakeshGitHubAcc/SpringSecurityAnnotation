package com.mytest.springbatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.joda.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.mytest.model.ExamResult;

@Configuration
@ComponentScan({ "com.mytest.springbatch" })
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	public DataSource dataSource;

	@Bean
	public JdbcCursorItemReader<ExamResult> reader() {
		JdbcCursorItemReader<ExamResult> reader = new JdbcCursorItemReader<ExamResult>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT username,examDate,percentage FROM exam_result");
		reader.setRowMapper(new RowMapper<ExamResult>() {
			@Override
			public ExamResult mapRow(ResultSet rs, int arg1)
					throws SQLException {
				ExamResult result = new ExamResult();
				result.setUserName(rs.getString("username"));
				result.setExamDate(new LocalDate(rs.getDate("examDate")));
				result.setPercentage(rs.getDouble("percentage"));
				return result;
			}
		});

		return reader;
	}

	@Bean
	public StaxEventItemWriter<ExamResult> writer()
			throws ClassNotFoundException {
		StaxEventItemWriter<ExamResult> writer = new StaxEventItemWriter<ExamResult>();
		writer.setResource(new FileSystemResource("file:xml/examResult1.xml"));
		writer.setRootTagName("UniversityExamResultList");
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(Class
				.forName("com.mytest.batch.model.ExamResult"));
		writer.setMarshaller(jaxb2Marshaller);

		return writer;
	}

	@Bean
	public JobExecutionListener listener() {
		return new ExamResultJobListener();
	}

	@Bean
	public Job examResultJob() throws ClassNotFoundException {
		return jobBuilderFactory.get("examResultJob")
				.incrementer(new RunIdIncrementer()).listener(listener())
				.flow(step1()).end().build();
	}

	@Bean
	public Step step1() throws ClassNotFoundException {
		return stepBuilderFactory.get("step1")
				.<ExamResult, ExamResult> chunk(10).reader(reader())
				.processor(new ItemProcessor<ExamResult, ExamResult>(){
					@Override
					public ExamResult process(ExamResult result) throws Exception {
						System.out.println("Processing result :" + result);
						if (result.getPercentage() < 75) {
							return null;
						}
						return result;
					}	
				}).writer(writer()).build();
	}

	@Bean
	public ExamResultItemProcessor processor() {
		return new ExamResultItemProcessor();
	}
}
