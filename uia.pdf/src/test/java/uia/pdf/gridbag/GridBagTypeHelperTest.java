package uia.pdf.gridbag;

import java.io.File;

import org.junit.Test;

import uia.pdf.gridbag.GridBagTypeHelper;
import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.GridType;
import uia.pdf.gridbag.layout.RowType;

public class GridBagTypeHelperTest {

    @Test
    public void testSample0() throws Exception {
        GridBagType layout = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample0.xml").toURI()));
        for (GridType grid : layout.getLayout().getGrid()) {
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
            for (RowType row : grid.getRow()) {
                System.out.println(String.format("  row[%s]: height:%s bSize:%s",
                        r,
                        row.getHeight(),
                        row.getBorderSize()));
                int ce = 1;
                for (CellType cell : row.getTextCellOrBindCellOrLayoutCell()) {
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
