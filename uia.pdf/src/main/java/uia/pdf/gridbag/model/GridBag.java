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

import uia.pdf.PDFUtil;
import uia.pdf.gridbag.GridBagLayout;
import uia.pdf.gridbag.layout.GridBagType;

/**
 * GridBag.
 *
 * @author Kan Lin
 *
 */
public class GridBag {

    public final String name;

    public final int x;

    public final int y;

    public final int width;

    public final int height;

    public final boolean borderEnabled;

    public final float borderSize;

    public final Color borderColor;

    public final int fontSize;

    public final Color background;

    public final Column[] columns;

    public final Row[] rows;

    public final Cell[][] cells;

    public GridBag(GridBagType gt, int viewWidth, int viewHeight) {
        this.name = gt.getName();
        this.x = GridBagLayout.calculate(gt.getX(), viewWidth, 0);
        this.y = GridBagLayout.calculate(gt.getY(), viewHeight, 0);
        this.width = GridBagLayout.calculate(gt.getWidth(), viewWidth, this.x);
        this.height = GridBagLayout.calculate(gt.getHeight(), viewHeight, this.y);
        this.borderEnabled = gt.isBorderEnabled();
        this.borderSize = gt.getBorderSize();
        this.borderColor = PDFUtil.toColor(gt.getBorderColor());
        this.fontSize = gt.getFontSize();
        this.background = PDFUtil.toColor(gt.getBackground());
        int rc = gt.getRows().getRow().size();
        int cc = gt.getColumns().getColumn().size();
        this.columns = new Column[gt.getColumns().getColumn().size()];
        this.rows = new Row[rc];
        this.cells = new Cell[rc][];
        for (int i = 0; i < rc; i++) {
            this.cells[i] = new Cell[cc];
        }
    }

    @Override
    public String toString() {
        return String.format("grid(x=%s,y=%s,w=%s,h=%s) name:%s", this.x, this.y, this.width, this.height, this.name);
    }
}
