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

package uia.pdf.grid.layout;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import uia.pdf.Layout;
import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.GridType.Columns;
import uia.pdf.grid.layout.LayoutType;

public class LayoutTypeTest {

    @Test
    public void testSample() throws Exception {
    	LayoutType layout = Layout.GRID_TYPE.fromXml(new File("sample/grid/layout.xml"));
        for (GridType table : layout.getGrid()) {
            System.out.println(table.getName());
            System.out.println("  - fontSize: " + table.getFontSize());
            Columns cols = table.getColumns();
            System.out.println(String.format("  columns height:%s, background:%s",
                    cols.getHeight(),
                    cols.getBackground()));
            for (ColumnType column : table.getColumns().getColumn()) {
                System.out.println(String.format("    - %s, width:%s, %s",
                        column.getBind(),
                        column.getWidth(),
                        column.getCellRenderer()));
            }
            System.out.println();
        }
        
        Assert.assertEquals(3, layout.getGrid().size());
        Assert.assertEquals(20, layout.getGrid().get(0).getColumns().getHeight());
        Assert.assertEquals("212,212,212", layout.getGrid().get(0).getColumns().getBackground());
        Assert.assertEquals(1, layout.getGrid().get(0).getColumns().getColumn().size());
    }
}
