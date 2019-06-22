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

package uia.pdf;

import java.io.File;

import org.junit.Test;

import uia.pdf.PDFMaker;
import uia.pdf.SimpleFooterView;
import uia.pdf.SimpleHeaderView;
import uia.pdf.grid.SampleEmployee;
import uia.pdf.grid.GridViewFactory;
import uia.pdf.grid.GridView;
import uia.pdf.grid.SampleProject;
import uia.pdf.gridbag.GridBagDescriptionView;
import uia.pdf.gridbag.GridBagFactory;
import uia.pdf.papers.Paper;

public class GridViewTest {

	/**
	 * 1 pager size.
	 * 
	 * @throws Exception
	 */
	@Test
    public void testCase1() throws Exception {
		// 1. load layouts for a document
		GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");

		// 2. prepare header and footer
		SimpleHeaderView header = new SimpleHeaderView("UIA4J", 20);
		SimpleFooterView footer = new SimpleFooterView("UIA4J-0920", "2018-09-20", 11);

		// 3. new a pdf
		PDFMaker pdf = new PDFMaker(FontUtils.traditional());

		// 4. add data
		GridView view = factory1.mainView(pdf, Paper.A4L, "employee");
		view.setHeaderView(header);
		view.setFooterView(footer);
		view.draw(SampleEmployee.create(), "Employee List", true);

		// 5. draw header & footer
		header.draw();
		footer.draw();

		// 6.save
		pdf.save(new File("output/grid_case1.pdf"));
	}

	/**
	 * 2 pager sizes.
	 * 
	 * @throws Exception
	 */
    @Test
    public void testCase2() throws Exception {
        GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");

        // 1. document
        PDFMaker pdf = new PDFMaker(FontUtils.traditional());
        
        // 2.1 data: employee
        GridView view1 = factory1.mainView(pdf, Paper.A4, "employee");
        view1.draw(SampleEmployee.create(), "員工基本資料", true);
        // 2.2 data: project
        GridView view2 = factory1.mainView(pdf, Paper.A4L, "project");
        view2.draw(SampleProject.create(), "專案基本資料", true);
        
        // 3.save
        pdf.save(new File("output/grid_case2.pdf"));
    }

    /**
     * View haeder & doc footer
     * @throws Exception
     */
    @Test
    public void testCase3() throws Exception {
        GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");

        // 1. document
        PDFMaker pdf = new PDFMaker(FontUtils.traditional());
        
        // 2. header & footer
        SimpleHeaderView header1 = new SimpleHeaderView("Employee List", 20);
        SimpleHeaderView header2 = new SimpleHeaderView("Project List", 20);
        SimpleFooterView footer = new SimpleFooterView("DOC-HR-00001", "2015-11-06", 11);
        
        // 3.1 data:employee
        GridView view1 = factory1.mainView(pdf, Paper.A4, "employee");
        view1.setHeaderView(header1);
        view1.setFooterView(footer);
        view1.draw(SampleEmployee.create(), "員工基本資料", true);
        // 3.2 data:project
        GridView view2 = factory1.mainView(pdf, Paper.A4L, "project");
        view2.setHeaderView(header2);
        view2.setFooterView(footer);
        view2.draw(SampleProject.create(), "專案基本資料", true);
        
        // 4. draw header & footer
        header1.draw();
        header2.draw();
        footer.draw();

        // 5. save
        pdf.save(new File("output/grid_case3.pdf"));
    }

    /**
     * Add bookmark group.
     * 
     * @throws Exception
     */
    @Test
    public void testCase4() throws Exception {
        GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");

        // 1. document
        PDFMaker pdf = new PDFMaker(FontUtils.traditional());
        
        // 3. header & footer
        SimpleFooterView footer = new SimpleFooterView("DOC-HR-00001", "2015-11-06", 11);
        SimpleHeaderView header1 = new SimpleHeaderView("員工基本資料", 20);
        SimpleHeaderView header2 = new SimpleHeaderView("專案基本資料", 20);
        
        // 3. bookmark group: begin 
        pdf.beginBookmarkGroup("基本資料");
        
        // 4.1 data:employee
        GridView view1 = factory1.mainView(pdf, Paper.A4, "employee");
        view1.setHeaderView(header1);
        view1.setFooterView(footer);
        view1.draw(SampleEmployee.create(), "員工基本資料", true);
        // 4.2 data:project
        GridView view2 = factory1.mainView(pdf, Paper.A4L, "project");
        view2.setHeaderView(header2);
        view2.setFooterView(footer);
        view2.draw(SampleProject.create(), "專案基本資料", true);
        
        // 5. header & footer
        header1.draw();
        header2.draw();
        footer.draw();
        
        // 6. bookmark group: end 
        pdf.endBookmarkGroup();
        
        // 7. save
        pdf.save(new File("output/grid_case4.pdf"));
    }


    /**
     * Add bookmark group.
     * 
     * @throws Exception
     */
    @Test
    public void testCase5() throws Exception {
        GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");

        // 1. document
        PDFMaker pdf = new PDFMaker(FontUtils.traditional());
        
        // 3. header & footer
        SimpleFooterView footer = new SimpleFooterView("DOC-HR-00001", "2015-11-06", 11);
        SimpleHeaderView header1 = new SimpleHeaderView("Employee List", 20);
        SimpleHeaderView header2 = new SimpleHeaderView("Project List", 20);
        
        // 3. bookmark group: begin 
        pdf.beginBookmarkGroup("基本資料");
        
        // 4.1 data:employee
        GridView view1 = factory1.mainView(pdf, Paper.A4, "employee");
        view1.beginBookmarkGroup("員工基本資料");
        view1.setHeaderView(header1);
        view1.setFooterView(footer);
        view1.draw(SampleEmployee.create(), "部門一", true);
        view1.draw(SampleEmployee.create(), "部門二", true);
        view1.endBookmarkGroup();
        // 4.2 data:project
        GridView view2 = factory1.mainView(pdf, Paper.A4L, "project");
        view2.setHeaderView(header2);
        view2.setFooterView(footer);
        view2.draw(SampleProject.create(), "專案基本資料", true);
        
        // 5. header & footer
        header1.draw();
        header2.draw();
        footer.draw();
        
        // 6. bookmark group: end 
        pdf.endBookmarkGroup();
        
        // 7. save
        pdf.save(new File("output/grid_case5.pdf"));
    }

    /**
     * Gridbag header & footer
     * 
     * @throws Exception
     */
	@Test
    public void testCase6() throws Exception {
        GridViewFactory factory1 = GridViewFactory.fromXml("sample/grid/layout.xml");
        GridBagFactory factory2 = GridBagFactory.fromXml("sample/gridbag/layout_header_footer.xml");

        // 1. document
        PDFMaker pdf = new PDFMaker(FontUtils.traditional());

        // 2. header & footer
        GridBagDescriptionView header = factory2.descView("header");
        GridBagDescriptionView footer = factory2.descView("footer");

        // 3.data
        GridView view = factory1.mainView(pdf, Paper.A4L, "employee");
        view.setHeaderView(header);
        view.setFooterView(footer);
        view.draw(SampleEmployee.create(), "員工基本資料", true);
        
        // 4. header & footer
        header.draw();
        footer.draw();

        // 5.save
        pdf.save(new File("output/grid_case6.pdf"));
    }
}
