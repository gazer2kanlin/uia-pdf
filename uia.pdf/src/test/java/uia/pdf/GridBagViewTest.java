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

public class GridBagViewTest {

    @Test
    public void test1() throws Exception {
        GridBagFactory factory1 = GridBagFactory.fromXml("sample/gridbag/layout_header_footer.xml");
        GridBagFactory factory2 = GridBagFactory.fromXml("sample/gridbag/layout.xml");

        // 1. new document
        PDFMaker pdf = new PDFMaker(FontUtils.simplified());

        // 2. header & footer
        GridBagDescriptionView header = factory1.descView(pdf, "header");
        GridBagDescriptionView footer = factory1.descView(pdf, "footer");

        // 3 A4
        GridBagView cv1 = factory2.mainView(pdf, Paper.A4);
        cv1.arrange();
        cv1.setHeaderView(header);
        cv1.setFooterView(footer);
        cv1.addPage(prepareData1(), "Inspection Report 1", false);
        cv1.addPage(prepareData2());
        cv1.addPage(prepareData3(), "Inspection Report 2", false);

        // 4 A4L
        GridBagView cv2 = factory2.mainView(pdf, Paper.A4L);
        cv1.arrange();
        cv2.setHeaderView(header);
        cv2.setFooterView(footer);
        cv2.addPage(prepareData1(), "Inspection Report 3", false);
        cv2.addPage(prepareData2());
        cv2.addPage(prepareData3(), "Inspection Report 4", false);
        
        // 5. draw header & footer
        header.draw();
        footer.draw();

        pdf.save(new File("output/gridbag_case1.pdf"));
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
