package uia.pdf.grid;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class MyCellRenderer extends DefaultCellRenderer {

    @Override
    public int paint(PDPageContentStream contentStream, Point topLeft, GridView view, ColumnModel cm, Object value, int row, int col) {
        if (col == 4) {
            if (((Boolean) value).booleanValue()) {
                return super.paint(contentStream, topLeft, view, cm, "正確", row, col);
            }
            else {
                return super.paint(contentStream, topLeft, view, cm, "錯誤", row, col);
            }
        }
        else {
            return super.paint(contentStream, topLeft, view, cm, value, row, col);
        }
    }

}
