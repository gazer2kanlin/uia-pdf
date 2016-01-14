/*
 * Copyright ${year} uia.pdf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import uia.pdf.papers.A4Paper;

public class GridViewTest {

    @Test
    public void testTutorial0() throws Exception {
        // 1. document
        File font = new File(System.getProperty("user.dir") + "\\fonts\\traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

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
        /**
        view1.getValueParserFactory().register(MyData.class, new ValueParser() {

            @Override
            public String read(Object value) {
                return "Name:" + ((MyData) value).getName();
            }

        });
         */
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        pdf.beginBookmarkGroup("第一章 A4 橫式測試頁");
        view1.draw(prepareData1(), "1-1 第一次資料");
        view1.draw(prepareData1(), "1-2 第二次資料");
        pdf.endBookmarkGroup();
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
                }, 11));
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        pdf.beginBookmarkGroup("第二章 A4 直式測試頁");
        view2.draw(prepareData1(), "2-1 第一次資料");
        pdf.endBookmarkGroup();
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("C:\\TEMP\\GRID_TUTORIAL0.PDF"));
    }

    @Test
    public void testTutorial1() throws Exception {
        File layout = new File(GridTypeHelperTest.class.getResource("sample.xml").toURI());
        GridXMLModelFactory modelFactory = new GridXMLModelFactory(layout);

        File font = new File(System.getProperty("user.dir") + "\\fonts\\traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        A4Paper paper = new A4Paper(true);
        GridView view = new GridView(
                pdf,
                paper,
                modelFactory.create("employee", paper));
        view.draw(Employee.createSample(), "員工基本資料");

        pdf.save(new File("C:\\TEMP\\GRID_TUTORIAL1.PDF"));
    }

    @Test
    public void testTutorial2() throws Exception {
        File layout = new File(GridTypeHelperTest.class.getResource("sample.xml").toURI());
        GridXMLModelFactory modelFactory = new GridXMLModelFactory(layout);

        File font = new File(System.getProperty("user.dir") + "\\fonts\\traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        A4Paper paper1 = new A4Paper();
        GridView view1 = new GridView(
                pdf,
                paper1,
                modelFactory.create("employee", paper1));
        view1.draw(Employee.createSample(), "員工基本資料");

        A4Paper paper2 = new A4Paper(true);
        GridView view2 = new GridView(
                pdf,
                paper2,
                modelFactory.create("project", paper2));
        view2.draw(Project.createSample(), "專案基本資料");

        pdf.save(new File("C:\\TEMP\\GRID_TUTORIAL2.PDF"));
    }

    @Test
    public void testToturial3() throws Exception {
        File layout = new File(GridTypeHelperTest.class.getResource("sample.xml").toURI());
        GridXMLModelFactory modelFactory = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File(System.getProperty("user.dir") + "\\fonts\\traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 11);

        // 3. chapter 1
        SimpleHeaderView hv1 = new SimpleHeaderView("第一章 A4 橫式測試頁", 20);
        A4Paper paper1 = new A4Paper();
        GridView view1 = new GridView(pdf, paper1, modelFactory.create("employee", paper1));
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        view1.draw(Employee.createSample(), "員工基本資料");
        hv1.draw();

        // 4. chapter 2
        SimpleHeaderView hv2 = new SimpleHeaderView("第二章 A4 直式測試頁", 20);
        A4Paper paper2 = new A4Paper(true);
        GridView view2 = new GridView(pdf, paper2, modelFactory.create("project", paper2));
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        view2.draw(Project.createSample(), "專案基本資料");
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. save
        pdf.save(new File("C:\\TEMP\\GRID_TUTORIAL3.PDF"));
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

        for (int i = 0; i < 89; i++) {
            LinkedHashMap<String, Object> r2 = new LinkedHashMap<String, Object>();
            r2.put("Byte", Byte.MIN_VALUE);
            r2.put("Short", Short.MIN_VALUE);
            r2.put("Integer", i);
            r2.put("Long", Long.MIN_VALUE);
            r2.put("Boolean", false);
            r2.put("Time", new Date());
            r2.put("String", "Bad Answer");
            r1.put("Value", new MyData("Kan"));
            table.add(r2);
        }

        return table;
    }

    @Test
    public void testTutorial4() throws Exception {
        // 1. document
        File font = new File(System.getProperty("user.dir") + "\\fonts\\traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

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
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        pdf.beginBookmarkGroup("第一章 A4 橫式測試頁");
        view1.draw(prepareData1(), "1-1 第一次資料");
        view1.draw(prepareData1(), "1-2 第二次資料");
        pdf.endBookmarkGroup();
        hv1.draw();

        SimpleHeaderView hv2 = new SimpleHeaderView("第二章 A4 直式測試頁", 20);
        GridView view2 = view1.create(new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 30, AlignmentType.NEAR),
                        new ColumnModel("Short", "SHORT", 40, AlignmentType.NEAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.NEAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.NEAR),
                        new ColumnModel("Boolean", "BOOL", 30, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 150, AlignmentType.NEAR)
                }, 11));
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        pdf.beginBookmarkGroup("第二章 A4 直式測試頁");
        view2.draw(prepareData1(), "2-1 第一次資料");
        pdf.endBookmarkGroup();
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("C:\\TEMP\\GRID_TUTORIAL4.PDF"));
    }
}
