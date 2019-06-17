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
import uia.pdf.grid.GridModelFactory;
import uia.pdf.grid.GridView;
import uia.pdf.grid.GridXMLModelFactory;
import uia.pdf.grid.data.Employee;
import uia.pdf.grid.data.Project;
import uia.pdf.papers.A4Paper;

public class SampleGridTest {

	@Test
    public void testCase1() throws Exception {
    	// layout model
        File layout = new File("sample/grid/layout.xml");
        GridModelFactory models = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2.data
        A4Paper paper = A4Paper.landscape();
        GridView view = models.createView(pdf, paper, "employee");
        view.draw(Employee.createSample(), "員工基本資料", true);

        // 3.save
        pdf.save(new File("output/employee_list.pdf"));
    }

    @Test
    public void testCase2() throws Exception {
    	// layout model
        File layout = new File("sample/grid/layout.xml");
        GridModelFactory models = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);
        
        // 2.1 data: employee
        GridView view1 = models.createView(pdf, A4Paper.portrait(), "employee");
        view1.draw(Employee.createSample(), "員工基本資料", true);
        // 2.2 data: project
        GridView view2 = models.createView(pdf, A4Paper.landscape(), "employee");
        view2.draw(Project.createSample(), "專案基本資料", true);
        
        // 3.save
        pdf.save(new File("output/employee_project_list.pdf"));
    }

    @Test
    public void testCase3() throws Exception {
    	// layout model
        File layout = new File("sample/grid/layout.xml");
        GridModelFactory models = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);
        
        // 2. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-HR-00001", "2015-11-06", 11);
        
        // 3.1 data:employee
        SimpleHeaderView hv1 = new SimpleHeaderView("員工基本資料", 20);
        GridView view1 = models.createView(pdf, A4Paper.portrait(), "employee");
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        view1.draw(Employee.createSample(), "員工基本資料", true);
        // 3.2 data:project
        GridView view2 = models.createView(pdf, A4Paper.landscape(), "project");
        SimpleHeaderView hv2 = new SimpleHeaderView("專案基本資料", 20);
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        view2.draw(Project.createSample(), "專案基本資料", true);
        
        // 4. draw footer
        fv.draw();

        // 5. save
        pdf.save(new File("output/employee_project_list_header_footer.pdf"));
    }

    @Test
    public void testCase4() throws Exception {
    	// layout model
        File layout = new File("sample/grid/layout.xml");
        GridXMLModelFactory models = new GridXMLModelFactory(layout);

        // 1. document
        File font = new File("fonts/traditional.ttf");
        PDFMaker pdf = new PDFMaker(font);
        
        // 2. bookmark group: begin 
        pdf.beginBookmarkGroup("基本資料");
        
        // 3. footer
        SimpleFooterView fv = new SimpleFooterView("DOC-HR-00001", "2015-11-06", 11);
        
        // 4.1 data:employee
        SimpleHeaderView hv1 = new SimpleHeaderView("員工基本資料", 20);
        GridView view1 = models.createView(pdf, A4Paper.portrait(), "employee");
        view1.setHeaderView(hv1);
        view1.setFooterView(fv);
        view1.draw(Employee.createSample(), "員工基本資料", true);
        // 4.2 data:project
        GridView view2 = models.createView(pdf, A4Paper.landscape(), "project");
        SimpleHeaderView hv2 = new SimpleHeaderView("專案基本資料", 20);
        view2.setHeaderView(hv2);
        view2.setFooterView(fv);
        view2.draw(Project.createSample(), "專案基本資料", true);
        
        // 5. draw footer
        fv.draw();
        
        // 6. bookmark group: end 
        pdf.endBookmarkGroup();
        
        // 7. save
        pdf.save(new File("output/employee_project_list_bookmark.pdf"));
    }
}
