package team.nero.poi.relative;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class RelativeCellImpl implements RelativeCell {
    private RelativeBook relativeBook;
    private RelativeSheet relativeSheet;
    private Cell cell;

    private int columnNumber;
    private int rowNumber;

    RelativeCellImpl(RelativeBook relativeBook, RelativeSheet relativeSheet,
                               Cell cell, int columnNumber, int rowNumber) {
        this.relativeBook = relativeBook;
        this.relativeSheet = relativeSheet;
        this.cell = cell;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    @Override
    public void setValue(String value) {
        this.cell.setCellValue(value);
    }

    @Override
    public CellStyle getStyle() {
        return cell.getCellStyle();
    }

    @Override
    public void setStyle(CellStyle style) {
        cell.setCellStyle(style);
    }

    @Override
    public CellStyle createNewStyleAndGet() {
        return this.setStyleAndGet(toString());
    }

    @Override
    public CellStyle setStyleAndGet(String styleName) {
        CellStyle cellStyle = relativeBook.getStyle(styleName);
        cell.setCellStyle(cellStyle);
        return cellStyle;
    }

    @Override
    public RelativeCell next(Direction direction) {
        return next(direction, 1);
    }

    @Override
    public RelativeCell next(Direction direction, int distance) {
        int row = rowNumber + direction.getRow() * distance;
        int col = columnNumber + direction.getCol() * distance;

        return relativeSheet.createCell(row, col);
    }
}
