package com.mytest.springbatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

import com.mytest.model.ExamResult;

public class ExamResultRowMapper implements RowMapper<ExamResult> {

	@Override
	public ExamResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExamResult result = new ExamResult();
		result.setUserName(rs.getString("username"));
		result.setExamDate(new LocalDate(rs.getDate("examDate")));
		result.setPercentage(rs.getDouble("percentage"));
		
		return result;
	}

}
