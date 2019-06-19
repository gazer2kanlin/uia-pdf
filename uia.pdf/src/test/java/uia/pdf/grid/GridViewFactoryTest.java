package uia.pdf.grid;

import org.junit.Test;

public class GridViewFactoryTest {

    @Test
    public void testFromXml() throws Exception {
    	GridViewFactory f = GridViewFactory.fromXml("sample/grid/layout.xml");
    	
        GridModel model1 = f.createModel("image", 500);
        System.out.println(model1.getCellRenderer(0, 0).getClass().getName());

        GridModel model2 = f.createModel("employee", 500);
        System.out.println(model2.getCellRenderer(0, 0).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 1).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 2).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 3).getClass().getName());
    }
}
