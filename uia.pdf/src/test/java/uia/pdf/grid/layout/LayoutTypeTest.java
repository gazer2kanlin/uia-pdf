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

import org.junit.Test;

import uia.pdf.Layout;
import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.LayoutType;

public class LayoutTypeTest {

    @Test
    public void testSample() throws Exception {
    	LayoutType layout = Layout.GRID_TYPE.fromXml(new File("sample/grid/layout.xml"));
        //  GridTypeHelper.load(new File("sample/grid/sample.xml"));
        for (GridType table : layout.getGrid()) {
            System.out.println(table.getName());
            System.out.println("    fontSize: " + table.getFontSize());
            for (ColumnType column : table.getColumns().getColumn()) {
                System.out.println(String.format("  [%-10s] width:%s, cr:%s",
                        column.getText(),
                        column.getWidth(),
                        column.getCellRenderer()));
                if (column.getCellRenderer() != null) {
                    System.out.println(Class.forName(column.getCellRenderer()).newInstance());
                }
            }
        }
    }
}
