/*
 * Copyright ${year} uia.pdf
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

package uia.pdf.gridbag.layout;

import java.io.File;

import org.junit.Test;

import uia.pdf.Layout;
import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.LayoutType;
import uia.pdf.gridbag.layout.RowType;

public class LayoutTypeTest {

    @Test
    public void testLayout() throws Exception {
        LayoutType layout = Layout.GRIDBAG_TYPE.fromXml(new File("sample/gridbag/layout.xml"));
        print(layout);
    }

    private void print(LayoutType layout) {
        for (GridBagType grid : layout.getGridbag()) {
            System.out.println(String.format("gridbg: width:%s, height:%s, border:%s",
                    grid.getWidth(),
                    grid.getHeight(),
                    grid.getBorderSize()));

            int c = 1;
            System.out.println("  columns");
            for (ColumnType column : grid.getColumns().getColumn()) {
                System.out.println(String.format("    - %s: width:%5s",
                        c++,
                        column.getWidth()));
            }

            int r = 1;
            for (RowType row : grid.getRows().getRow()) {
                System.out.println(String.format("  rows[%s]: height:%s, border:%s",
                        r,
                        row.getHeight(),
                        row.getBorderSize()));
                int ce = 1;
                for (CellType cell : row.getTextCellOrBindCellOrImageCell()) {
                    System.out.println(String.format("    %s: %-15s, colSpan:%s, rowSpan:%s, border:%s",
                            ce++,
                    		cell.getClass().getSimpleName(),
                            cell.getColspan(),
                            cell.getRowspan(),
                            cell.getBorderSize()));
                }
                r++;
            }
        }
    }
}
