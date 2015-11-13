package uia.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Footer view.
 *
 * @author Kan Lin
 *
 */
public class FooterView extends DescriptionView {

    private final String leftText;

    private final String rightText;

    public FooterView(String leftText, String rightText) {
        this.leftText = leftText;
        this.rightText = rightText;
    }

    @Override
    public int getHeight() {
        return 40;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        PDFont font = cv.getDoc().getFont();

        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, true, false, false);
        contentStream.setFont(font, 9);

        contentStream.moveTo(cv.getPaper().getLeft(), cv.getPaper().getBottom() + 15);
        contentStream.lineTo(cv.getPaper().getRight(), cv.getPaper().getBottom() + 15);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getLeft(), cv.getPaper().getBottom());
        contentStream.showText(this.leftText);
        contentStream.endText();

        String pageIndex = getPageIndex(page) + " / " + getPageCount();
        int cx = PDFUtil.getStringWidth(pageIndex, font, 9);
        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getCenterX() - cx / 2, cv.getPaper().getBottom());
        contentStream.showText(pageIndex);
        contentStream.endText();

        int rx = PDFUtil.getStringWidth(this.rightText, font, 9);
        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getRight() - rx, cv.getPaper().getBottom());
        contentStream.showText(this.rightText);
        contentStream.endText();

        contentStream.close();
    }
}
