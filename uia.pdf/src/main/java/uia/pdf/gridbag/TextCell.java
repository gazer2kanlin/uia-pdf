package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.PDFUtil;

public class TextCell extends Cell {

    private final String alignment;

    public final Text text;

    public final Text subText;

    TextCell(Grid grid, int row, int col, int rowspan, int colspan, Color background, float boderSize, Color borderColor, String alignment, Text text, Text subText) {
        super(grid, row, col, rowspan, colspan, background, boderSize, borderColor);
        this.alignment = alignment;
        this.text = text;
        this.subText = subText;
    }

    @Override
    void accept(GridBagView view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data) {
        int textLine = bottomLeft.y + (getHeight() / 2) - 1;

        try {
            PDFont font = view.getDoc().getFont();

            contentStream.setFont(font, this.text.fontSize);
            contentStream.setNonStrokingColor(this.text.foreground);

            int fontSize1 = PDFUtil.fixFontSzie(this.text.value, font, this.text.fontSize, getWidth());
            contentStream.setFont(font, fontSize1);
            contentStream.setNonStrokingColor(this.text.foreground);
            int cw1 = PDFUtil.getStringWidth(this.text.value, font, fontSize1);
            contentStream.beginText();
            if ("NEAR".equalsIgnoreCase(this.alignment)) {
                contentStream.newLineAtOffset(bottomLeft.x + 2, textLine);
            }
            else if ("FAR".equalsIgnoreCase(this.alignment)) {
                contentStream.newLineAtOffset(bottomLeft.x + getWidth() - cw1 - 2, textLine);
            }
            else {
                contentStream.newLineAtOffset(bottomLeft.x + (getWidth() - cw1) / 2, textLine);
            }
            contentStream.showText(this.text.value);
            contentStream.endText();

            int fontSize2 = PDFUtil.fixFontSzie(this.subText.value, font, this.subText.fontSize, getWidth());
            contentStream.setFont(font, fontSize2);
            contentStream.setNonStrokingColor(this.subText.foreground);
            int cw2 = PDFUtil.getStringWidth(this.subText.value, font, fontSize2);
            contentStream.beginText();
            if ("NEAR".equalsIgnoreCase(this.alignment)) {
                contentStream.newLineAtOffset(bottomLeft.x + 2, textLine - PDFUtil.getStringHeight(font, fontSize2) - 3);
            }
            else if ("FAR".equalsIgnoreCase(this.alignment)) {
                contentStream.newLineAtOffset(bottomLeft.x + getWidth() - cw2 - 2, textLine - PDFUtil.getStringHeight(font, fontSize2) - 3);
            }
            else {
                contentStream.newLineAtOffset(bottomLeft.x + (getWidth() - cw2) / 2, textLine - PDFUtil.getStringHeight(font, fontSize2) - 3);
            }
            contentStream.showText(this.subText.value);
            contentStream.endText();
        }
        catch (Exception ex) {

        }
    }
}
