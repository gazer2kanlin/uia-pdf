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

package uia.pdf.gridbag;

import java.awt.Color;

import uia.pdf.CoordUtils;
import uia.pdf.DrawingUtils;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.Column;
import uia.pdf.gridbag.model.Row;

/**
 * GridBag.
 *
 * @author Kan Lin
 *
 */
public class GridBagModel {
	
	private final GridBagType gt;

    public final String name;

    public final boolean borderEnabled;

    public final float borderSize;

    public final Color borderColor;

    public final int fontSize;

    public final Color background;

    public final Column[] columns;

    public final Row[] rows;

    public final Cell[][] cells;

    private int x;

    private int y;

    private int width;

    private int height;

    public GridBagModel(GridBagType gt) {
    	this.gt = gt;
        this.name = gt.getName();
        this.borderEnabled = gt.isBorderEnabled();
        this.borderSize = gt.getBorderSize();
        this.borderColor = DrawingUtils.toColor(gt.getBorderColor());
        this.fontSize = gt.getFontSize();
        this.background = DrawingUtils.toColor(gt.getBackground());
        int rc = gt.getRows().getRow().size();
        int cc = gt.getColumns().getColumn().size();
        this.columns = new Column[gt.getColumns().getColumn().size()];
        this.rows = new Row[rc];
        this.cells = new Cell[rc][];
        for (int i = 0; i < rc; i++) {
            this.cells[i] = new Cell[cc];
        }
    }
    
    public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void arrange(int startY, int viewWidth, int viewHeight) {
        this.x = CoordUtils.point(this.gt.getX(), viewWidth, 0);
        this.y = CoordUtils.point(this.gt.getY(), viewHeight, startY);
        this.width = CoordUtils.size(this.gt.getWidth(), viewWidth, 0);
        this.height = CoordUtils.size(this.gt.getHeight(), viewHeight, startY);

        System.out.println(this);
        
        int colX = getX();
        for(Column column : this.columns) {
        	column.arrange(colX);
        	System.out.println(column);
        	colX = column.getX() + column.getWidth();
        }
        
        int rowY = getY();
        for(Row row : this.rows) {
        	row.arrange(rowY);
        	System.out.println(row);
        	rowY = row.getY() + row.getHeigth();
        }

        System.out.println();
	}

	@Override
    public String toString() {
        return String.format("%-10s:(x=%-3s, y=%-3s, w=%-4s, h=%-4s)",  this.name, this.x, this.y, this.width, this.height);
    }
}
