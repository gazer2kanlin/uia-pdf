package uia.pdf.grid;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;

/**
 * Cell renderer.
 *
 * @author Kan Lin
 *
 */
public interface CellRenderer {

    /**
     *
     * @param contentStream Content stream of page.
     * @param bottomLeft Axis of bottom-left.
     * @param view Content view.
     * @param cm Column model.
     * @param value Data used to display.
     * @param row Row index.
     * @param col Column index.
     */
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, ColumnModel cm, Object value, int row, int col);
}
