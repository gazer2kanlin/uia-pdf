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
import uia.pdf.gridbag.GridBagView;
import uia.pdf.papers.PaperType;

public class SampleGridBagTest {

    @Test
    public void testTutorial3() throws Exception {
        File hfLayout = new File("sample/gridbag/sample_headerFooter.xml");

        // 1. new document
        File font = new File("fonts/simplified.ttf");
        PDFMaker pdf = new PDFMaker(font);

        // 2. new headers
        GridBagDescriptionView hv = new GridBagDescriptionView(hfLayout, "header");

        // 3. new footer
        GridBagDescriptionView fv = new GridBagDescriptionView(hfLayout, "footer");

        // 4. new layout content view
        GridBagView cv = pdf.createGridBag(PaperType.A4, new File("sample/gridbag/layout3.xml"));
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
