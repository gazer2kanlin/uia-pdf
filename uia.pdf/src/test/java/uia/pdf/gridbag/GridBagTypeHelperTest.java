package uia.pdf.gridbag;

import java.io.File;

import org.junit.Test;

import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.LayoutType;
import uia.pdf.gridbag.layout.RowType;

public class GridBagTypeHelperTest {

    @Test
    public void testSample0() throws Exception {
        LayoutType layout = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample0.xml").toURI()));
        print(layout);
    }
    
    @Test
    public void testSample1() throws Exception {
    	LayoutType layout = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample1.xml").toURI()));
        print(layout);
    }
    
    @Test
    public void testSample2() throws Exception {
    	LayoutType layout = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample2.xml").toURI()));
        print(layout);
    }
    
    @Test
    public void testSample3() throws Exception {
    	LayoutType layout = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample3.xml").toURI()));
        print(layout);
    }

    private void print(LayoutType layout) {
        for (GridBagType grid : layout.getGridbag()) {
            System.out.println(String.format("grid: width:%s height:%s bSize:%s",
                    grid.getWidth(),
                    grid.getHeight(),
                    grid.getBorderSize()));

            int cl = 1;
            System.out.println("  cols");
            for (ColumnType column : grid.getColumns().getColumn()) {
                System.out.println(String.format("    col[%s]: width:%s",
                        cl,
                        column.getWidth()));
                cl++;
            }

            int r = 1;
            for (RowType row : grid.getRows().getRow()) {
                System.out.println(String.format("  row[%s]: height:%s bSize:%s",
                        r,
                        row.getHeight(),
                        row.getBorderSize()));
                int ce = 1;
                for (CellType cell : row.getTextCellOrBindCellOrImageCell()) {
                    System.out.println(String.format("    cell[%s] cSpan:%s rSpan:%s bSize:%s",
                            ce,
                            cell.getColspan(),
                            cell.getRowspan(),
                            cell.getBorderSize()));
                    ce++;
                }
                r++;
            }
        }
    }
}
