package uia.pdf.grid;

import java.io.File;

import org.junit.Test;

public class GridXMLModelFactoryTest {

    @Test
    public void test() throws Exception {
    	GridXMLModelFactory f = new GridXMLModelFactory(new File("sample/grid/layout.xml"));
    	
        GridModel model1 = f.create("image", 500);
        System.out.println(model1.getCellRenderer(0, 0).getClass().getName());

        GridModel model2 = f.create("employee", 500);
        System.out.println(model2.getCellRenderer(0, 0).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 1).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 2).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 3).getClass().getName());
    }
}
