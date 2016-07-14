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
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

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
public class GridView extends ContentView implements AbstractGridView {

    private final GridModel model;

    private int rowV;

    private int tableTop;

    private int columnH;

    private boolean columnEachPage;

    private int fontSize;

    private PDPage currPage;

    private boolean drawColumn;

    public GridView(PDFMaker pdf, Paper paper, GridModel model) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = true;
        this.fontSize = model.getFontSize();
        this.drawColumn = model.isHeaderVisible();
    }

    private GridView(PDFMaker pdf, Paper paper, GridModel model, int rowV, int tableTop, int columnH, boolean columnEachPage, PDPage currPage) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = columnEachPage;
        this.fontSize = model.getFontSize();
        this.drawColumn = model.isHeaderVisible();

        this.rowV = rowV;
        this.tableTop = tableTop;
        this.columnH = columnH;
        this.currPage = currPage;
    }

    public boolean isDrawColumn() {
        return this.drawColumn;
    }

    public void setDrawColumn(boolean drawColumn) {
        this.drawColumn = drawColumn;
    }

    public GridView create(GridModel model) {
        GridView gv = new GridView(
                this.pdf,
                this.paper,
                model,
                this.rowV,
                this.tableTop,
                this.columnH,
                this.columnEachPage,
                this.currPage);

        gv.setHeaderView(getHeaderView());
        gv.setFooterView(getFooterView());

        return gv;
    }

    public boolean isColumnEachPage() {
        return this.columnEachPage;
    }

    public void setColumnEachPage(boolean columnEachPage) {
        this.columnEachPage = columnEachPage;
    }

    @Override
    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void endPage() {
        this.currPage = null;
    }

    public void draw(List<Map<String, Object>> data, String bookmark) throws IOException {
        PDPage page = this.currPage;
        if (page == null) {
            page = newPage();
        }

        page = this.pdf.addBookmark(this, page, bookmark);
        page = drawColumns(page);

        if (data == null) {
            return;
        }
        int row = 0;
        for (Map<String, Object> rowCells : data) {
            page = drawRow(page, rowCells, row, false);
        }
        drawGridLine(page);
    }

    @Override
    public PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException {
        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, 14);
        for (PDOutlineItem oi : ois) {
            if (this.rowV - 16 < getBottom()) {
                contentStream.close();
                page = newPage();
                contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
                contentStream.setFont(font, 14);
            }
            contentStream.beginText();
            contentStream.newLineAtOffset(getLeft(), this.rowV - 16);
            contentStream.showText(oi.getTitle().trim());
            contentStream.endText();
            this.rowV -= 16;
        }
        contentStream.close();
        this.rowV -= 10;
        this.tableTop = this.rowV;

        return page;
    }

    private PDPage newPage() {
        this.rowV = getTop();
        this.tableTop = this.rowV;
        this.columnH = getLeft();

        PDPage page = this.paper.createPage();
        this.currPage = page;
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);

        return page;

    }

    private PDPage drawColumns(PDPage page) throws IOException {
        if (!this.drawColumn) {
            return page;
        }

        PDFont font = this.pdf.getFont();
        int h = PDFUtil.getContentHeight("", font, this.fontSize);
        if (this.rowV - (5 * h) < getBottom()) {
            page = newPage();
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, this.fontSize);

        DefaultCellRenderer renderer = new DefaultCellRenderer();

        ColumnModel[] cms = this.model.getColumnModels();
        for (int i = 0; i < cms.length; i++) {
            if (i == 0) {
                this.columnH = getLeft();
            }
            else {
                this.columnH += cms[i - 1].getWidth();
            }

            contentStream.setNonStrokingColor(new Color(232, 232, 232));
            contentStream.addRect(this.columnH, this.rowV - h - 8, cms[i].getWidth(), h + 8);
            contentStream.fill();
            contentStream.setNonStrokingColor(new Color(0, 0, 0));

            String content = cms[i].getDisplayName();
            int offset = (cms[i].getWidth() - PDFUtil.getContentWidth(content, font, this.fontSize)) / 2;
            if (this.columnH + offset > getRight()) {
                break;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(this.columnH + offset, this.rowV - h - 3);
            contentStream.showText(content);
            contentStream.endText();
        }
        contentStream.moveTo(getLeft(), this.rowV - h - 8);
        contentStream.lineTo(getRight(), this.rowV - h - 8);
        contentStream.stroke();
        this.rowV -= (h + 8);
        contentStream.close();

        return page;
    }

    private PDPage drawRow(PDPage page, Map<String, Object> rowCells, int row, boolean forceNewPage) throws IOException {
        PDPage currPage = page;
        if (forceNewPage || (this.rowV - 12) < getBottom()) {
            drawGridLine(page);
            currPage = newPage();
            if (this.columnEachPage) {
                currPage = drawColumns(currPage);
            }
        }

        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), currPage, AppendMode.APPEND, false, false);
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

            contentStream.setLineWidth(0.1f);
            contentStream.moveTo(getLeft(), this.rowV);
            contentStream.lineTo(getRight(), this.rowV);
            contentStream.stroke();

            contentStream.close();
        }

        return currPage;
    }

    private void drawGridLine(PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setLineWidth(1.0f);

        ColumnModel[] cms = this.model.getColumnModels();
        int columnH = getLeft();
        for (int i = 0; i < cms.length; i++) {
            columnH += cms[i].getWidth();
            contentStream.moveTo(columnH, this.tableTop);
            contentStream.lineTo(columnH, this.rowV);
            contentStream.stroke();

        }
        contentStream.addRect(
                getLeft(),
                this.rowV,
                (float) this.getPaper().getDrawableSize().getWidth(),
                this.tableTop - this.rowV);
        contentStream.stroke();

        contentStream.close();

        this.rowV -= 10;
    }
}
