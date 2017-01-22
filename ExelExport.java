import db.DBserv;
import db.DataSet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ihor on 21.01.17.
 */
public class ExelExport {

    public ExelExport() {

        String filename = "matches.xls";
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
        //File home = new File(System.getProperty("user.home"));
        File file = new File(absoluteFilePath);

        try {

            checkFile(file);
            FileInputStream fis = new FileInputStream(file);
            Workbook book = new HSSFWorkbook(fis);
            book.removeSheetAt(0);
            Sheet sheet = book.createSheet();
            write(sheet, file, book);
            fis.close();

        } catch (IOException e) {
            System.out.println("IO EXP");
        } catch (SQLException e) {}
        System.out.println("use");
    }

    public void checkFile(File file) {

        try {
            if (!file.exists()) {
                Workbook wb = new HSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream("matches.xls");
                wb.createSheet();
                wb.write(fileOut);
                wb.close();
                fileOut.close();
                System.out.println("Created file");
            }
        } catch (IOException e) {}
    }

    public void write(Sheet sheet, File file, Workbook book) throws SQLException{

        try {

            Row row = sheet.createRow(0);

            Cell idmatches = row.createCell(0);
            Cell name = row.createCell(1);
            Cell count1 = row.createCell(2);
            Cell count2 = row.createCell(3);
            Cell time = row.createCell(4);
            Cell coef1 = row.createCell(5);
            Cell coef2 = row.createCell(6);
            Cell coef3 = row.createCell(7);
            Cell url = row.createCell(8);

            idmatches.setCellValue("idmatches");
            name.setCellValue("name");
            count1.setCellValue("count1");
            count2.setCellValue("count2");
            time.setCellValue("time");
            coef1.setCellValue("coef1");
            coef2.setCellValue("coef2");
            coef3.setCellValue("coef3");
            url.setCellValue("url");

            DBserv dBserv = new DBserv();
            ArrayList<DataSet> alls = dBserv.getAll();
            int rownum = 1;

            for (DataSet set : alls) {
                Row row1 = sheet.createRow(rownum++);

                Cell idmatches1 = row1.createCell(0);
                idmatches1.setCellValue(set.getId());

                Cell name1 = row1.createCell(1);
                name1.setCellValue(set.getName());

                Cell count11 = row1.createCell(2);
                count11.setCellValue(set.getCount1());

                Cell count21 = row1.createCell(3);
                count21.setCellValue(set.getCount2());

                Cell time1 = row1.createCell(4);
                time1.setCellValue(set.getTime());

                Cell coef11 = row1.createCell(5);
                coef11.setCellValue(set.getCoef1());

                Cell coef21 = row1.createCell(6);
                coef21.setCellValue(set.getCoef2());

                Cell coef31 = row1.createCell(7);
                coef31.setCellValue(set.getCoef3());

                Cell url1 = row1.createCell(8);
                url1.setCellValue(set.getUrl());
            }

            FileOutputStream os = new FileOutputStream(file);
            book.write(os);
            os.close();
            book.close();
            dBserv.closeFactory();
        } catch (IOException e) {}
    }
}
