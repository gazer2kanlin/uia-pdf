/*
 * Copyright 2015 uia.pdf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uia.pdf.gridbag.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;
import uia.pdf.DrawingUtils;
import uia.pdf.gridbag.GridBagDrawer;
import uia.pdf.gridbag.GridBagModel;
import uia.pdf.gridbag.layout.CellType;

/**
 * Cell.
 *
 * @author Kan Lin
 *
 */
public class Cell {

    public final GridBagModel grid;

    public final int row;

    public final int col;

    public final int rowspan;

    public final int colspan;

    public final float borderSize;

    public final Color borderColor;

    protected CellType ct;

    public Cell(CellType ct, GridBagModel grid, int rowIndex, int columnIndex) {
        this.grid = grid;
        this.ct = ct;
        this.row = rowIndex;
        this.col = columnIndex;
        this.rowspan = ct.getRowspan();
        this.colspan = ct.getColspan();
        this.borderSize = ct.getBorderSize();
        this.borderColor = DrawingUtils.toColor(ct.getBorderColor());
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
        return this.grid.columns[this.col].getX();
    }

    public int getY() {
        return this.grid.rows[this.row].getY();
    }

    public int getWidth() {
        int w = 0;
        for (int i = 0; i < this.colspan; i++) {
            w += this.grid.columns[this.col + i].getWidth();
        }
        return w;
    }

    public int getHeight() {
        int h = 0;
        for (int i = 0; i < this.rowspan; i++) {
            h += this.grid.rows[this.row + i].getHeigth();
        }
        return h;
    }

    public int getFontSize() {
        if (this.ct.getFontSize() != null) {
            return this.ct.getFontSize().intValue();
        }
        else {
            return this.grid.rows[this.row].getFontSize();
        }
    }

    public Color getBackground() {
        if (this.ct.getBackground() != null) {
            return DrawingUtils.toColor(this.ct.getBackground());
        }
        else if (this.grid.rows[this.row].background != null) {
            return this.grid.rows[this.row].background;
        }
        else if (this.grid.columns[this.col].background != null) {
            return this.grid.columns[this.col].background;
        }
        else {
            return null;
        }
    }

    public String getAlignment() {
        return this.ct.getAlignment();
    }

    public String getVAlignment() {
        return this.ct.getValignment();
    }

    @Override
    public String toString() {
        return String.format("cell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s)", this.row, this.col, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Draw nothing.
     * @param view View.
     * @param contentStream Content stream.
     * @param topLeft Coordinate of bottom-left.
     * @param data Data used to draw.
     */
    public void accept(ContentView cv, GridBagDrawer view, PDPageContentStream contentStream, Point topLeft, Map<String, Object> data) {
    }
}
