package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * @author ondrej.hosek
 */

public class FileUtils {
	public static final String FILTER_RESULTS = "filter results";
	public static final String CUSTOM_FILE_NAME = "create custom file";
	public static final int VAT_RATE_20 = 20;

	private static final String OUTPUT_FILE_LOCATION_PREFIX = "vat-over-";
	private static final String OUTPUT_FILE_LOCATION_SUFFIX = ".txt";
	private static final String OUTPUT_FILE_LOCATION = "vat-over-20.txt";

	static final String INPUT_FILE_LOCATION = "vat-eu.csv";
	static final Logger logger = Logger.getLogger(FileUtils.class);


	private ArrayList<Country> data = new ArrayList<>();

	public List<Country> scanner(String fileLocation) {
		try (Scanner s = new Scanner(new BufferedReader(new FileReader(fileLocation)))) {
			while (s.hasNext()) {
				data.add(Country.parseText(s.nextLine()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	protected Integer scanFromKeyboard(String reason) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("You want to " + reason + ", please enter the VAT rate: ");
		String inputFromKeyboard = keyboard.nextLine();
		Integer intFromKeyboard;
		if (inputFromKeyboard.isEmpty()) {
			return VAT_RATE_20;
		}
		try {
			intFromKeyboard = Integer.parseInt(inputFromKeyboard);
			return intFromKeyboard;
		} catch (NumberFormatException e) {
			logger.debug("Entered value cannot be parsed as a number. You entered: " + inputFromKeyboard);
			logger.debug("Default value " + VAT_RATE_20 + " will be used.");
		}
		return VAT_RATE_20;
	}

	private static String createCustomFilename(Integer integer) {
		String customFilename = OUTPUT_FILE_LOCATION_PREFIX + integer.toString() + OUTPUT_FILE_LOCATION_SUFFIX;
		return customFilename;
	}


	void vatOver20fileCreation(final List<Country> countries) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE_LOCATION)))) {
			for (Country country : countries) {
				if (country.getFullVat() > VAT_RATE_20) {
					writer.println(country.printInfo());
				}
			}
		} catch (IOException e) {
			System.err.println("Error during writing to file: " + OUTPUT_FILE_LOCATION);
		}
	}

	protected void customVatfileCreation(final List<Country> countries) {
		int i = scanFromKeyboard(CUSTOM_FILE_NAME);
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(createCustomFilename(i))))) {
			for (Country country : countries) {
				if (country.getFullVat() > i) {
					writer.println(country.printInfo());
				}
			}
		} catch (IOException e) {
			logger.error("Error during writing to file: " + OUTPUT_FILE_LOCATION);
		}
	}
}