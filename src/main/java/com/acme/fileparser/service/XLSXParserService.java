package com.acme.fileparser.service;

import com.acme.fileparser.FileParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
public class XLSXParserService implements FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XLSXParserService.class);

    @Override
    public List<Map<String, String>> parse(MultipartFile file) {

        if (file == null) {
            throw new RuntimeException("Missing input file");
        }

        List<Map<String, String>> tableData = new ArrayList<>();

        try {
            Iterator<Row> rowIterator = getFirstSheetRowIterator(file);
            List<String> headerColumns = getHeaderColumns(rowIterator);
            fillTableData(rowIterator, headerColumns, tableData);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        LOGGER.info("File {} successfully parsed.", file.getOriginalFilename());
        LOGGER.debug("Parsed {} rows", tableData.size());
        return tableData;
    }

    public Iterator<Row> getFirstSheetRowIterator(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        return sheet.iterator();
    }

    public List<String> getHeaderColumns(Iterator<Row> rowIterator) {
        ArrayList<String> headerColumns = new ArrayList<>();
        Row headerRow = rowIterator.next();
        Iterator<Cell> headerCellIterator = headerRow.cellIterator();

        while (headerCellIterator.hasNext()) {
            Cell headerCell = headerCellIterator.next();
            String valueStr = headerCell.getStringCellValue();
            if (valueStr.trim().length() > 0) {
                headerColumns.add(valueStr);
            }
        }
        return headerColumns;
    }

    public void fillTableData(Iterator<Row> rowIterator, List<String> headerColumns, List<Map<String,
            String>> tableData) {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            int columnSeq = 0;
            Map<String, String> rowData = new HashMap<>();

            while (cellIterator.hasNext()) {
                if (columnSeq == headerColumns.size()) {
                    break;
                }
                Cell cell = cellIterator.next();
                String valueStr = "";
                switch (cell.getCellType()) {
                    case STRING:
                        valueStr = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        double valueNum = cell.getNumericCellValue();
                        valueStr = "" + valueNum;
                        break;
                    default:
                }
                if (valueStr.trim().length() == 0) {
                    break;
                }
                rowData.put(headerColumns.get(columnSeq), valueStr);
                columnSeq++;
            } //iterate row columns

            tableData.add(rowData);
        } //iterate row
    }
}
