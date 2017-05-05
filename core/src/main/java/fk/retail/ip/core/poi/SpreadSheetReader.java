package fk.retail.ip.core.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fk.retail.ip.core.enums.CellType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author pragalathan.m
 */
public class SpreadSheetReader {

    //    private final DecimalFormat formatter = new DecimalFormat("#.###");
    public List<Map<String, Object>> read(InputStream xlsxFile) throws InvalidFormatException, IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        try (OPCPackage pkg = OPCPackage.open(xlsxFile)) {

            XSSFWorkbook wb = new XSSFWorkbook(pkg);
            Sheet sheet = wb.getSheetAt(0);
//            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

            int maxRows = sheet.getLastRowNum() + 1;
            List<String> headers = new ArrayList<>();
            Row headerRow = sheet.getRow(0);
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                Cell cell = headerRow.getCell(c);
                if (cell == null) {
                    continue;
                }
                headers.add(cell.getStringCellValue());
            }

            for (int r = 1; r < maxRows; r++) {
                Map<String, Object> values = new HashMap<>();
                Row row = sheet.getRow(r);
                boolean blankRow = true;
                for (int c = 0; c < headers.size(); c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        // blank cells at the end of the row will be treated as null
                        continue;
                    }
                    DataFormatter formatter = new DataFormatter();
                    String value = formatter.formatCellValue(cell);
                    value = removeUnnecessaryCharacters(value);

                    if (CellType.getType(headers.get(c)) == 2) {
                        try {
                            values.put(headers.get(c), Double.parseDouble(value));
                        } catch (NumberFormatException ex) {
                            values.put(headers.get(c), cell.getStringCellValue());
                        }

                    } else if(CellType.getType(headers.get(c)) == 1) {
                        try {
                            Integer intValue = Integer.parseInt(value);
                            values.put(headers.get(c), intValue);
                        } catch (NumberFormatException e) {
                            try {
                                Double doubleValue = Double.parseDouble(value);
                                values.put(headers.get(c), doubleValue);
                            } catch (NumberFormatException ex) {
                                values.put(headers.get(c), value);
                            }
                        }

                    } else {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            try {
                                values.put(headers.get(c), Long.parseLong(value));
                            } catch (NumberFormatException ex) {
                                values.put(headers.get(c), Double.parseDouble(value));
                            }
                        } else {
                            values.put(headers.get(c), cell.getStringCellValue());
                        }
                    }

                    if (values.get(headers.get(c)) instanceof String) {
                        String cellValue = values.get(headers.get(c)).toString();
                        if (cellValue.isEmpty()) {
                            values.put(headers.get(c), null);
                        }
                    }

                    if (blankRow) {
                        if (values.get(headers.get(c)) instanceof String) {
                            blankRow = ((String) values.get(headers.get(c))).isEmpty();
                        } else {
                            blankRow = false;
                        }
                    }
                }
                if (!blankRow) {
                    rows.add(values);
//                    writer.printRecord(values); // write to out file
                }
            }
        }
        return rows;
    }

    public String removeUnnecessaryCharacters(String value) {
        value = value.trim();
        return value.replace("\u00A0", "");
    }
}
