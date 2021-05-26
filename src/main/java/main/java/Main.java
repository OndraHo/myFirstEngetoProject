package main.java;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static main.java.FileUtils.FILTER_RESULTS;
import static main.java.FileUtils.VAT_RATE_20;

public class Main {
	private static final String SEPARATOR = "====================";

	public static void main(String[] args) {
		FileUtils file = new FileUtils();
		CountryList countryList = new CountryList();
		List<Country> countries = countryList.getNotNullCountries(file);

//		Vypiš seznam všech států a u každého uveď základní sazbu daně z přidané hodnoty ve formátu
		for (Country country : countries) {
			System.out.println(country.printInfo());
		}

		//	Vypište ve stejném formátu pouze státy, které mají základní sazbu daně z přidané hodnoty vyšší než 20 % a přitom nepoužívají speciální sazbu daně.
		for (Country country : countries) {
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
		System.out.println("Sazba VAT 20 % nebo nižší a nebo používají speciální sazbu: " + countryList.getSpecialCountries(countries));

		//	Výsledný výpis kromě zapište i do souboru s názvem vat-over-20.txt, který uložíte do stejné složky, ve které byl vstupní soubor.
		file.vatOver20fileCreation(countries);

		//	Doplňte možnost, aby uživatel z klávesnice zadal výši sazby DPH/VAT, podle které se má filtrovat.
		file.scanFromKeyboard(FILTER_RESULTS);
		//	Upravte název výstupního souboru tak, aby reflektoval zadanou sazbu daně.
		file.customVatfileCreation(countries);
	}

}