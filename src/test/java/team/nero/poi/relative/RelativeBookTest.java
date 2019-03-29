package team.nero.poi.relative;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class RelativeBookTest {
    @Test
    public void init() throws IOException {
        RelativeBook book = new RelativeBook("test");
        RelativeSheet sheet = book.getSheet("sheet1");

        RelativeCell cell = sheet.getPivotCell("B", 3);
        cell.setValue("1000");

        RelativeCell cell1 = cell.next(Direction.RIGHT);
        cell1.setValue("RIGHT");
        CellStyle style = cell1.createNewStyleAndGet();
        style.setAlignment(HorizontalAlignment.RIGHT);

        cell1 = cell.next(Direction.LEFT);
        cell1.setValue("LEFT");

        cell1 = cell.next(Direction.TOP);
        cell1.setValue("TOP");

        cell1 = cell.next(Direction.BOTTOM);
        cell1.setValue("BOTTOM");

        cell1 = cell.next(Direction.RIGHT_TOP);
        cell1.setValue("RIGHT_TOP");

        cell1 = cell.next(Direction.RIGHT_BOTTOM);
        cell1.setValue("RIGHT_BOTTOM");

        cell1 = cell.next(Direction.LEFT_TOP);
        cell1.setValue("LEFT_TOP");

        cell1 = cell.next(Direction.LEFT_BOTTOM);
        cell1.setValue("LEFT_BOTTOM");

        cell1 = cell.next(Direction.RIGHT, 3);
        cell1.setValue("RIGHT * 3");

        book.toFile(new File("out"));
    }
}
