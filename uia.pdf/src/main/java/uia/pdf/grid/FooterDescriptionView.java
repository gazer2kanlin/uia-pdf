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

package uia.pdf.grid;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.DescriptionView;
import uia.pdf.PDFMaker;
import uia.pdf.PDFUtil;
import uia.pdf.papers.Paper;

/**
 * Present content with grid.
 *
 * @author Kan Lin
 *
 */
public class FooterDescriptionView extends DescriptionView implements AbstractGridView {

    private final PDFMaker pdf;

    private final Paper paper;

    private final GridModel model;

    private int rowV;

    private int height;

    private List<Map<String, Object>> data;

    public FooterDescriptionView(PDFMaker pdf, Paper paper, GridModel model, int height) {
        this.pdf = pdf;
        this.paper = paper;
        this.model = model;
        this.height = height;
    }

    public List<Map<String, Object>> getData() {
        return this.data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
        int h = PDFUtil.getContentHeight("A", this.pdf.getFont(), this.model.getFontSize());
        this.height = this.data.size() * (h + 4) + h;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public PDFMaker getDoc() {
        return this.pdf;
    }

    @Override
    public int getFontSize() {
        return this.model.getFontSize();
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        this.rowV = this.paper.getBottom() + this.height;
        int row = 0;
        for (Map<String, Object> rowCells : this.data) {
            drawRow(page, rowCells, row++);
        }
    }

    private void drawRow(PDPage page, Map<String, Object> rowCells, int row) throws IOException {
        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);
        contentStream.setFont(font, this.model.getFontSize());

        int h = Short.MIN_VALUE;
        int columnH = 0;
        ColumnModel[] cms = this.model.getColumnModels();
        for (int col = 0; col < cms.length; col++) {
            ColumnModel cm = cms[col];
            if (col == 0) {
                columnH = this.paper.getLeft();
            }
            else {
                columnH += cms[col - 1].getWidth();
            }

            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(columnH, this.rowV), this, cm, rowCells.get(cm.getId()), row, col));
        }
        this.rowV -= h;

        contentStream.close();
    }
}
