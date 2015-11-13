package uia.pdf.grid;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;

public class MyCellRenderer extends DefaultCellRenderer {

    @Override
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, ColumnModel cm, Object value, int row, int col) {
        if (col == 4) {
            if (((Boolean) value).booleanValue()) {
                super.paint(contentStream, bottomLeft, view, cm, "正確", row, col);
            }
            else {
                super.paint(contentStream, bottomLeft, view, cm, "錯誤", row, col);
            }
        }
        else {
            super.paint(contentStream, bottomLeft, view, cm, value, row, col);
        }
    }

}
