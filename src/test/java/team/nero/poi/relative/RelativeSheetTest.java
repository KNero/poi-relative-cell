package team.nero.poi.relative;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class RelativeSheetTest {
    @Test
    public void textColumnNameToNumber() throws Exception {
        int num = Whitebox.invokeMethod(RelativeSheet.class, "columnNameToNumber", "A");
        Assert.assertEquals(0, num);

        num = Whitebox.invokeMethod(RelativeSheet.class, "columnNameToNumber", "a");
        Assert.assertEquals(0, num);

        num = Whitebox.invokeMethod(RelativeSheet.class, "columnNameToNumber", "Z");
        Assert.assertEquals(25, num);
    }
}
