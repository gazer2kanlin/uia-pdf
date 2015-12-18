package uia.pdf.gridbag;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import uia.pdf.PDFMaker;
import uia.pdf.SimpleFooterView;
import uia.pdf.SimpleHeaderView;
import uia.pdf.papers.A4Paper;

public class GridBagViewTest {

    @Test
    public void testGen() throws Exception {
        // 1. new document
        PDFMaker pdf = new PDFMaker(new File(System.getProperty("user.idr") + "\\fonts\\traditional.ttf"));

        // 2. new headers
        SimpleHeaderView hv = new SimpleHeaderView("Baseball Report for Fun", 20);

        // 3. new footer
        SimpleFooterView fv = new SimpleFooterView("BASEBALL-2005.1021", "2015-10-21", 11);

        // 4. new layout content view
        GridBagView cv = new GridBagView(pdf, new A4Paper(), new File(GridBagTypeHelperTest.class.getResource("sample3.xml").toURI()));
        // 4.1 set header to content
        cv.setHeaderView(hv);
        // 4.2 set footer to content
        cv.setFooterView(fv);
        // 4.3 draw
        cv.addPage(prepareData1(), "New York");
        cv.addPage(prepareData2());
        cv.addPage(prepareData3(), "Chicago");

        // 5. draw header & footer
        hv.draw();
        fv.draw();

        pdf.save(new File("C:\\TEMP\\GRIDBAG_SAMPLE3.PDF"));
    }

    private Map<String, Object> prepareData1() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "New York Mets");
        return row1;
    }

    private Map<String, Object> prepareData2() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "New York Yankee");
        return row1;
    }

    private Map<String, Object> prepareData3() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "Chicago Bears");
        return row1;
    }
}
