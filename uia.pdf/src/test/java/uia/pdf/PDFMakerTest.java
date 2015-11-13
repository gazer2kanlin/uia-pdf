package uia.pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

import uia.pdf.grid.ColumnModel;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.DefaultGridModel;
import uia.pdf.grid.GridView;
import uia.pdf.grid.MyCellRenderer;
import uia.pdf.grid.MyData;
import uia.pdf.gridbag.GridBagTypeHelperTest;
import uia.pdf.gridbag.GridBagView;
import uia.pdf.papers.A4Paper;

public class PDFMakerTest {

    @Test
    public void justTest() throws Exception {
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contents = new PDPageContentStream(doc, page, true, false, false);
            contents.moveTo(20, 650);
            contents.lineTo(500, 650);
            contents.stroke();
            contents.close();

            PDImageXObject pdImage = PDImageXObject.createFromFile(new File(PDFMakerTest.class.getResource("sample.jpg").toURI()), doc);
            contents = new PDPageContentStream(doc, page, true, false, false);
            contents.drawImage(pdImage, 20, 700);
            contents.close();

            contents = new PDPageContentStream(doc, page, true, false, false);
            contents.moveTo(20, 550);
            contents.lineTo(500, 550);
            contents.stroke();
            contents.close();

            doc.save("C:\\TEMP\\IMAGE.PDF");
        }
        finally {
            doc.close();
        }

    }

    @Test
    public void testGen() throws Exception {
        // 1. new document
        PDFMaker pdf = new PDFMaker(PDFMaker.TRADITIONAL);

        // 2. new headers
        HeaderView hv1 = new HeaderView("第一章節", 20);
        HeaderView hv2 = new HeaderView("第二章節", 20);

        // 3. new footer
        FooterView fv = new FooterView("DOC-0001-AAA0-12.32B", "2015-11-06");

        // 4. draw grid view
        GridView vg1 = new GridView(pdf, new A4Paper(true), new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 30, AlignmentType.FAR),
                        new ColumnModel("Short", "SHORT", 45, AlignmentType.FAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.FAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.FAR),
                        new ColumnModel("Boolean", "BOOL", 40, AlignmentType.CENTER),
                        new ColumnModel("Time", "TIME", 120, AlignmentType.CENTER),
                        new ColumnModel("String", "CONTENT", 200, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 100, AlignmentType.NEAR)
                },
                new MyCellRenderer()));
        vg1.getValueParserFactory().register(MyData.class, value -> "Name:" + ((MyData) value).getName());
        vg1.setHeaderView(hv1);
        vg1.setFooterView(fv);
        vg1.draw(prepareGridData(), "Grid 1");
        hv1.draw();

        // 5. draw layout view
        GridBagView cv2 = new GridBagView(pdf, new A4Paper(), new File(GridBagTypeHelperTest.class.getResource("mr.xml").toURI()));
        cv2.setHeaderView(hv2);
        cv2.setFooterView(fv);
        ArrayList<String> bs = new ArrayList<String>();
        bs.add("Report One");
        bs.add("Report Two");
        cv2.draw(prepareGridBagData(), bs);
        hv2.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("C:\\TEMP\\SAMPLE_MIX.PDF"));
    }

    private List<Map<String, Object>> prepareGridData() {
        ArrayList<Map<String, Object>> table = new ArrayList<Map<String, Object>>();

        LinkedHashMap<String, Object> r1 = new LinkedHashMap<String, Object>();
        r1.put("Byte", Byte.MAX_VALUE);
        r1.put("Short", Short.MAX_VALUE);
        r1.put("Integer", Integer.MAX_VALUE);
        r1.put("Long", Long.MAX_VALUE);
        r1.put("Boolean", true);
        r1.put("Time", new Date());
        r1.put("String", "Good Answer");
        r1.put("Value", new MyData("Kyle"));
        table.add(r1);

        for (int i = 0; i < 100; i++) {
            LinkedHashMap<String, Object> r2 = new LinkedHashMap<String, Object>();
            r2.put("Byte", Byte.MIN_VALUE);
            r2.put("Short", Short.MIN_VALUE);
            r2.put("Integer", Integer.MIN_VALUE);
            r2.put("Long", Long.MIN_VALUE);
            r2.put("Boolean", false);
            r2.put("Time", new Date());
            r2.put("String", "Bad Answer");
            r1.put("Value", new MyData("Kan"));
            table.add(r2);
        }

        return table;
    }

    private List<Map<String, Object>> prepareGridBagData() {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("fullName", "Kyle K. Lin");
        data.add(row1);
        TreeMap<String, Object> row2 = new TreeMap<String, Object>();
        row2.put("fullName", "Patrick W. Lin");
        data.add(row2);
        return data;
    }
}
