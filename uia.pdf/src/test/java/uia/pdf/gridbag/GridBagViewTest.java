package uia.pdf.gridbag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import uia.pdf.FooterView;
import uia.pdf.HeaderView;
import uia.pdf.PDFMaker;
import uia.pdf.papers.A4Paper;

public class GridBagViewTest {

    @Test
    public void testGen() throws Exception {
        // 1. new document
        PDFMaker pdf = new PDFMaker(PDFMaker.TRADITIONAL);

        // 2. new headers
        HeaderView hv = new HeaderView("Funny Report", 20);

        // 3. new footer
        FooterView fv = new FooterView("MLB-ABCDEFG-2005.1021D", "2015-10-21");

        // 4. new layout content view
        GridBagView cv = new GridBagView(pdf, new A4Paper(), new File(GridBagTypeHelperTest.class.getResource("sample3.xml").toURI()));
        // 4.1 set header to content
        cv.setHeaderView(hv);
        // 4.2 set footer to content
        cv.setFooterView(fv);
        // 4.3 draw
        ArrayList<String> bs = new ArrayList<String>();
        bs.add("Chicago Bears");
        bs.add("New York Mets");
        cv.draw(prepareData(), bs);

        // 5. draw header & footer
        hv.draw();
        fv.draw();

        pdf.save(new File("C:\\TEMP\\SAMPLE_GRIDBAG.PDF"));
    }

    private List<Map<String, Object>> prepareData() {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "Chicago Bears");
        data.add(row1);
        TreeMap<String, Object> row2 = new TreeMap<String, Object>();
        row2.put("titleInfo", "New York Mets");
        data.add(row2);
        return data;
    }
}
