package uia.pdf.gridbag;

import java.awt.Point;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;

public interface GridBagCellRenderer {

    /**
     *
     * @param contentStream
     * @param bottomLeft
     * @param view
     * @param cell
     * @param value
     * @throws IOException
     */
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, Cell cell, Object value) throws IOException;
}
