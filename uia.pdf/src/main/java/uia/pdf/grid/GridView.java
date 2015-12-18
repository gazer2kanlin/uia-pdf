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

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFMaker;
import uia.pdf.PDFUtil;
import uia.pdf.papers.Paper;

/**
 * Present content with grid.
 *
 * @author Kan Lin
 *
 */
public class GridView extends ContentView {

    private final GridModel model;

    private int rowV;

    private int columnH;

    private boolean columnEachPage;

    private int fontSize;

    private String bookmarkGroup;

    public GridView(PDFMaker pdf, Paper paper, GridModel model) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = true;
        this.fontSize = 11;
    }

    public boolean isColumnEachPage() {
        return this.columnEachPage;
    }

    public void setColumnEachPage(boolean columnEachPage) {
        this.columnEachPage = columnEachPage;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public void beginBookmarkGroup() {
        this.pdf.beginBookmarkGroup();
    }

    @Override
    public void beginBookmarkGroup(String bookmarkGroup) {
        this.bookmarkGroup = bookmarkGroup;
    }

    @Override
    public void endBookmarkGroup() {
        this.bookmarkGroup = null;
        this.pdf.endBookmarkGroup();
    }

    public void draw(List<Map<String, Object>> data, String bookmark) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        if (this.bookmarkGroup != null) {
            this.pdf.addBookmark(page, this.bookmarkGroup);
            this.bookmarkGroup = null;
            this.pdf.beginBookmarkGroup();
        }
        this.pdf.addBookmark(page, bookmark);

        this.rowV = getTop();
        this.columnH = getLeft();
        drawColumns(page);

        if (data == null) {
            return;
        }
        int row = 0;
        for (Map<String, Object> rowCells : data) {
            page = drawRow(page, rowCells, row, false);
        }
        drawGridLine(page);
    }

    private void drawColumns(PDPage page) throws IOException {
        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);
        contentStream.setFont(font, this.fontSize);

        int h = PDFUtil.getStringHeight("", font, this.fontSize);

        ColumnModel[] cms = this.model.getColumnModels();
        for (int i = 0; i < cms.length; i++) {
            if (i == 0) {
                this.columnH = getLeft();
            }
            else {
                this.columnH += cms[i - 1].getWidth();
            }

            String content = cms[i].getDisplayName();
            int offset = (cms[i].getWidth() - PDFUtil.getStringWidth(content, font, this.fontSize)) / 2;
            if (this.columnH + offset > getRight()) {
                break;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(this.columnH + offset, getTop() - h - 3);
            contentStream.showText(content);
            contentStream.endText();
        }
        contentStream.moveTo(getLeft(), getTop() - h - 8);
        contentStream.lineTo(getRight(), getTop() - h - 8);
        contentStream.stroke();
        this.rowV -= (h + 8);
        contentStream.close();
    }

    private PDPage drawRow(PDPage page, Map<String, Object> rowCells, int row, boolean forceNewPage) throws IOException {
        PDPage currPage = page;
        if (forceNewPage || (this.rowV - 12) < getBottom()) {
            drawGridLine(page);
            this.rowV = getTop();
            currPage = this.paper.createPage();
            this.pdf.getDocument().addPage(currPage);
            this.pages.add(currPage);
            if (this.columnEachPage) {
                drawColumns(currPage);
            }
        }

        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), currPage, true, false, false);
        contentStream.setFont(font, this.fontSize);

        ColumnModel[] cms = this.model.getColumnModels();
        int h = Short.MIN_VALUE;
        for (int col = 0; col < cms.length; col++) {
            ColumnModel cm = cms[col];
            if (col == 0) {
                this.columnH = getLeft();
            }
            else {
                this.columnH += cms[col - 1].getWidth();
            }

            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(this.columnH, this.rowV), this, cm, rowCells.get(cm.getId()), row, col));
        }
        this.rowV -= h;

        // handle overlap at footer area
        if (!forceNewPage && this.rowV < getBottom()) {
            contentStream.setNonStrokingColor(Color.white);
            contentStream.addRect(
                    getLeft(),
                    this.rowV,
                    (float) this.getPaper().getDrawableSize().getWidth(),
                    h);
            contentStream.fill();
            contentStream.setNonStrokingColor(Color.black);
            contentStream.close();
            this.rowV += h;
            return drawRow(page, rowCells, row, true);
        }
        else {
            contentStream.close();
        }

        return currPage;
    }

    private void drawGridLine(PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);

        ColumnModel[] cms = this.model.getColumnModels();
        int columnH = getLeft();
        for (int i = 0; i < cms.length; i++) {
            columnH += cms[i].getWidth();
            contentStream.moveTo(columnH, getTop());
            contentStream.lineTo(columnH, this.rowV);
            contentStream.stroke();

        }
        contentStream.addRect(
                getLeft(),
                this.rowV,
                (float) this.getPaper().getDrawableSize().getWidth(),
                getTop() - this.rowV);
        contentStream.stroke();

        contentStream.close();
    }
}
