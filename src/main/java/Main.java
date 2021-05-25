package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.logging.Logger;

/**
 * @author ondrej.hosek
 */
public class Main {
	private static final String INPUT_FILE_LOCATION = "vat-eu.csv";
	private static final String OUTPUT_FILE_LOCATION = "vat-over-20.txt";
	private static final String OUTPUT_FILE_LOCATION_PREFIX = "vat-over-";
	private static final String OUTPUT_FILE_LOCATION_SUFFIX = ".txt";
	private static final String SEPARATOR = "====================";
	private static final int VAT_RATE_20 = 20;
//	final static Logger LOGGER = Logger.getLogger(classname.class);

	public static void main(String[] args) {
		FileUtils file = new FileUtils();
		ArrayList<Country> countries = file.scanner(INPUT_FILE_LOCATION);
		countries.removeAll(Collections.singleton(null));

//		Vypiš seznam všech států a u každého uveď základní sazbu daně z přidané hodnoty ve formátu:
		for (Country country : countries) {
			System.out.println(country.printInfo());
		}

		for (Country country : countries) {
			//	Vypište ve stejném formátu pouze státy, které mají základní sazbu daně z přidané hodnoty vyšší než 20 % a přitom nepoužívají speciální sazbu daně.
			if ((country.getFullVat() > VAT_RATE_20) && (!country.isUseSpecialRate())) {
				System.out.println(country.printInfo());
			}
		}

		//	Výpis seřaďte podle výše základní sazby DPH/VAT sestupně (nejprve státy s nejvyšší sazbou).
		Collections.sort(countries, Comparator.comparing(Country::getFullVat).reversed());
		for (Country country : countries) {
			System.out.println(country.printInfo());
		}
		// Pod výpis doplňte řádek s rovnítky pro oddělení a poté seznam zkratek států, které ve výpisu nefigurují.
		System.out.println(SEPARATOR);
		System.out.println("Sazba VAT 20 % nebo nižší a nebo používají speciální sazbu: " + getSpecialCountries(countries));

		//	Výsledný výpis kromě zapište i do souboru s názvem vat-over-20.txt, který uložíte do stejné složky, ve které byl vstupní soubor.
		vatOver20fileCreation(countries);

		//	Doplňte možnost, aby uživatel z klávesnice zadal výši sazby DPH/VAT, podle které se má filtrovat.
		scanFromKeyboard();
		//	Upravte název výstupního souboru tak, aby reflektoval zadanou sazbu daně.
		createCustomFilename(scanFromKeyboard());
		customVatfileCreation(countries);
	}

	private static void vatOver20fileCreation(final ArrayList<Country> countries) {
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

	private static void customVatfileCreation(final ArrayList<Country> countries) {
		int i = scanFromKeyboard();
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(createCustomFilename(i))))) {
			for (Country country : countries) {
				if (country.getFullVat() > i) {
					writer.println(country.printInfo());
				}
			}
		} catch (IOException e) {
			System.err.println("Error during writing to file: " + OUTPUT_FILE_LOCATION);
		}
	}

	private static String createCustomFilename(Integer integer) {
		String customFilename = OUTPUT_FILE_LOCATION_PREFIX + integer.toString() + OUTPUT_FILE_LOCATION_SUFFIX;
		System.out.println(customFilename);
		return customFilename;


	}

	private static String getSpecialCountries(ArrayList<Country> countries) {
		StringJoiner joiner = new StringJoiner(", ");
		countries.stream()
				.filter(country -> (country.getFullVat() <= VAT_RATE_20) || (country.isUseSpecialRate()))
				.forEach(country -> joiner.add(country.getAbbreviation()));
		return joiner.toString();
	}

	private static Integer scanFromKeyboard() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter the VAT rate: ");
		String inputFromKeyboard = keyboard.nextLine();
		Integer intFromKeyboard;
		if (inputFromKeyboard.isEmpty()) {
			return VAT_RATE_20;
		}
		try {
			intFromKeyboard = Integer.parseInt(inputFromKeyboard);
			return intFromKeyboard;
		} catch (NumberFormatException e) {
			System.err.println("Entered value cannot be parsed as a number. You entered: " + inputFromKeyboard);
			System.out.println("Default value " + VAT_RATE_20 + " will be used.");
		}
		return VAT_RATE_20;
	}

}