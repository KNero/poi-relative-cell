package team.nero.poi.relative;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class RelativeSheet {
    private RelativeBook relativeBook;
    private String name;
    private Sheet sheet;

    RelativeSheet(RelativeBook relativeBook, String name, Sheet sheet) {
        this.relativeBook = relativeBook;
        this.name = name;
        this.sheet = sheet;
    }

    public String getName() {
        return name;
    }

    /**
     * create cell
     * @param az excel column A ~ Z
     * @param num row number 1 ~ 65535
     *
     */
    public RelativeCell getPivotCell(String az, int num) {
        if (num < 1) {
            throw new RuntimeException("cell row number range: 1 ~ 65535");
        }

        int columnNumber = columnNameToNumber(az);
        int rowNumber = num - 1;

        return createCell(rowNumber, columnNumber);
    }

    RelativeCell createCell(int rowNumber, int columnNumber) {
        if (rowNumber < 0) {
            throw new RuntimeException("row is must bigger than 0.");
        }

        if (columnNumber < 0) {
            throw new RuntimeException("column is must bigger than 0.");
        }

        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }

        Cell cell = row.getCell(columnNumber);
        if (cell == null) {
            cell = row.createCell(columnNumber);
        }

        return new RelativeCellImpl(relativeBook, this, cell, columnNumber, rowNumber);
    }

    /**
     * changed excel column A ~ Z to 0 ~ 25
     * @param az excel column A ~ Z
     * @return 0 ~ 25
     */
    private static int columnNameToNumber(String az) {
        final String col = az.toLowerCase();
        return col.charAt(0) - 'a';
    }
}
