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
import uia.pdf.PDFDoc;
import uia.pdf.DrawingUtils;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.papers.Paper;
import uia.utils.PropertyBeanUtils;

/**
 * Present content with grid.
 *
 * @author Kan Lin
 *
 */
public class GridView extends ContentView {

    private final GridModel model;

    private int rowV;

    private int tableTop;

    private int columnHz;

    private boolean columnEachPage;

    private PDPage currPage;

    private boolean drawHeader;

    public GridView(PDFDoc pdf, Paper paper, GridModel model) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = true;
        this.drawHeader = model.isHeaderVisible();
    }

    private GridView(PDFDoc pdf, Paper paper, GridModel model, int rowV, int tableTop, int columnH, boolean columnEachPage, PDPage currPage) {
        super(pdf, paper);
        this.model = model;
        this.columnEachPage = columnEachPage;
        this.drawHeader = model.isHeaderVisible();

        this.rowV = rowV;
        this.tableTop = tableTop;
        this.columnHz = columnH;
        this.currPage = currPage;
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

    public int getFontSize() {
        return this.model.getFontSize();
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
            contentStream.newLineAtOffset(this.paper.getContentLeft(), this.rowV - 16);
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
        this.columnHz = this.paper.getContentLeft();

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

        int hh = this.model.getHeaderHeight();
        ColumnModel[] cms = this.model.getColumnModels();
        for (int i0 = 0; i0 < cms.length; i0++) {
            ArrayList<String> cs = new ArrayList<String>();
            int h0 = DrawingUtils.getContentWrapHeight(
            		cms[i0].getDisplayName(), 
            		font, 
            		getFontSize(), 
            		cms[i0].getWidth() - 4, 
            		cs);
            hh = Math.max(hh, h0);
        }

        if (this.rowV - (4 * hh) < getDrawingBottom()) {
            page = newPage();
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, getFontSize());
        DefaultCellRenderer renderer = new DefaultCellRenderer();
        for (int i = 0; i < cms.length; i++) {
            if (i == 0) {
                this.columnHz = this.paper.getContentLeft();
            }
            else {
                this.columnHz += cms[i - 1].getWidth();
            }

            contentStream.setNonStrokingColor(new Color(232, 232, 232));
            contentStream.addRect(this.columnHz, this.rowV - hh, cms[i].getWidth(), hh);
            contentStream.fill();
            contentStream.setNonStrokingColor(new Color(0, 0, 0));

            String content = cms[i].getDisplayName();
            int offset = (cms[i].getWidth() - DrawingUtils.getContentWidth(content, font, getFontSize())) / 2;
            if (this.columnHz + offset > this.paper.getContentRight()) {
                break;
            }

            ColumnModel other = cms[i].clone();
            other.setWrap(true);
            other.setHorizontalAlignment(AlignmentType.CENTER);
            other.setBackground(new Color(232, 232, 232));
            renderer.paint(contentStream, new Point(this.columnHz, this.rowV), this, other, content, -1, i);
        }
        contentStream.moveTo(this.paper.getContentLeft(), this.rowV - hh);
        contentStream.lineTo(this.paper.getContentRight(), this.rowV - hh);
        contentStream.stroke();
        this.rowV -= hh;
        contentStream.close();

        return page;
    }

    private PDPage drawRow(PDPage page, Map<String, Object> rowCells, int row, boolean forceNewPage) throws Exception {
        PDFont font = this.pdf.getFont();

        PDPage currPage = page;
        int rh = DrawingUtils.getContentHeight(font, getFontSize());
        if (forceNewPage || (this.rowV - rh) < getDrawingBottom()) {
            drawGridLine(page);
            currPage = newPage();
            if (this.columnEachPage) {
                currPage = drawColumns(currPage);
            }
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), currPage, AppendMode.APPEND, false, false);
        contentStream.setFont(font, getFontSize());
        ColumnModel[] cms = this.model.getColumnModels();
        int h = 0;
        for (int col = 0; col < cms.length; col++) {
            ColumnModel cm = cms[col];
            if (col == 0) {
                this.columnHz = this.paper.getContentLeft();
            }
            else {
                this.columnHz += cms[col - 1].getWidth();
            }

            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(this.columnHz, this.rowV), this, cm, rowCells.get(cm.getId()), row, col));
        }
        this.rowV -= h;

        // recover: overlap on footer area
        if (!forceNewPage && this.rowV < getDrawingBottom()) {
        	// erase painted content
            contentStream.setNonStrokingColor(Color.white);
            contentStream.addRect(
            		this.paper.getContentLeft(),
                    this.rowV,
                    (float) this.getPaper().getContentSize().getWidth(),
                    h);
            contentStream.fill();
            contentStream.setNonStrokingColor(Color.black);
            contentStream.close();
        	// move to previous position
            this.rowV += h;
            
            return drawRow(page, rowCells, row, true);
        }

        contentStream.setLineWidth(0.1f);
        contentStream.moveTo(this.paper.getContentLeft(), this.rowV);
        contentStream.lineTo(this.paper.getContentRight(), this.rowV);
        contentStream.stroke();
        contentStream.close();

        return currPage;
    }


    private PDPage draw2Row(PDPage page, Object data, int row, boolean forceNewPage) throws Exception {
        PDFont font = this.pdf.getFont();

        PDPage currPage = page;
        int rh = DrawingUtils.getContentHeight(font, getFontSize());
        if (forceNewPage || (this.rowV - rh) < getDrawingBottom()) {
            drawGridLine(page);
            currPage = newPage();
            if (this.columnEachPage) {
                currPage = drawColumns(currPage);
            }
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), currPage, AppendMode.APPEND, false, false);
        contentStream.setFont(font, getFontSize());
        ColumnModel[] cms = this.model.getColumnModels();
        int h = 0;
        for (int col = 0; col < cms.length; col++) {
            ColumnModel cm = cms[col];
            if (col == 0) {
                this.columnHz = this.paper.getContentLeft();
            }
            else {
                this.columnHz += cms[col - 1].getWidth();
            }
            
            Object value = PropertyBeanUtils.read(data, cm.getId());
            CellRenderer cr = this.model.getCellRenderer(0, col);
            h = Math.max(h, cr.paint(contentStream, new Point(this.columnHz, this.rowV), this, cm, value, row, col));
        }
        this.rowV -= h;

        // recover: overlap on footer area
        if (!forceNewPage && this.rowV < getDrawingBottom()) {
        	// erase painted content
            contentStream.setNonStrokingColor(Color.white);
            contentStream.addRect(
            		this.paper.getContentLeft(),
                    this.rowV,
                    (float) this.getPaper().getContentSize().getWidth(),
                    h);
            contentStream.fill();
            contentStream.setNonStrokingColor(Color.black);
            contentStream.close();
        	// move to previous position
            this.rowV += h;
            
            return draw2Row(page, data, row, true);
        }

        contentStream.setLineWidth(0.1f);
        contentStream.moveTo(this.paper.getContentLeft(), this.rowV);
        contentStream.lineTo(this.paper.getContentRight(), this.rowV);
        contentStream.stroke();
        contentStream.close();

        return currPage;
    }

    private void drawGridLine(PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setLineWidth(1.0f);

        ColumnModel[] cms = this.model.getColumnModels();
        int columnH = this.paper.getContentLeft();
        for (int i = 0; i < cms.length; i++) {
            columnH += cms[i].getWidth();
            contentStream.moveTo(columnH, this.tableTop);
            contentStream.lineTo(columnH, this.rowV);
            contentStream.stroke();

        }
        contentStream.addRect(
        		this.paper.getContentLeft(),
                this.rowV,
                (float) this.getPaper().getContentSize().getWidth(),
                this.tableTop - this.rowV);
        contentStream.stroke();

        contentStream.close();

        this.rowV -= 10;
    }
}
