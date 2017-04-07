package data;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Class data.Excel.
 * Create XLS file with currencies.
 */
public class Excel {
    private static final Logger LOG = Logger.getLogger(Excel.class);
    private Map currencies;

    public Excel(Map currencies) {
        this.currencies = currencies;
    }

    private void create() {
        Workbook wbook = new HSSFWorkbook();
        Sheet sheet = wbook.createSheet("Curs");
        Row[] row = new Row[currencies.size()];
        final int[] i = {0, 0, 0, 0, 0};

        for (int j = 0; j < currencies.size(); j++) {
            row[j] = sheet.createRow(j);
        }

        currencies.forEach((key, obj) -> {
            row[(i[0]++)].createCell(0).setCellValue((String) key);
            Currency currency = (Currency) obj;
            row[(i[1]++)].createCell(1).setCellValue(currency.getNumCode());
            row[(i[2]++)].createCell(2).setCellValue(currency.getCharCode());
            row[(i[3]++)].createCell(3).setCellValue(currency.getNominal());
            row[(i[4]++)].createCell(4).setCellValue(currency.getValue());
        });

        write(wbook);
    }

    private void write(Workbook wbook) {
        try {
            LOG.info("Write dates to XLS file");
            FileOutputStream fos = new FileOutputStream("src/main/resources/currencies.xls");
            wbook.write(fos);
            fos.close();
        } catch (IOException e) {
            LOG.error("Writing error!", e);
        }
    }
}