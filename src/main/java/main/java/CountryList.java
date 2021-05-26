package main.java;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static main.java.FileUtils.INPUT_FILE_LOCATION;
import static main.java.FileUtils.VAT_RATE_20;

/**
 * @author ondrej.hosek
 */
public class CountryList {

	protected String getSpecialCountries(List<Country> countries) {
		StringJoiner joiner = new StringJoiner(", ");
		countries
				.stream()
				.filter(country -> (country.getFullVat() <= VAT_RATE_20) || (country.isUseSpecialRate()))
				.forEach(country -> joiner.add(country.getAbbreviation()));
		return joiner.toString();
	}

	protected List<Country> getNotNullCountries(final FileUtils file) {
		List<Country> countries = file.scanner(INPUT_FILE_LOCATION);
		countries.removeAll(Collections.singleton(null));
		return countries;
	}

}