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

import uia.pdf.Layout;
import uia.pdf.PDFDoc;
import uia.pdf.gridbag.layout.BindCellType;
import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.ImageCellType;
import uia.pdf.gridbag.layout.RowType;
import uia.pdf.gridbag.layout.TextCellType;
import uia.pdf.gridbag.model.BindCell;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.Column;
import uia.pdf.gridbag.model.ImageCell;
import uia.pdf.gridbag.model.Row;
import uia.pdf.gridbag.model.TextCell;
import uia.pdf.papers.Paper;

public abstract class GridBagFactory {

    public static GridBagFactory fromXml(String filename) throws Exception {
    	return new GridBagXmlFactory(Layout.GRIDBAG_TYPE.fromXml(new File(filename)));
    }
    
    public abstract GridBagView mainView(PDFDoc pdf, Paper paper);
    
    public abstract GridBagDescriptionView descView(PDFDoc pdf, String name);

    protected GridBagModel createModel(GridBagType gbt) {
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
                if (ct instanceof BindCellType) {
                    cell = new BindCell((BindCellType) ct, grid, rowIndex, cellIndex);
                }
                else if (ct instanceof ImageCellType) {
                    cell = new ImageCell((ImageCellType) ct, grid, rowIndex, cellIndex);
                }
                else {
                    cell = new TextCell((TextCellType) ct, grid, rowIndex, cellIndex);
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
}
