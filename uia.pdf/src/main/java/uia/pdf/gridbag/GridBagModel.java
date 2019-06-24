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
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.CoordUtils;
import uia.pdf.DrawingUtils;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.Column;
import uia.pdf.gridbag.model.Row;
import uia.utils.ObjectUtils;

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
	
    void drawOn(ContentView cv, PDPage page, Object value) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(
        		cv.getDoc().getDocument(), 
        		page, 
        		AppendMode.APPEND, 
        		false, 
        		false);
        PDFont font = cv.getDoc().getFont();
        contentStream.setFont(font, 9);
        contentStream.setLineWidth(0.5f);

        Point topLeft = cv.getDrawingTopLeft();
        if (this.background != null) {
            contentStream.setNonStrokingColor(this.background);
            contentStream.addRect(
            		topLeft.x + getX(), 
            		topLeft.y - getY() - getHeight(), 
            		getWidth(), 
            		getHeight());
            contentStream.fill();
        }

        ArrayList<Cell> colorBorder = new ArrayList<Cell>();
        // draw grid
        if (this.borderEnabled) {
        	int r = -1;
            for (Cell[] cells : this.cells) {
            	r++;
            	int c = -1;
                for (Cell cell : cells) {
                	c++;
                	System.out.println(r + "," + c);
                    contentStream.setLineWidth(cell.borderSize);

                    Point cellBottomLeft = cell.drawingBottomLeft(topLeft);
                    Color background = cell.getBackground();
                    if (background != null) {
                        contentStream.setNonStrokingColor(background);
                        contentStream.addRect(
                        		cellBottomLeft.x, 
                        		cellBottomLeft.y, 
                        		cell.getWidth(), 
                        		cell.getHeight());
                        contentStream.fill();
                    }

                    contentStream.addRect(
                    		cellBottomLeft.x, 
                    		cellBottomLeft.y, 
                    		cell.getWidth(), 
                    		cell.getHeight());
                    contentStream.stroke();

                    if (cell.borderColor != null) {
                        colorBorder.add(cell);
                    }
                }
            }

            // draw border
            for (Cell cell : colorBorder) {
                Point cellBottomLeft = cell.drawingBottomLeft(topLeft);
                contentStream.setLineWidth(cell.borderSize);
                contentStream.setStrokingColor(cell.borderColor);
                contentStream.addRect(
                		cellBottomLeft.x, 
                		cellBottomLeft.y, 
                		cell.getWidth(), 
                		cell.getHeight());
                contentStream.stroke();
            }
        }

        // draw data
        Object bindData = bind(value);
        int r = 0;
        for (Cell[] cells : this.cells) {
            int c = 0;
            for (Cell cell : cells) {
                if (cell.col != c && cell.row != r) {
                    continue;
                }
                cell.draw(
                		contentStream, 
                		cell.drawingTopLeft(topLeft), 
                		cv, 
                		bindData);
                c++;
            }
            r++;
        }

        if (this.borderEnabled) {
            contentStream.setStrokingColor(this.borderColor);
            contentStream.setLineWidth(1.0f);
            contentStream.addRect(
            		topLeft.x + getX(), 
            		topLeft.y - getHeight() - getY(), 
            		getWidth(), 
            		getHeight());
            contentStream.stroke();
        }

        contentStream.setStrokingColor(Color.black);
        contentStream.close();
    }
    
    @SuppressWarnings("rawtypes")
	private Object bind(Object value) {
    	if(value == null) {
    		return null;
    	}
    	
    	String name = this.gt.getBind();
    	if(name == null || "".equals(name)) {
    		return value;
    	}
    	
    	try {
	    	if(value instanceof Map) {
	    		return ((Map)value).get(name);
	    	}
	    	else {
	    		return ObjectUtils.read(value, name);
	    	}
    	}
    	catch(Exception ex) {
    		return null;
    	}
    }

	@Override
    public String toString() {
        return String.format("%-10s:(x=%-3s, y=%-3s, w=%-4s, h=%-4s)",  this.name, this.x, this.y, this.width, this.height);
    }
}
