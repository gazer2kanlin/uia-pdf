package uia.pdf.gridbag;

import java.awt.Color;

public class Grid {

    public int x;

    public int y;

    public int width;

    public int height;

    public boolean borderEnabled;

    public float borderSize;

    public Color borderColor;

    public final Column[] columns;

    public final Row[] rows;

    public final Cell[][] cells;

    Grid(int colCount, int rowCount) {
        this.columns = new Column[colCount];
        this.rows = new Row[rowCount];
        this.cells = new Cell[rowCount][];
        for (int i = 0; i < rowCount; i++) {
            this.cells[i] = new Cell[colCount];
        }
        this.borderEnabled = true;
        this.borderSize = 1.0f;
        this.borderColor = Color.black;
    }

    @Override
    public String toString() {
        return String.format("grid(x=%s,y=%s,w=%s,h=%s)", this.x, this.y, this.width, this.height);
    }
}
