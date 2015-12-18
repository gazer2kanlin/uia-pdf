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
import java.io.File;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import uia.pdf.ContentView;
import uia.pdf.gridbag.GridBagDrawer;
import uia.pdf.gridbag.layout.ImageCellType;

/**
 * Text cell.
 *
 * @author Kan Lin
 *
 */
public class ImageCell extends Cell {

    private String fileName;

    public ImageCell(ImageCellType ict, GridBag grid, int rowIndex, int columnIndex) {
        super(ict, grid, rowIndex, columnIndex);
        this.fileName = ict.getUrl();
    }

    @Override
    public void accept(ContentView cv, GridBagDrawer view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data) {
        PDImageXObject img = null;
        try {
            img = PDImageXObject.createFromFile(
                    new File(this.fileName),
                    cv.getDoc().getDocument());
            float pct = (float) getHeight() / (float) img.getHeight();
            contentStream.drawImage(
                    img,
                    bottomLeft.x,
                    bottomLeft.y,
                    img.getWidth() * pct,
                    img.getHeight() * pct);
        }
        catch (Exception ex) {

        }
    }

    @Override
    public String toString() {
        return String.format("textCell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s) url:%s",
                this.row,
                this.col,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                this.fileName);
    }
}
