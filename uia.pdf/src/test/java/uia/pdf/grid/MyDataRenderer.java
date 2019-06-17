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

package uia.pdf.grid;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.grid.data.MyData;

public class MyDataRenderer extends DefaultCellRenderer {

    @Override
    public int paint(PDPageContentStream contentStream, Point topLeft, AbstractGridView view, ColumnModel cm, Object value, int row, int col) {
    	MyData md = (MyData)value;
    	if(md != null) {
            return super.paint(contentStream, topLeft, view, cm, md.getName(), row, col);
    	}
    	else {
            return super.paint(contentStream, topLeft, view, cm, "", row, col);
    	}
    }

}
