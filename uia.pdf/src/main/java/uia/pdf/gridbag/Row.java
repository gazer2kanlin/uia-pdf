package uia.pdf.gridbag;

import java.awt.Color;

public class Row {

    public Grid grid;

    public int index;

    public int y;

    public int height;

    public Color background;

    public String fontSize;

    Row() {

    }

    @Override
    public String toString() {
        return String.format("row[%2s](x=%3s,y=%3s,w=%3s,h=%3s)", this.index, this.grid.x, this.y, this.grid.width, this.height);
    }
}
