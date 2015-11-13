package uia.pdf.grid;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import uia.pdf.FooterView;
import uia.pdf.HeaderView;
import uia.pdf.PDFMaker;
import uia.pdf.grid.ColumnModel;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.DefaultGridModel;
import uia.pdf.grid.GridView;
import uia.pdf.papers.A4Paper;

public class GridViewTest {

    @Test
    public void testGen() throws Exception {
        // 1. new document
        PDFMaker pdf = new PDFMaker(PDFMaker.TRADITIONAL);

        // 2. new headers
        HeaderView hv1 = new HeaderView("第一章節", 20);
        HeaderView hv2 = new HeaderView("第二章節", 20);

        // 3. new footer
        FooterView fv = new FooterView("DOC-0001-AAA0-12.32B", "2015-11-06");

        // 4. new section 1 view WITH customize renderer
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
        // 4.1 add MyData value parser
        vg1.getValueParserFactory().register(MyData.class, value -> "Name:" + ((MyData) value).getName());
        // 4.2 set header to section 1
        vg1.setHeaderView(hv1);
        // 4.3 set footer to section 1
        vg1.setFooterView(fv);
        // 4.4 draw section 1
        vg1.draw(prepareData1(), "Chapter 1");
        hv1.draw();

        // 5. new section 2 view WITHOUT customize renderer
        GridView vg2 = new GridView(pdf, new A4Paper(), new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 30, AlignmentType.NEAR),
                        new ColumnModel("Short", "SHORT", 40, AlignmentType.NEAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.NEAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.NEAR),
                        new ColumnModel("Boolean", "BOOL", 30, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 150, AlignmentType.NEAR)
                }));
        // 5.1 set header to section 2
        vg2.setHeaderView(hv2);
        // 5.2 set footer to section 2 (share with section 1)
        vg2.setFooterView(fv);
        // 5.3 draw section 2
        vg2.draw(prepareData1(), "Chapter 2");
        hv2.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("C:\\TEMP\\SAMPLE_GRID.PDF"));
    }

    private List<Map<String, Object>> prepareData1() {
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
}
