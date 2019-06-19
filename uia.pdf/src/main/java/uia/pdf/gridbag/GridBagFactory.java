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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import uia.pdf.Layout;
import uia.pdf.PDFException;
import uia.pdf.PDFMaker;
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
import uia.pdf.gridbag.model.ImageCell;
import uia.pdf.gridbag.model.Row;
import uia.pdf.gridbag.model.TextCell;
import uia.pdf.papers.Paper;

public class GridBagFactory {

    private final LayoutType layoutType;

    private final TreeMap<String, GridBagType> gbts;
    
    public static GridBagFactory fromXml(String filename) throws Exception {
    	return new GridBagFactory(Layout.GRIDBAG_TYPE.fromXml(new File(filename)));
    }

    GridBagFactory(LayoutType layoutType) {
        this.layoutType = layoutType;
        this.gbts = new TreeMap<String, GridBagType>();
        for (GridBagType gbt : this.layoutType.getGridbag()) {
            this.gbts.put(gbt.getName(), gbt);
        }
    }
    
    public GridBagView mainView(PDFMaker pdf, Paper paper) throws PDFException {
    	return new GridBagView(pdf, paper.clone(), createModels());
    }
    
    public GridBagDescriptionView descView(PDFMaker pdf, String name) throws PDFException {
    	return new GridBagDescriptionView(
    			createModel(name));
    }

    public GridBagType getGridBagType(String name) {
        return this.gbts.get(name);
    }

    public GridBagModel createModel(String name) {
    	GridBagType gbt = this.gbts.get(name);

        GridBagModel grid = new GridBagModel(gbt);

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
        
        return grid;
    }

    public List<GridBagModel> createModels() {
        ArrayList<GridBagModel> grids = new ArrayList<GridBagModel>();
        for (GridBagType gbt : this.layoutType.getGridbag()) {
            GridBagModel grid = new GridBagModel(gbt);
            grids.add(grid);

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
        
        return grids;
    }
}
