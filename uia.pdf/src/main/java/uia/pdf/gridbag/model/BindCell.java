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

import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import uia.pdf.ContentView;
import uia.pdf.gridbag.GridBagDrawer;
import uia.pdf.gridbag.layout.BindCellType;

/**
 * Binding cell.
 *
 * @author Kan Lin
 *
 */
public class BindCell extends Cell {

    public final String id;

    public BindCell(BindCellType bct, GridBag grid, int rowIndex, int columnIndex) {
        super(bct, grid, rowIndex, columnIndex);
        this.id = bct.getId();
    }

    @Override
    public void accept(ContentView cv, GridBagDrawer view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data) {
        try {
            Object value = data == null ? null : data.get(this.id);
            if (value == null) {
                return;
            }

            view.getBindRenderer(this.id, value).paint(contentStream, bottomLeft, cv, this, value);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public String toString() {
        return String.format("bindCell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s) id:%s",
                this.row,
                this.col,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                this.id);
    }
}
