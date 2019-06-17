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
import java.util.TreeMap;

import org.junit.Test;

import uia.pdf.PDFMaker;
import uia.pdf.SimpleFooterView;
import uia.pdf.SimpleHeaderView;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.data.MyData;
import uia.pdf.grid.layout.LayoutTypeTest;
import uia.pdf.papers.A4Paper;

public class GridViewTest {

    @Test 
    public void testTutorial0() throws Exception {
        // 1. document
        PDFMaker pdf = new PDFMaker(new File("fonts/traditional.ttf"));

        // 2. footer
        SimpleFooterView docFooterView = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 12);
        
        pdf.beginBookmarkGroup("第一章 A4 橫式測試頁");

        // 3. chapter 1
        GridView ch1View = new GridView(pdf, new A4Paper(), createModelRenderer());
        ch1View.setFooterView(docFooterView);
        ch1View.draw(prepareData1(), "1-1 第一次資料", true);
        ch1View.draw(prepareData1(), "1-2 第二次資料", true);

        // 4. chapter 2
        GridView ch2View = new GridView(pdf, new A4Paper(),createModel());
        ch2View.setFooterView(docFooterView);
        ch2View.draw(prepareData1(), "2-1 第一次資料", true);

        pdf.endBookmarkGroup();

        // 5. draw footer
        docFooterView.draw();

        pdf.save(new File("output/grid_test0.pdf"));
    }

    @Test
    public void testTutorial4() throws Exception {
        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 12);

        // 3. chapter 1
        SimpleHeaderView hv1 = new SimpleHeaderView("第一章 A4 橫式測試頁", 20);
        GridView view1 = new GridView(pdf, new A4Paper(true), createModelRenderer());
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        pdf.beginBookmarkGroup("第一章 A4 橫式測試頁");
        view1.draw(prepareData1(), "1-1 第一次資料", true);
        view1.draw(prepareData1(), "1-2 第二次資料", true);
        pdf.endBookmarkGroup();
        hv1.draw();

        SimpleHeaderView hv2 = new SimpleHeaderView("第二章 A4 直式測試頁", 20);
        GridView view2 = view1.create(createModel());
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        pdf.beginBookmarkGroup("第二章 A4 直式測試頁");
        view2.draw(prepareData1(), "2-1 第一次資料", true);
        pdf.endBookmarkGroup();
        hv2.draw();

        // 5. draw footer
        fv.draw();

        // 6. draw footer
        fv.draw();

        pdf.save(new File("output/grid_test4.pdf"));
    }

    @Test
    public void testTutorial5() throws Exception {
        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2. footer
        // 3. chapter 1
        HeaderDescriptionView hv1 = new HeaderDescriptionView(pdf, new A4Paper(true), new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("C1", "", 100, AlignmentType.NEAR),
                        new ColumnModel("V1", "", 200, AlignmentType.NEAR),
                        new ColumnModel("C2", "", 100, AlignmentType.NEAR),
                        new ColumnModel("V2", "", 200, AlignmentType.NEAR),
                },
                9),
                90);
        hv1.setData(prepareFvData());
        GridView view1 = new GridView(pdf, new A4Paper(true), createModelRenderer());
        view1.setHeaderView(hv1);
        pdf.beginBookmarkGroup("第一章 A4 橫式測試頁");
        view1.draw(prepareData1(), "1-1 第一次資料", true);
        view1.draw(prepareData1(), "1-2 第二次資料", true);
        pdf.endBookmarkGroup();

        // 6. draw footer
        hv1.draw();

        pdf.save(new File("output/grid_test5.pdf"));
    }

    @Test
    public void testToturial6() throws Exception {
        File layout = new File(LayoutTypeTest.class.getResource("sample.xml").toURI());
        GridXMLModelFactory modelFactory = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-0001-AAA0-12.32B", "2015-11-06", 11);

        // 3. chapter 1
        SimpleHeaderView hv1 = new SimpleHeaderView("第一章 A4 橫式測試頁", 20);
        A4Paper paper1 = new A4Paper();
        GridView view1 = new GridView(pdf, paper1, modelFactory.create("image", paper1));
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);

        // 圖片資訊，利用  Map 儲存，在 bind 整個會當  value 傳給 paint 操作
        TreeMap<String, Object> imageInfo = new TreeMap<String, Object>();
        imageInfo.put("imagePath", "c:/temp/ABC.jpg");  // 圖檔
        imageInfo.put("imageText", "WTF Funny Job");    // 說明

        // 一筆資料，關鍵字 imageInfo，此值須跟 <column> 中的 bind 匹配。xml 參考 resources/uia/pdf/grid/sample.xml 中的 image grid 定義。
        // cellRenderer="uia.pdf.grid.ImageDescCellRenderer" 設定，客製化對  row 資料的處理
        TreeMap<String, Object> row = new TreeMap<String, Object>();
        row.put("imageInfo", imageInfo);

        // 資料集合
        ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        rows.add(row);

        view1.draw(rows, "測試圖片", true);
        hv1.draw();

        // 5. draw footer
        fv.draw();

        // 6. save
        pdf.save(new File("output/grid_test6.pdf"));
    }
    
    private DefaultGridModel createModel() {
    	return new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 50, AlignmentType.FAR),
                        new ColumnModel("Short", "SHORT", 55, AlignmentType.FAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.FAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.FAR),
                        new ColumnModel("Boolean", "BOOL", 40, AlignmentType.CENTER),
                        new ColumnModel("Time", "TIME", 120, AlignmentType.CENTER),
                        new ColumnModel("String", "CONTENT", 200, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 100, AlignmentType.NEAR)
                },
                12);
    }
    
    private DefaultGridModel createModelRenderer() {
    	return new DefaultGridModel(
                new ColumnModel[] {
                        new ColumnModel("Byte", "BYTE", 50, AlignmentType.FAR),
                        new ColumnModel("Short", "SHORT", 55, AlignmentType.FAR),
                        new ColumnModel("Integer", "INT", 60, AlignmentType.FAR),
                        new ColumnModel("Long", "LONG", 110, AlignmentType.FAR),
                        new ColumnModel("Boolean", "BOOL", 40, AlignmentType.CENTER, new MyBooleanRenderer()),
                        new ColumnModel("Time", "TIME", 120, AlignmentType.CENTER),
                        new ColumnModel("String", "CONTENT", 200, AlignmentType.NEAR),
                        new ColumnModel("Value", "VALUE", 100, AlignmentType.NEAR, new MyDataRenderer())
                },
                12);
    }

    private List<Map<String, Object>> prepareFvData() {
        ArrayList<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 3; i++) {
            LinkedHashMap<String, Object> r = new LinkedHashMap<String, Object>();
            r.put("C1", "C1 Name");
            r.put("V1", "C1 value");
            r.put("C2", "C2 Name");
            r.put("V2", Long.MAX_VALUE);
            table.add(r);
        }

        return table;
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
}
