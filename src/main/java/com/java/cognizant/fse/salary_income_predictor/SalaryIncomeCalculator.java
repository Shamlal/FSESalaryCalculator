package com.java.cognizant.fse.salary_income_predictor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalaryIncomeCalculator {
	
	public static void main(String[] args) {
		SalaryIncomeCalculator main = new SalaryIncomeCalculator();
		
		String[] nk = new String[6];
		System.out.println("Please enter the input parameters");
		System.out.println("1. Starting Salary");
		System.out.println("2. Increment Percentage");
		System.out.println("3. Increment Frequency :1 - Annually, 2 - Half-Yearly,  4 - Quarterly");
		System.out.println("4. Deduction Percentage");
		System.out.println("5. Deduction Frequency :1 - Annually, 2 - Half-Yearly,  4 - Quarterly");
		System.out.println("6. Prediction Years");
		Scanner scanner = new Scanner(System.in);
        nk = scanner.nextLine().split(" ");
        
        scanner.close();
        if(validateInputs(nk[0], nk[1], nk[2], nk[3], nk[4], nk[5]) > 0) {
        	System.out.println("Please try again");
        	System.exit(0);
        }
		int startingSalary = Integer.parseInt(nk[0]);
        double incrementPercent = Double.parseDouble(nk[1]);
        int incrementFreq = Integer.parseInt(nk[2]);
        double deductionPercent = Double.parseDouble(nk[3]);
        int deductionsFreq = Integer.parseInt(nk[4]);
        int predYears = Integer.parseInt(nk[5]);
        
        calculateTheSalary(startingSalary, incrementPercent, incrementFreq, deductionPercent, deductionsFreq, predYears);
	}

	private static int validateInputs(String startingSalary, String incrementPercent, String incrementFreq, 
			String deductionPercent, String deductionsFreq,	String predYears) {
		
		return checkValue(startingSalary, 1, "Salary") + checkValue(incrementPercent, 0, "Increment Percent") + 
				checkValue(deductionPercent, 0, "Decrement Percent") + checkValue(incrementFreq, 1, "Increment Frequency")
				+ checkValue(deductionsFreq, 1, "Decrement Frequency");
	}
	
	private static int checkValue(String inputValue, int lowerLimit ,String nameOfValue) {
		int result = 0;
		try {
			Float value  = Float.parseFloat(inputValue);
			if((value < lowerLimit)) {
				throw new Exception(nameOfValue + " less than "+lowerLimit);
			}
		} catch (Exception e) {
			System.out.println("Incorrect "+nameOfValue);
			result = 1;
		}
		return result;
	}

	private static void calculateTheSalary(double startingSalary, double incrementPercent, int incrementFreq,
			double deductionPercent, int deductionsFreq, int predYears) {
		
		// initialize incrementReport, deductionReport, predictionReport
		List<List<String>> incrementReport = new ArrayList();
		List<List<String>> deductionReport = new ArrayList();
		List<List<String>> predictionReport = new ArrayList();
		
		double newSalary = startingSalary;
		
		for (int i = 0; i < predYears; i++) {
			
			double increment = rounding(startingSalary * (incrementPercent/100) * incrementFreq);
			prepareIncreDecreReportsRow(incrementReport, startingSalary, incrementFreq, incrementPercent, increment);
			
			double deduction = rounding(startingSalary * (deductionPercent/100) * deductionsFreq);
			prepareIncreDecreReportsRow(deductionReport, startingSalary, deductionsFreq, deductionPercent, deduction);

			newSalary = rounding(startingSalary + increment - deduction);//(BODMAS)
			prepareIncreDecreReportsRow(predictionReport, startingSalary, increment, deduction, newSalary);
			
			startingSalary = newSalary;
		}
		printReports(incrementReport, deductionReport, predictionReport);
	}
	
	private static double rounding(double valueToRound) {
		return Math.round((valueToRound*100.0)/100.0);
	}

	private static void prepareIncreDecreReportsRow(List<List<String>> report,double newSalary,double freq,double percent,double amount) {
		List<String> row = new ArrayList<String>();
		row.add(Double.toString(newSalary));
		row.add(Double.toString(freq));
		row.add(Double.toString(percent));
		row.add(Double.toString(amount));
		report.add(row);
	}
	
	private static void printReports(List<List<String>> incrementReport, List<List<String>> deductionReport ,List<List<String>> predictionReport ) {
		System.out.println("Increment Report");
		printSingleReport(incrementReport);
		System.out.println("Deduction Report");
		printSingleReport(deductionReport);
		System.out.println("Prediction");
		printSingleReport(predictionReport);
	}

	private static void printSingleReport(List<List<String>> report) {
		for (int i = 0; i < report.size(); i++) {
			List<String> row = report.get(i);
			System.out.print(getCorrectedStringValue(Integer.toString(i+1)));
			for (String value : row) {
				System.out.print(getCorrectedStringValue(value));
			}
			System.out.println();
		}
	}

	private static String getCorrectedStringValue(String value) {
		return "  "+ value + "  ";
	}

}
