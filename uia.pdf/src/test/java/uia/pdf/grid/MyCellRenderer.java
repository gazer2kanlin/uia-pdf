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

public class MyCellRenderer extends DefaultCellRenderer {

    @Override
    public int paint(PDPageContentStream contentStream, Point topLeft, AbstractGridView view, ColumnModel cm, Object value, int row, int col) {
        if (col == 4) {
            if (((Boolean) value).booleanValue()) {
                return super.paint(contentStream, topLeft, view, cm, "正確", row, col);
            }
            else {
                return super.paint(contentStream, topLeft, view, cm, "錯誤", row, col);
            }
        }
        else {
            return super.paint(contentStream, topLeft, view, cm, value, row, col);
        }
    }

}
