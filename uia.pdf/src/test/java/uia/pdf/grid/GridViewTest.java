package uia.pdf.grid;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import uia.pdf.PDFMaker;
import uia.pdf.SimpleFooterView;
import uia.pdf.SimpleHeaderView;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.exs.Employee;
import uia.pdf.grid.exs.EmployeeProject;
import uia.pdf.papers.A4Paper;
import uia.pdf.parsers.ValueParser;

public class GridViewTest {

    @Test
    public void testGenUsingXML() throws Exception {
        GridModelFactory modelFactory = new GridModelFactory(new File(GridTypeHelperTest.class.getResource("sample.xml").toURI()));

        // 1. document
        PDFMaker pdf = new PDFMaker(new File(System.getProperty("user.idr") + "\\fonts\\traditional.ttf"));

        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 11);

        // 3. chapter 1
        SimpleHeaderView hv1 = new SimpleHeaderView("第一章 A4 橫式測試頁", 20);
        A4Paper paper1 = new A4Paper(true);
        GridView view1 = new GridView(pdf, paper1, modelFactory.create("employee", paper1.getDrawableSize().width));
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        view1.draw(Employee.createSample(), "員工基本資料");
        hv1.draw();

        // 4. chapter 2
        SimpleHeaderView hv2 = new SimpleHeaderView("第二章 A4 直式測試頁", 20);
        A4Paper paper2 = new A4Paper();
        GridView view2 = new GridView(pdf, paper2, modelFactory.create("employeeProject", paper2.getDrawableSize().width));
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        view2.draw(EmployeeProject.createSample(), "專案基本資料");
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. save
        pdf.save(new File("C:\\TEMP\\GRID_SAMPLE_XML.PDF"));
    }

    @Test
    public void testGenUsingCode() throws Exception {
        // 1. document
        PDFMaker pdf = new PDFMaker(new File(System.getProperty("user.idr") + "\\fonts\\traditional.ttf"));

        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 12);

        // 3. chapter 1
        SimpleHeaderView hv1 = new SimpleHeaderView("第一章 A4 橫式測試頁", 20);
        GridView view1 = new GridView(pdf, new A4Paper(true), new DefaultGridModel(
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
        view1.getValueParserFactory().register(MyData.class, new ValueParser() {

            @Override
            public String read(Object value) {
                return "Name:" + ((MyData) value).getName();
            }

        });
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        view1.beginBookmarkGroup("Topic 1 Group");
        view1.draw(prepareData1(), "1-1 Topic 1-1");
        view1.draw(prepareData1(), "1-2 Topic 1-2");
        view1.endBookmarkGroup();
        hv1.draw();

        // 4. chapter 2
        SimpleHeaderView hv2 = new SimpleHeaderView("第二章 A4 直式測試頁", 20);
        GridView view2 = new GridView(pdf, new A4Paper(), new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 30, AlignmentType.NEAR),
                        new ColumnModel("Short", "SHORT", 40, AlignmentType.NEAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.NEAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.NEAR),
                        new ColumnModel("Boolean", "BOOL", 30, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 150, AlignmentType.NEAR)
                }));
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        view2.draw(prepareData1(), "2-1 Topic 2-1");
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("C:\\TEMP\\GRID_SAMPLE_CODE.PDF"));
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
