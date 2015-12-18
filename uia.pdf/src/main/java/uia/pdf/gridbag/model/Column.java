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
import uia.pdf.gridbag.layout.ColumnType;

/**
 * Column.
 *
 * @author Kan Lin
 *
 */
public class Column {

    /**
     * Grid.
     */
    public final GridBag grid;

    /**
     * Index.
     */
    public final int index;

    /**
     * x
     */
    public final int x;

    /**
     * Width.
     */
    public final int width;

    /**
     * Background color.
     */
    public final Color background;

    public Column(ColumnType ct, GridBag grid, int columnIndex) {
        this.grid = grid;
        this.index = columnIndex;
        this.x = this.index == 0 ? grid.x : grid.columns[this.index - 1].x + grid.columns[this.index - 1].width;
        this.width = GridBagLayout.calculate(ct.getWidth(), grid.width, this.x - grid.x);
        this.background = PDFUtil.toColor(ct.getBackground());
    }

    @Override
    public String toString() {
        return String.format("col[%2s](x=%3s,y=%3s,w=%3s)", this.index, this.x, this.grid.y, this.width);
    }

}
