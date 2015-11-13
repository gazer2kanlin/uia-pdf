package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFUtil;

public class DefaultBindCellRenderer implements GridBagCellRenderer {

    @Override
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, Cell cell, Object value) throws IOException {
        PDFont font = view.getDoc().getFont();
        int fontSize = 12;
        contentStream.setFont(font, fontSize);
        String content = view.getValueParserFactory().read(value);
        int cw = PDFUtil.getStringWidth(content, font, fontSize);
        int ch = PDFUtil.getStringHeight(font, fontSize);

        contentStream.setNonStrokingColor(Color.black);
        contentStream.beginText();
        contentStream.newLineAtOffset(bottomLeft.x + (cell.getWidth() - cw) / 2, bottomLeft.y + (cell.getHeight() - ch) / 2);
        contentStream.showText(content);
        contentStream.endText();
    }
}
