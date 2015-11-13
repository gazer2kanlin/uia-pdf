package uia.pdf.gridbag;

import java.awt.Color;

public class Column {

    public Grid grid;

    public int index;

    public int x;

    public int width;

    public Color background;

    Column() {

    }

    @Override
    public String toString() {
        return String.format("col[%2s](x=%3s,y=%3s,w=%3s)", this.index, this.x, this.grid.y, this.width);
    }

}
