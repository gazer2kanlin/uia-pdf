package uia.pdf.grid;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFUtil;
import uia.pdf.grid.ColumnModel.AlignmentType;

/**
 * Default implementation of cell renderer.
 *
 * @author Kan Lin
 *
 */
public class DefaultCellRenderer implements CellRenderer {

    @Override
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, ColumnModel cm, Object value, int row, int col) {
        try {
            PDFont font = view.getDoc().getFont();
            String content = view.getValueParserFactory().read(value);
            int contentWidth = Math.min(cm.getWidth() - 4, PDFUtil.getStringWidth(content, font, 9));
            int offset = 2;
            if (cm.getHorizontalAlignment() == AlignmentType.FAR) {
                offset = cm.getWidth() - contentWidth - 2;
            }
            else if (cm.getHorizontalAlignment() == AlignmentType.CENTER) {
                offset = (cm.getWidth() - contentWidth) / 2;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(bottomLeft.x + offset, bottomLeft.y);
            contentStream.showText(content);
            contentStream.endText();
        }
        catch (Exception ex) {

        }
    }

}
