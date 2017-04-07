package data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.log4j.Logger;
import web.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XMLParser {
    public static final Logger LOG = Logger.getLogger(XMLParser.class);
    XStream xStream = new XStream(new DomDriver("UTF-8"));
    Map<String, Currency> currencies = new TreeMap();

    public List getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        this.currencies = parse(new Request().getExpectedInfo());

        this.currencies.forEach((key, obj) -> {
            Currency currency = obj;
            currencies.add(new Currency(key, currency.getNumCode(), currency.getCharCode(), currency.getNominal(), currency.getValue()));
        });

        return currencies;
    }

    private Map parse(String file) {
        LOG.info("Prepare for parsing XML file");
        xStream.alias("ValCurs", Unit[].class);
        xStream.processAnnotations(Unit.class);

        LOG.info("Parsing...");
        Unit[] units = null;
        units = (Unit[]) xStream.fromXML(file);

        LOG.info("Parsing complete");
        LOG.info("Return unsorted expected content");
        return getCurrencies(units);
    }

    private Map getCurrencies(Unit[] units) {
        LOG.info("Sorting expected content");
        for (int i = 0; i < units.length; i++) {
            currencies.put(units[i].name,
                    new Currency(units[i].name,
                            Integer.parseInt(units[i].numCode),
                            units[i].charCode,
                            Integer.parseInt(units[i].nominal),
                            Double.parseDouble(units[i].value)));
        }

        LOG.info("Return sorted content");
        return currencies;
    }

    @XStreamAlias("Valute")
    private class Unit {
        @XStreamAlias("Name")
        String name;

        @XStreamAlias("NumCode")
        String numCode;

        @XStreamAlias("CharCode")
        String charCode;

        @XStreamAlias("Nominal")
        String nominal;

        @XStreamAlias("Value")
        String value;

        @Override
        public String toString() {
            return name + " " + numCode + " " + charCode + " " + nominal + " " + value;
        }
    }
}