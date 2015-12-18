package uia.pdf.gridbag;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;

import uia.pdf.ContentView;
import uia.pdf.DescriptionView;

public class GridBagDescriptionView extends DescriptionView {

    private GridBagDrawer gbView;

    private Map<String, Object> data;

    private int height;

    public GridBagDescriptionView(File file, String name) throws Exception {
        this(file, name, null);
    }

    public GridBagDescriptionView(File file, String name, Map<String, Object> data) throws Exception {
        this.gbView = new GridBagDrawer(file);
        this.data = data;
        this.height = Integer.parseInt(this.gbView.gbLayout.getGridBagType(name).getHeight());
    }

    @Override
    public int getHeight() {
        return this.height + 3;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        this.gbView.draw(cv, page, this.data);
    }

}
