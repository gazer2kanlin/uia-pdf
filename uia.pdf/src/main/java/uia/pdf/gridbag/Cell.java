package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public abstract class Cell {

    public final Grid grid;

    public final int row;

    public final int col;

    public final int rowspan;

    public final int colspan;

    public final Color background;

    public final float boderSize;

    public final Color borderColor;

    Cell(Grid grid, int row, int col, int rowspan, int colspan, Color background, float boderSize, Color borderColor) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.background = background;
        this.boderSize = boderSize;
        this.borderColor = borderColor;
    }

    /**
     * Convert bottom-left point to PdfBox coordinates system.
     * @param contentTopLeft Top-left point of PdfBox coordinates system.
     * @return Result.
     */
    public Point bottomLeft(Point contentTopLeft) {
        return new Point(contentTopLeft.x + getX(), contentTopLeft.y - getY() - getHeight());
    }

    public int getX() {
        return this.grid.columns[this.col].x;
    }

    public int getY() {
        return this.grid.rows[this.row].y;
    }

    public int getWidth() {
        int w = 0;
        for (int i = 0; i < this.colspan; i++) {
            w += this.grid.columns[this.col + i].width;
        }
        return w;
    }

    public int getHeight() {
        int h = 0;
        for (int i = 0; i < this.rowspan; i++) {
            h += this.grid.rows[this.row + i].height;
        }
        return h;
    }

    @Override
    public String toString() {
        return String.format("cell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s)", this.row, this.col, getX(), getY(), getWidth(), getHeight());
    }

    abstract void accept(GridBagView view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data);
}
