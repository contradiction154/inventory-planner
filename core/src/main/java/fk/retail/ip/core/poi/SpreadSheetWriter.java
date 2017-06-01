package fk.retail.ip.core.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author pragalathan.m
 */
@Slf4j
public class SpreadSheetWriter {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss.S");

    public void populateTemplate(SXSSFWorkbook wbss, Map<String, Object> records, List<String> headers, int rownum) throws InvalidFormatException, IOException {
        //SXSSFWorkbook wbss;
        //List<String> headers = new ArrayList<>();
            //wbss = new SXSSFWorkbook(100);


//            Row headerRow = sheet.getRow(0);
//            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
//                Cell cell = headerRow.getCell(c);
//                if (cell == null) {
//                    continue;
//                }
//                headers.add(cell.getStringCellValue());
//            }


            // read csv and write to spreadsheet
            Sheet sheet = wbss.getSheetAt(0);
//            for (int r = 0; r < records.size(); r++) {
//                Map<String, Object> record = records;
//                Row row = sheet.createRow(r + 1);
//                for (int c = 0; c < headers.size(); c++) {
//                    if (headers.get(c).trim().isEmpty()) {
//                        break;
//                    }
//                    Object value = record.get(headers.get(c));
//                    Cell cell = row.getCell(c, Row.CREATE_NULL_AS_BLANK);
//                    setCellValue(value, cell);
//
//                    // applyCellStyle(wb, cell, headers.get(c));
//                }
//                out.flush();
//            }


            Row row = sheet.createRow(rownum + 1);
            for (int c = 0; c < headers.size(); c++) {
                if (headers.get(c).trim().isEmpty()) {
                    break;
                }
                Object value = records.get(headers.get(c));
                Cell cell = row.getCell(c, Row.CREATE_NULL_AS_BLANK);
                setCellValue(value, cell);

                // applyCellStyle(wb, cell, headers.get(c));
            }
            //out.flush();


//        for (int r = 0; r < records.size(); r++) {
//            Map<String, Object> record = records.get(r);
//            Row row = sheet.createRow(r + 1);
//            for (int c = 0; c < headers.size(); c++) {
//                if (headers.get(c).trim().isEmpty()) {
//                    break;
//                }
//                Object value = record.get(headers.get(c));
//                Cell cell = row.getCell(c, Row.CREATE_NULL_AS_BLANK);
//                setCellValue(value, cell);
//
//                // applyCellStyle(wb, cell, headers.get(c));
//            }
//            out.flush();
//        }

            //wbss.write(out);
            //out.close();
            //wbss.dispose();
        }

//    }


    private void setCellValue(Object value, Cell cell) {
        if (value == null) {
            return;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellValue(NumberUtils.toDouble(value.toString()));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    protected void applyCellStyle(SXSSFWorkbook wb, Cell cell, String columnName) {
        CellStyle editableStyle = wb.createCellStyle();
        editableStyle.setLocked(false);
        cell.setCellStyle(editableStyle);
    }
}