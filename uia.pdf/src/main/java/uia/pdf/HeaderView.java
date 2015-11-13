package uia.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Header view.
 *
 * @author Kan Lin
 *
 */
public class HeaderView extends DescriptionView {

    private String title;

    private int fontSize;

    public HeaderView(String title, int fontSize) {
        this.title = title;
        this.fontSize = fontSize;
    }

    @Override
    public int getHeight() {
        return this.fontSize + 30;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        PDFont font = cv.getDoc().getFont();

        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, true, false, false);
        contentStream.setFont(font, this.fontSize);

        contentStream.moveTo(cv.getPaper().getLeft(), cv.getPaper().getTop() - this.fontSize - 5);
        contentStream.lineTo(cv.getPaper().getRight(), cv.getPaper().getTop() - this.fontSize - 5);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getLeft(), cv.getPaper().getTop() - this.fontSize);
        contentStream.showText(this.title);
        contentStream.endText();

        contentStream.close();
    }
}
