package uia.pdf.grid;

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

    public GridView(PDFMaker pdf, Paper paper, GridModel model) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = true;
    }

    public boolean isColumnEachPage() {
        return this.columnEachPage;
    }

    public void setColumnEachPage(boolean columnEachPage) {
        this.columnEachPage = columnEachPage;
    }

    public void draw(List<Map<String, Object>> data, String bookmark) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pdf.addBookmark(page, bookmark);
        this.pages.add(page);

        this.rowV = getTop();
        this.columnH = getLeft();
        drawHeader(page);

        if (data == null) {
            return;
        }
        int row = 0;
        for (Map<String, Object> rowCells : data) {
            page = drawRow(page, rowCells, row);
        }
        drawGrid(page);
    }

    private void drawHeader(PDPage page) throws IOException {
        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);
        contentStream.setFont(font, 9);

        ColumnModel[] cms = this.model.getColumnModels();
        for (int i = 0; i < cms.length; i++) {
            if (i == 0) {
                this.columnH = getLeft();
            }
            else {
                this.columnH += cms[i - 1].getWidth();
            }

            String content = cms[i].getDisplayName();
            int offset = (cms[i].getWidth() - PDFUtil.getStringWidth(content, font, 9)) / 2;

            contentStream.beginText();
            contentStream.newLineAtOffset(this.columnH + offset, getTop());
            contentStream.showText(content);
            contentStream.endText();
        }
        contentStream.moveTo(getLeft(), getTop() - 3);
        contentStream.lineTo(getRight(), getTop() - 3);
        contentStream.stroke();
        this.rowV -= 15;
        contentStream.close();
    }

    private PDPage drawRow(PDPage page, Map<String, Object> rowCells, int row) throws IOException {
        PDPage currPage = page;
        if (this.rowV < getBottom()) {
            drawGrid(page);
            this.rowV = getTop();
            currPage = this.paper.createPage();
            this.pdf.getDocument().addPage(currPage);
            this.pages.add(currPage);
            if (this.columnEachPage) {
                drawHeader(currPage);
            }
        }

        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), currPage, true, false, false);
        contentStream.setFont(font, 9);

        ColumnModel[] cms = this.model.getColumnModels();
        for (int col = 0; col < cms.length; col++) {
            ColumnModel cm = cms[col];
            if (col == 0) {
                this.columnH = getLeft();
            }
            else {
                this.columnH += cms[col - 1].getWidth();
            }

            CellRenderer cr = this.model.getCellRenderer(0, col);
            cr.paint(contentStream, new Point(this.columnH, this.rowV), this, cm, rowCells.get(cm.getId()), row, col);
        }
        this.rowV -= 15;
        contentStream.close();

        return currPage;
    }

    private void drawGrid(PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);

        ColumnModel[] cms = this.model.getColumnModels();
        int columnH = getLeft();
        for (int i = 0; i < cms.length; i++) {
            columnH += cms[i].getWidth();
            contentStream.moveTo(columnH, getTop() + 12);
            contentStream.lineTo(columnH, this.rowV + 12);
            contentStream.stroke();

        }
        contentStream.addRect(
                getLeft(),
                this.rowV + 12,
                (float) this.getPaper().getViewSzie().getWidth(),
                getTop() - this.rowV);

        contentStream.close();
    }
}
