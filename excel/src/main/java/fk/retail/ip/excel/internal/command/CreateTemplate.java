package fk.retail.ip.excel.internal.command;

import fk.retail.ip.excel.internal.command.model.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 18/05/17.
 */

public interface CreateTemplate {

    default SXSSFWorkbook create(List<Column> columnList) throws IOException {
        SXSSFWorkbook wbss;
        wbss = new SXSSFWorkbook(100);
        Sheet spreadSheet = wbss.createSheet();

        Row row;
        List<String> columnHeaders = new ArrayList<>();
        columnList.forEach(column -> {
            columnHeaders.add(column.getName());
        });

        int rowid = 0;
        row = spreadSheet.createRow(rowid);
        int cellid = 0;
        for (String key : columnHeaders) {
            Cell cell = row.createCell(cellid++);
            cell.setCellValue(key);
        }
        //try {
            //OutputStream outputStream;
            //StreamingOutput streamingOutput = wbss.write(outputStream);
            //FileOutputStream fs = new FileOutputStream("abc.xlsx");
//            StreamingOutput streamingOutput = (OutputStream out1)  -> {
//                wbss.write(out1);
//                out1.flush();
//            };
//        flushOutputStream outputStream = (OutputStream out) -> {
//          wbss.write(out);
//        };

//        OutputStream outputStream1 = new FileOutputStream("tempfile.xlsx");
//        wbss.write(outputStream1);
        //wbss.dispose();
        //return outputStream1;
        return wbss;

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //wbss.write(baos);
        //outputStream.flush();

//        StreamingOutput outputStream = (OutputStream out1) -> {
//            wbss.write(out1);
//        };


        //return outputStream;



//       StreamingOutput outputStream = (OutputStream out1) -> {
//            wbss.write(out1);
//            out1.flush();
//       };
//
//            wbss.dispose();
//            //workbook.write(fs);
//            return outputStream;

   // } catch(Exception e) {

    //}
    //return null;

    }
}
