package uia.pdf.grid;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
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
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.papers.Paper;
import uia.utils.PropertyBeanUtils;

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

    private int columnHz;

    private boolean columnEachPage;

    private int fontSize;

    private PDPage currPage;

    private boolean drawHeader;

    public GridView(PDFMaker pdf, Paper paper, GridModel model) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = true;
        this.fontSize = model.getFontSize();
        this.drawHeader = model.isHeaderVisible();
    }

    private GridView(PDFMaker pdf, Paper paper, GridModel model, int rowV, int tableTop, int columnH, boolean columnEachPage, PDPage currPage) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = columnEachPage;
        this.fontSize = model.getFontSize();
        this.drawHeader = model.isHeaderVisible();

        this.rowV = rowV;
        this.tableTop = tableTop;
        this.columnHz = columnH;
        this.currPage = currPage;
    }
 
    public boolean isDrawHeader() {
        return this.drawHeader;
    }

    public void setDrawHeader(boolean drawHeader) {
        this.drawHeader = drawHeader;
    }
    
    public GridView beginBookmarkGroup(String text) {
    	this.pdf.beginBookmarkGroup(text);
    	return this;
    }

    public GridView endBookmarkGroup() {
    	this.pdf.endBookmarkGroup();
    	return this;
    }

    public GridView create(GridModel model) {
        GridView gv = new GridView(
                this.pdf,
                this.paper,
                model,
                this.rowV,
                this.tableTop,
                this.columnHz,
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

    public GridView draw(List<Map<String, Object>> data, String bookmark, boolean bookmarkAsContent) throws Exception {
        PDPage page = this.currPage;
        if (page == null) {
            page = newPage();
        }

        page = this.pdf.addBookmark(this, page, bookmark, bookmarkAsContent);
        page = drawColumns(page);

        if (data == null) {
            return this;
        }
        int row = 0;
        for (Map<String, Object> rowCells : data) {
            page = drawRow(page, rowCells, row, false);
        }
        drawGridLine(page);
        
        return this;
    }

    public <T> GridView draw2(List<T> data, String bookmark, boolean bookmarkAsContent) throws Exception {
        PDPage page = this.currPage;
        if (page == null) {
            page = newPage();
        }

        page = this.pdf.addBookmark(this, page, bookmark, bookmarkAsContent);
        page = drawColumns(page);

        if (data == null) {
            return this;
        }
        int row = 0;
        for (Object rowData : data) {
            page = draw2Row(page, rowData, row, false);
        }
        drawGridLine(page);
        
        return this;
    }

    @Override
    public PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException {
        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, 14);
        for (PDOutlineItem oi : ois) {
            if (this.rowV - 16 < getDrawingBottom()) {
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
        this.rowV = getDrawingTop();
        this.tableTop = this.rowV;
        this.columnHz = getLeft();

        PDPage page = this.paper.createPage();
        this.currPage = page;
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);

        return page;

    }

    private PDPage drawColumns(PDPage page) throws Exception {
        if (!this.drawHeader) {
            return page;
        }

        PDFont font = this.pdf.getFont();

        ColumnModel[] cms = this.model.getColumnModels();
        int hh = 0;
        for (int i0 = 0; i0 < cms.length; i0++) {
            ArrayList<String> cs = new ArrayList<String>();
            int h0 = PDFUtil.splitContent(cms[i0].getDisplayName(), font, getFontSize(), cms[i0].getWidth() - 4, cs);
            hh = Math.max(hh, h0);
        }

        if (this.rowV - (4 * hh) < getDrawingBottom()) {
            page = newPage();
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, this.fontSize);

        DefaultCellRenderer renderer = new DefaultCellRenderer();
        for (int i = 0; i < cms.length; i++) {
            if (i == 0) {
                this.columnHz = getLeft();
            }
            else {
                this.columnHz += cms[i - 1].getWidth();
            }

            contentStream.setNonStrokingColor(new Color(232, 232, 232));
            contentStream.addRect(this.columnHz, this.rowV - hh, cms[i].getWidth(), hh);
            contentStream.fill();
            contentStream.setNonStrokingColor(new Color(0, 0, 0));

            String content = cms[i].getDisplayName();
            int offset = (cms[i].getWidth() - PDFUtil.getContentWidth(content, font, this.fontSize)) / 2;
            if (this.columnHz + offset > getRight()) {
                break;
            }

            ColumnModel other = cms[i].clone();
            other.setWrap(true);
            other.setHorizontalAlignment(AlignmentType.CENTER);
            other.setBackground(new Color(232, 232, 232));
            renderer.paint(contentStream, new Point(this.columnHz, this.rowV), this, other, content, -1, i);

            //contentStream.beginText();
            //contentStream.newLineAtOffset(this.columnH + offset, this.rowV - h - 3);
            //contentStream.showText(content);
            //contentStream.endText();
        }
        contentStream.moveTo(getLeft(), this.rowV - hh);
        contentStream.lineTo(getRight(), this.rowV - hh);
        contentStream.stroke();
        this.rowV -= hh;
        contentStream.close();

        return page;
    }

    private PDPage drawRow(PDPage page, Map<String, Object> rowCells, int row, boolean forceNewPage) throws Exception {
        PDPage currPage = page;
        if (forceNewPage || (this.rowV - 12) < getDrawingBottom()) {
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
                this.columnHz = getLeft();
            }
            else {
                this.columnHz += cms[col - 1].getWidth();
            }

            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(this.columnHz, this.rowV), this, cm, rowCells.get(cm.getId()), row, col));
        }
        this.rowV -= h;

        // handle overlap at footer area
        if (!forceNewPage && this.rowV < getDrawingBottom()) {
            contentStream.setNonStrokingColor(Color.white);
            contentStream.addRect(
                    getLeft(),
                    this.rowV,
                    (float) this.getPaper().getContentSize().getWidth(),
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


    private PDPage draw2Row(PDPage page, Object data, int row, boolean forceNewPage) throws Exception {
        PDPage currPage = page;
        if (forceNewPage || (this.rowV - 12) < getDrawingBottom()) {
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
                this.columnHz = getLeft();
            }
            else {
                this.columnHz += cms[col - 1].getWidth();
            }
            
            Object value = PropertyBeanUtils.read(data, cm.getId());
            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(this.columnHz, this.rowV), this, cm, value, row, col));
        }
        this.rowV -= h;

        // handle overlap at footer area
        if (!forceNewPage && this.rowV < getDrawingBottom()) {
            contentStream.setNonStrokingColor(Color.white);
            contentStream.addRect(
                    getLeft(),
                    this.rowV,
                    (float) this.getPaper().getContentSize().getWidth(),
                    h);
            contentStream.fill();
            contentStream.setNonStrokingColor(Color.black);
            contentStream.close();
            this.rowV += h;
            return draw2Row(page, data, row, true);
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
                (float) this.getPaper().getContentSize().getWidth(),
                this.tableTop - this.rowV);
        contentStream.stroke();

        contentStream.close();

        this.rowV -= 10;
    }
}
