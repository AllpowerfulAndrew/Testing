import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {
    private XMLParser parser;

    public void create() {
        Workbook wbook = new HSSFWorkbook();
        Sheet sheet = wbook.createSheet("1");
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(1);

        try {
            FileOutputStream fos = new FileOutputStream("src/main/resources/Currencies.xls");
            wbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
