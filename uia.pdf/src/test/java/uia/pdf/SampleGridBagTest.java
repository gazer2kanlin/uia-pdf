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
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import uia.pdf.PDFMaker;
import uia.pdf.gridbag.GridBagDescriptionView;
import uia.pdf.gridbag.GridBagFactory;
import uia.pdf.gridbag.GridBagView;
import uia.pdf.papers.Paper;

public class SampleGridBagTest {

    @Test
    public void test1() throws Exception {
        System.out.println(Paper.A4);

        // 1. new document
        PDFMaker pdf = new PDFMaker(FontUtils.simplified());
        
        GridBagFactory factory1 = GridBagFactory.fromXml("sample/gridbag/sample_headerFooter.xml");

        // 2. new headers
        GridBagDescriptionView hv = factory1.descView(pdf, "header");

        // 3. new footer
        GridBagDescriptionView fv = factory1.descView(pdf, "footer");

        // 4. new layout content view
        GridBagFactory factory2 = GridBagFactory.fromXml("sample/gridbag/layout3.xml");
        GridBagView cv = factory2.mainView(pdf, Paper.A4);
        cv.getPaper().setBottomPadding(100);
        
        // 4.1 set header to content
        cv.setHeaderView(hv);
        // 4.2 set footer to content
        cv.setFooterView(fv);
        // 4.3 draw
        cv.addPage(prepareData1(), "Inspection Report 1", false);
        cv.addPage(prepareData2());
        cv.addPage(prepareData3(), "Inspection Report 2", false);

        // 5. draw header & footer
        hv.draw();
        fv.draw();

        System.out.println(cv);
        pdf.save(new File("output/gridbag_layout3.pdf"));
    }

    private Map<String, Object> prepareData1() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "Inspection Report 1 - 0102851");
        return row1;
    }

    private Map<String, Object> prepareData2() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "Inspection Report 1 - 0829987");
        return row1;
    }

    private Map<String, Object> prepareData3() {
        TreeMap<String, Object> row1 = new TreeMap<String, Object>();
        row1.put("titleInfo", "Inspection Report 2 - 0098011");
        return row1;
    }
}
