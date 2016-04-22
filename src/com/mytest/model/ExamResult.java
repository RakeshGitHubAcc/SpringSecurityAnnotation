package com.mytest.model;

import org.joda.time.LocalDate;

public class ExamResult {
	private String userName;
	private double percentage;
	private LocalDate examDate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	@Override
	public String toString() {
		return "ExamResult [User Name=" + userName + ", Exam Date=" + examDate
				+ ", Percentage=" + percentage + "]";
	}
}
