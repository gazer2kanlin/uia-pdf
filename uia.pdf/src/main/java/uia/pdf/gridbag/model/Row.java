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
import uia.pdf.gridbag.layout.RowType;

/**
 * Row.
 *
 * @author Kan Lin
 *
 */
public class Row {

    private final RowType rt;

    public final GridBagModel grid;

    public final int index;

    public final Color background;

    public float borderSize;

    public final Color borderColor;

    private int y;

    private int height;

    public Row(RowType rt, GridBagModel grid, int rowIndex) {
        this.rt = rt;
        this.grid = grid;
        this.index = rowIndex;
        this.background = DrawingUtils.toColor(rt.getBackground());
        this.borderSize = rt.getBorderSize();
        this.borderColor = DrawingUtils.toColor(rt.getBorderColor());
    }

	public int getY() {
    	if(this.index == 0) {
    		return this.y;
    	}
    	
    	Row prev = this.grid.rows[this.index - 1];
    	return prev.getY() + prev.getHeigth();
    }
	
	public int getHeigth(){
		return this.height;
	}
	
	public void arrange(int y) {
        this.y = y;
        this.height = CoordUtils.size(
        		rt.getHeight(), 
        		grid.getHeight(), 
        		this.y - grid.getY());
	}

    @Override
    public String toString() {
        return String.format("row[%2s](x=%3s,y=%3s,w=%3s,h=%3s)", 
        		this.index, 
        		this.grid.getX(), 
        		this.y, 
        		this.grid.getWidth(), 
        		this.height);
    }

    public int getFontSize() {
        return this.rt.getFontSize() != null ? this.rt.getFontSize().intValue() : this.grid.fontSize;
    }
}
