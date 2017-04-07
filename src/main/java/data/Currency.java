package data;

public class Currency {
    private final int numCode;
    private final String charCode;
    private final int nominal;
    private final String name;
    private final double value;

    public Currency(String name, int numCode, String charCode, int nominal, double value) {
        this.name = name;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.value = value;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getNominal() + " " + getNumCode() + " " + getValue() + " " + getCharCode() + " " + getName();
    }
}