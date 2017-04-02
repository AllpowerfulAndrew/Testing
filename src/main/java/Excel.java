import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Class Excel.
 * Create XLS file with currencies.
 */
public class Excel {
    private Map currencies;

    public void create() {
        currencies = new XMLParser("data.xml").parse();

        Workbook wbook = new HSSFWorkbook();
        Sheet sheet = wbook.createSheet("Curs");
        Row[] row = new Row[currencies.size()];

        for (int i = 0; i < currencies.size(); i++) {
            row[i] = sheet.createRow(i);
        }
        final int[] i = {0, 0, 0, 0, 0};

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
            FileOutputStream fos = new FileOutputStream("src/main/resources/Currencies.xls");
            wbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
