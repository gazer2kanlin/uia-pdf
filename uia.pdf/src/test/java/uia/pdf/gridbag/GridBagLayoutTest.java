package uia.pdf.gridbag;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

import uia.pdf.gridbag.layout.LayoutType;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.Column;
import uia.pdf.gridbag.model.GridBag;
import uia.pdf.gridbag.model.Row;

public class GridBagLayoutTest {

    @Test
    public void testSample1() throws URISyntaxException, Exception {
        LayoutType layoutType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample1.xml").toURI()));
        GridBagLayout layout = new GridBagLayout(layoutType);
        layout.load(700, 900);
        print(layout);
    }

    @Test
    public void testSample2() throws URISyntaxException, Exception {
    	LayoutType layoutType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample2.xml").toURI()));
        GridBagLayout layout = new GridBagLayout(layoutType);
        layout.load(700, 900);
        print(layout);
    }

    @Test
    public void testMR() throws URISyntaxException, Exception {
    	LayoutType layoutType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample3.xml").toURI()));
        GridBagLayout layout = new GridBagLayout(layoutType);
        layout.load(700, 900);
        print(layout);
    }

    private void print(GridBagLayout layout) {
        for (GridBag grid : layout.getGrids()) {
            System.out.println(grid);
            for (Column col : grid.columns) {
                System.out.println(" " + col);
            }
            System.out.println();

            for (Row row : grid.rows) {
                System.out.println(" " + row);
            }
            System.out.println();

            for (Cell[] cells : grid.cells) {
                for (Cell cell : cells) {
                    System.out.println(String.format(" %s", cell));
                }
            }
            System.out.println();
        }
    }

}
