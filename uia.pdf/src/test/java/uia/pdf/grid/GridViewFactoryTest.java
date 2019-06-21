package uia.pdf.grid;

import org.junit.Test;

public class GridViewFactoryTest {

    @Test
    public void testFromXml() throws Exception {
    	GridViewFactory f = GridViewFactory.fromXml("sample/grid/layout.xml");
    	
        GridModel model1 = f.createModel("image", 500);
        System.out.println("image> fontSize:" + model1.getFontSize());
        for(ColumnModel col : model1.getColumnModels()) {
        	System.out.println(String.format(" %s> width:%s, %s",
        			col.getId(),
        			col.getWidth(),
        			col.getCellRenderer().getClass().getName()));
        }
    	System.out.println();

        GridModel model2 = f.createModel("employee", 500);
        System.out.println("employee> fontSize:" + model2.getFontSize());
        for(ColumnModel col : model2.getColumnModels()) {
        	System.out.println(String.format(" %s> width:%s, %s",
        			col.getId(),
        			col.getWidth(),
        			col.getCellRenderer()));
        }
    }
}
