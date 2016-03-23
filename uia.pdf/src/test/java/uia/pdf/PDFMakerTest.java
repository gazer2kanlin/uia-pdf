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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

public class PDFMakerTest {

    @Test
    public void justTest() throws Exception {
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.APPEND, false, false);
            contents.moveTo(20, 650);
            contents.lineTo(500, 650);
            contents.stroke();
            contents.close();

            PDImageXObject pdImage = PDImageXObject.createFromFile(PDFMakerTest.class.getResource("sample.jpg").getFile(), doc);
            contents = new PDPageContentStream(doc, page, AppendMode.APPEND, false, false);
            contents.drawImage(pdImage, 20, 700);
            contents.close();

            contents = new PDPageContentStream(doc, page, AppendMode.APPEND, false, false);
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
}
