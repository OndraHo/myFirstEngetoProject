package main.java;

/**
 * @author ondrej.hosek
 */
public class Country {
	private String abbreviation;
	private String name;
	private int fullVat;
	private double lowVat;
	private boolean useSpecialRate;

	private static final String DELIMITER = "\t";

	public Country(final String abbreviation, final String name, final int fullVat, final double lowVat, final boolean useSpecialRate) {
		this.abbreviation = abbreviation;
		this.name = name;
		this.fullVat = fullVat;
		this.lowVat = lowVat;
		this.useSpecialRate = useSpecialRate;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getFullVat() {
		return fullVat;
	}

	public void setFullVat(final int fullVat) {
		this.fullVat = fullVat;
	}

	public double getLowVat() {
		return lowVat;
	}

	public void setLowVat(final double lowVat) {
		this.lowVat = lowVat;
	}

	public boolean isUseSpecialRate() {
		return useSpecialRate;
	}

	public void setUseSpecialRate(final boolean useSpecialRate) {
		this.useSpecialRate = useSpecialRate;
	}

	public String printInfo() {
		if (getName() != null && getAbbreviation() != null) {
			return (getName() + " (" + getAbbreviation() + "): " + getFullVat() + " %");
		} else {
			return "Can`t return info about country";
		}
	}

	public static Country parseText(String input) {
		String[] items = input.split(DELIMITER);
		try {
			String abbreviation = items[0];
			String name = items[1];
			int fullVat = Integer.parseInt(items[2]);
			double lowVat = Integer.parseInt(items[3]);
			boolean useSpecialRate = Boolean.parseBoolean(items[4]);

			return new Country(abbreviation, name, fullVat, lowVat, useSpecialRate);
		} catch (Exception e) {
			System.err.println("Can`t parse input " + input);
			System.err.println(e);
		}
		return null;
	}

}

