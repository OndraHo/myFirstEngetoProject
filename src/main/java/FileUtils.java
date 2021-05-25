package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author ondrej.hosek
 */
public class FileUtils {

	private ArrayList<Country> data = new ArrayList<>();

	private static final String DELIMITER = "\t";

	public ArrayList<Country> scanner(String fileLocation) {
		try (Scanner s = new Scanner(new BufferedReader(new FileReader(fileLocation)))) {
			while (s.hasNext()) {
				data.add(Country.parseText(s.nextLine()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}