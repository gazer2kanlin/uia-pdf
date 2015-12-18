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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import uia.pdf.gridbag.layout.BindCellType;
import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.ImageCellType;
import uia.pdf.gridbag.layout.LayoutType;
import uia.pdf.gridbag.layout.RowType;
import uia.pdf.gridbag.layout.TextCellType;
import uia.pdf.gridbag.model.BindCell;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.Column;
import uia.pdf.gridbag.model.GridBag;
import uia.pdf.gridbag.model.ImageCell;
import uia.pdf.gridbag.model.Row;
import uia.pdf.gridbag.model.TextCell;

public class GridBagLayout {

    private LayoutType layoutType;

    private ArrayList<GridBag> grids;

    private TreeMap<String, GridBagType> gbts;

    private int viewWidth;

    private int viewHeight;

    public GridBagLayout(LayoutType layoutType) {
        this.grids = new ArrayList<GridBag>();
        this.layoutType = layoutType;
        this.gbts = new TreeMap<String, GridBagType>();
        for (GridBagType gbt : this.layoutType.getGridbag()) {
            this.gbts.put(gbt.getName(), gbt);
        }
    }

    public GridBagType getGridBagType(String name) {
        return this.gbts.get(name);
    }

    /**
     * Get grids.
     * @return Grids.
     */
    public List<GridBag> getGrids() {
        return this.grids;
    }

    /**
     *
     * @param viewWidth
     * @param viewHeight
     */
    public void load(int viewWidth, int viewHeight) {
        if (this.viewWidth == viewWidth && this.viewHeight == viewHeight) {
            return;
        }

        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.grids.clear();
        for (GridBagType gbt : this.layoutType.getGridbag()) {
            // grid
            GridBag grid = new GridBag(gbt, viewWidth, viewHeight);
            this.grids.add(grid);

            // columns
            int colIndex = 0;
            for (ColumnType ct : gbt.getColumns().getColumn()) {
                Column col = new Column(ct, grid, colIndex);
                grid.columns[colIndex] = col;
                colIndex++;
            }

            // rows
            int rowIndex = 0;
            for (RowType rt : gbt.getRows().getRow()) {
                Row row = new Row(rt, grid, rowIndex);
                grid.rows[rowIndex] = row;

                // cells
                int cellIndex = 0;
                for (CellType ct : rt.getTextCellOrBindCellOrImageCell()) {
                    // handle column span
                    while (grid.cells[rowIndex][cellIndex] != null) {
                        cellIndex++;
                    }

                    Cell cell = null;
                    if (ct instanceof TextCellType) {
                        cell = new TextCell((TextCellType) ct, grid, rowIndex, cellIndex);
                    }
                    else if (ct instanceof BindCellType) {
                        cell = new BindCell((BindCellType) ct, grid, rowIndex, cellIndex);
                    }
                    else if (ct instanceof ImageCellType) {
                        cell = new ImageCell((ImageCellType) ct, grid, rowIndex, cellIndex);
                    }
                    else {
                        cell = new Cell(ct, grid, rowIndex, cellIndex);
                    }

                    // handle row & column span
                    for (int cs = 0; cs < cell.colspan; cs++) {
                        for (int rs = 0; rs < cell.rowspan; rs++) {
                            grid.cells[rowIndex + rs][cellIndex] = cell;
                        }
                        cellIndex++;
                    }
                }

                rowIndex++;
            }
        }
    }

    public static int calculate(String value, int total, int offset) {
        if (value.endsWith("%")) {
            if (value.startsWith("+")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return (total - offset) * p / 100;
            }
            else if (value.startsWith("*")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return total * p / 100 - offset;
            }
            else {
                int p = Integer.parseInt(value.substring(0, value.length() - 1));
                return total * p / 100;
            }
        }
        else {
            return Integer.parseInt(value);
        }
    }
}
