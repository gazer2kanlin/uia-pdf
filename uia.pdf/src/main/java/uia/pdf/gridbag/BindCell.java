package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class BindCell extends Cell {

    public final String id;

    BindCell(Grid grid, int row, int col, int rowspan, int colspan, Color background, float boderSize, Color borderColor, String id) {
        super(grid, row, col, rowspan, colspan, background, boderSize, borderColor);
        this.id = id;
    }

    @Override
    void accept(GridBagView view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data) {
        try {
            Object value = data.get(this.id);
            view.getBindRenderer(this.id, value).paint(contentStream, bottomLeft, view, this, value);
        }
        catch (Exception ex) {
        }
    }
}
