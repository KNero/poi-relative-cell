package team.nero.poi.relative;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * default xls file.
 */
public class RelativeBook {
    private String fileName;

    private Workbook workbook;
    private Map<String, RelativeSheet> sheets = new HashMap<>();
    private Map<String, CellStyle> styleMap = new HashMap<>();

    public RelativeBook(String fileName) {
        this(fileName, false);
    }

    private RelativeBook(String fileName, boolean isXlsx) {
        this.fileName = fileName;

        if (isXlsx) {
            workbook = new XSSFWorkbook();
            this.fileName =  fileName + ".xlsx";
        } else {
            workbook = new HSSFWorkbook();
            this.fileName = fileName + ".xls";
        }
    }

    /**
     * get work book's sheet.
     * if sheet is not exists created new sheet and return.
     * @param sheetName sheet name for find.
     */
    public RelativeSheet getSheet(String sheetName) {
        return sheets.computeIfAbsent(sheetName, name -> {
            RelativeSheet newSheet = new RelativeSheet(this, name, workbook.createSheet(name));
            sheets.put(sheetName, newSheet);
            return newSheet;
        });
    }

    /**
     * get work book's cell style.
     * if style is not exists created new cell style and return.
     * @param styleName style nick name for recycle.
     * @return {@link org.apache.poi.ss.usermodel.CellStyle}
     */
    public CellStyle getStyle(String styleName) {
        return styleMap.computeIfAbsent(styleName, name -> {
            CellStyle style = workbook.createCellStyle();
            styleMap.put(name, style);
            return style;
        });
    }

    public void toFile(File directory) throws IOException {
        File target = new File(directory, fileName);
        try (FileOutputStream out = new FileOutputStream(target)) {
            workbook.write(out);
        }
    }
}
