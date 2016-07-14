package uia.pdf.grid;

import java.io.File;

import org.junit.Test;

public class GridXMLModelFactoryTest {

    @Test
    public void test() throws Exception {
        GridModel model1 = new GridXMLModelFactory(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "doc_panels.xml")).create("image", 500);
        System.out.println(model1.getCellRenderer(0, 0).getClass().getName());

        GridModel model2 = new GridXMLModelFactory(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "doc_panels.xml")).create("panel", 500);
        System.out.println(model2.getCellRenderer(0, 0).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 1).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 2).getClass().getName());
        System.out.println(model2.getCellRenderer(0, 3).getClass().getName());
    }
}
