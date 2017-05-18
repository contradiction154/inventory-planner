package fk.retail.ip.excel.internal.command;

import fk.retail.ip.excel.internal.command.model.Column;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by agarwal.vaibhav on 18/05/17.
 */

public class CreateTemplate {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss.S");

    public static StreamingOutput create(List<Column> columnList) throws IOException, InvalidFormatException {
        //Path tempFile = Files.createTempFile(null, format.format(new Date()));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadSheet = workbook.createSheet("test");
            XSSFRow row;
            Map<String, Object[]> excelValueMap = new HashMap();
            excelValueMap.put("1", new Object[]{"requirement id", "name"});
            Set<String> keyset = excelValueMap.keySet();
            int rowid = 0;
            for (String key : keyset) {
                row = spreadSheet.createRow(rowid++);
                Object[] objectArr = excelValueMap.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }
            //try {
                //FileOutputStream fs = new FileOutputStream("abc.xlsx");
                StreamingOutput streamingOutput = (OutputStream out1)  -> {
                    workbook.write(out1);
                };
                //workbook.write(fs);
                return streamingOutput;

        } catch(Exception e) {

        }
        return null;

    }
}
