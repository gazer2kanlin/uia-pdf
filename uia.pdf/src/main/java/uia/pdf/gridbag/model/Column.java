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

import uia.pdf.CoordUtils;
import uia.pdf.DrawingUtils;
import uia.pdf.gridbag.GridBagModel;
import uia.pdf.gridbag.layout.ColumnType;

/**
 * Column.
 *
 * @author Kan Lin
 *
 */
public class Column {
	
	public final ColumnType ct;

    public final GridBagModel grid;

    public final int index;

    public final Color background;

    private int x;

    private int width;

    public Column(ColumnType ct, GridBagModel grid, int columnIndex) {
    	this.ct = ct;
        this.grid = grid;
        this.index = columnIndex;
        this.background = DrawingUtils.toColor(ct.getBackground());
    }
    
    public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	public void arrange(int x) {
        this.x = x;
        this.width = CoordUtils.size(
        		this.ct.getWidth(), 
        		this.grid.getWidth(), 
        		this.x - grid.getX());
    }

    @Override
    public String toString() {
        return String.format("col[%2s](x=%3s,y=%3s,w=%3s)", 
        		this.index, 
        		this.x, 
        		this.grid.getY(), 
        		this.width);
    }

}
