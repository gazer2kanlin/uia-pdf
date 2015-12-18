package uia.pdf;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

public class PDFMakerTest {

    @Test
    public void justTest() throws Exception {
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contents = new PDPageContentStream(doc, page, true, false, false);
            contents.moveTo(20, 650);
            contents.lineTo(500, 650);
            contents.stroke();
            contents.close();

            PDImageXObject pdImage = PDImageXObject.createFromFile(new File(PDFMakerTest.class.getResource("sample.jpg").toURI()), doc);
            contents = new PDPageContentStream(doc, page, true, false, false);
            contents.drawImage(pdImage, 20, 700);
            contents.close();

            contents = new PDPageContentStream(doc, page, true, false, false);
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
