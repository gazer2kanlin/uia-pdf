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

import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;
import uia.pdf.gridbag.GridBagModel;
import uia.pdf.gridbag.layout.BindCellType;
import uia.pdf.renderers.DataBlock;
import uia.pdf.renderers.DataRenderer;
import uia.pdf.renderers.TextDataRenderer;
import uia.utils.PropertyBeanUtils;

/**
 * Binding cell.
 *
 * @author Kan Lin
 *
 */
public class BindCell extends Cell {
	
	private static final TextDataRenderer TEXT_DATA_RENDERER = new TextDataRenderer();

    public final BindCellType bct;

    public BindCell(BindCellType bct, GridBagModel grid, int rowIndex, int columnIndex) {
        super(bct, grid, rowIndex, columnIndex);
        this.bct = bct;
    }

    @Override
    public void draw(PDPageContentStream contentStream, Point topLeft, ContentView cv, Object value) {
        try {
        	String bindName = this.bct.getBind();
        	if(bindName == null || "".equals(bindName)) {
        		return;
        		
        	}
        	@SuppressWarnings("rawtypes")
			Object data = value instanceof Map 
        			? ((Map)value).get(this.bct.getBind()) 
        			: PropertyBeanUtils.read(value, bct.getBind());
            if (data == null) {
                return;
            }
            
            DataBlock block = new DataBlock(
    				getX(), 
    				getY(), 
    				getWidth(), 
    				getHeight(), 
    				getFontSize(), 
    				DataBlock.AlignmentType.valueOf(getAlignment()),
    				DataBlock.AlignmentType.valueOf(getVAlignment()));
            
            String cls = this.bct.getRenderer();
            DataRenderer renderer = cls == null 
            		? TEXT_DATA_RENDERER
            		: (DataRenderer)Class.forName(cls).newInstance();
            renderer.paint(
            		contentStream, 
            		topLeft, 
            		cv, 
            		data,
            		block);
        }
        catch (Exception ex) {
        	
        }
    }

    @Override
    public String toString() {
        return String.format("bindCell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s) id:%s",
                this.row,
                this.col,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                this.bct.getBind());
    }
}
