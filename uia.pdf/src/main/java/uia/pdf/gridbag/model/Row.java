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
import uia.pdf.gridbag.layout.RowType;

/**
 * Row.
 *
 * @author Kan Lin
 *
 */
public class Row {

    public final GridBag grid;

    public final int index;

    public final int y;

    public final int height;

    public final Color background;

    public float borderSize;

    public final Color borderColor;

    private final RowType rt;

    public Row(RowType rt, GridBag grid, int rowIndex) {
        this.grid = grid;
        this.rt = rt;
        this.index = rowIndex;
        this.y = this.index == 0 ? grid.y : grid.rows[this.index - 1].y + grid.rows[this.index - 1].height;
        this.height = GridBagLayout.calculate(rt.getHeight(), grid.height, this.y - grid.y);
        this.background = PDFUtil.toColor(rt.getBackground());
        this.borderSize = rt.getBorderSize();
        this.borderColor = PDFUtil.toColor(rt.getBorderColor());
    }

    @Override
    public String toString() {
        return String.format("row[%2s](x=%3s,y=%3s,w=%3s,h=%3s)", this.index, this.grid.x, this.y, this.grid.width, this.height);
    }

    public int getFontSize() {
        return this.rt.getFontSize() != null ? this.rt.getFontSize().intValue() : this.grid.fontSize;
    }
}
