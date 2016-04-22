package com.mytest.springbatch;

import org.springframework.batch.item.ItemProcessor;

import com.mytest.batch.model.ExamResult;

public class ExamResultItemProcessor implements
		ItemProcessor<ExamResult, ExamResult> {

	@Override
	public ExamResult process(ExamResult result) throws Exception {
		System.out.println("Processing result :" + result);

		/*
		 * Only return results which are equal or more than 60% for reading from
		 * csv file and to write in xml file
		 */
		/*
		 * if(result.getPercentage() < 60){ return null; }
		 */

		/*
		 * Only return results which are equal or more than 75% 
		 * for reading from xml file and to write in csv file
		 */
		if (result.getPercentage() < 75) {
			return null;
		}
		return result;
	}

}
