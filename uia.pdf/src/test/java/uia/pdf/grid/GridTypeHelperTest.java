package uia.pdf.grid;

import java.io.File;

import org.junit.Test;

import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.LayoutType;

public class GridTypeHelperTest {

    @Test
    public void testSample() throws Exception {
    	LayoutType layout = GridTypeHelper.load(new File(GridTypeHelperTest.class.getResource("sample.xml").toURI()));
        for (GridType table : layout.getGrid()) {
            System.out.println(table.getName());
            System.out.println("    fontSize: " + table.getFontSize());
            for (ColumnType column : table.getColumns().getColumn()) {
                System.out.println(String.format("  [%-10s] width:%s",
                        column.getText(),
                        column.getWidth()));
            }
        }
    }
}
