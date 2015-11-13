package uia.pdf.gridbag;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

import uia.pdf.gridbag.layout.GridBagType;

public class GridBagLayoutTest {

    @Test
    public void testSample1() throws URISyntaxException, Exception {
        GridBagType gbType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample1.xml").toURI()));
        GridBagLayout tv = new GridBagLayout(gbType);
        tv.load(700, 900);
        print(tv);
    }

    @Test
    public void testSample2() throws URISyntaxException, Exception {
        GridBagType gbType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("sample2.xml").toURI()));
        GridBagLayout tv = new GridBagLayout(gbType);
        tv.load(700, 900);
        print(tv);
    }

    @Test
    public void testMR() throws URISyntaxException, Exception {
        GridBagType gbType = GridBagTypeHelper.load(new File(GridBagTypeHelperTest.class.getResource("mr.xml").toURI()));
        GridBagLayout tv = new GridBagLayout(gbType);
        tv.load(700, 900);
        print(tv);
    }

    private void print(GridBagLayout tv) {
        for (Grid grid : tv.getGrids()) {
            System.out.println(grid);
            for (Column col : grid.columns) {
                System.out.println(" " + col);
            }
            System.out.println();

            for (Row row : grid.rows) {
                System.out.println(" " + row);
            }
            System.out.println();

            int r = 0;
            for (Cell[] cells : grid.cells) {
                int c = 0;
                for (Cell cell : cells) {
                    System.out.println(String.format(" c[%s,%s]: %s", r, c, cell));
                    c++;
                }
                r++;
            }
            System.out.println();
        }
    }

}
