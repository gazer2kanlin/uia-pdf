package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFMaker;
import uia.pdf.papers.Paper;

public class GridBagView extends ContentView {

    private GridBagLayout gbLayout;

    private DefaultBindCellRenderer bindRenderer0;

    private HashMap<String, GridBagCellRenderer> bindRenderers;

    private HashMap<Class<?>, GridBagCellRenderer> classRenderers;

    public GridBagView(PDFMaker pdf, Paper paper, File layoutFile) throws Exception {
        super(pdf, paper);
        this.gbLayout = new GridBagLayout(GridBagTypeHelper.load(layoutFile));
        this.bindRenderer0 = new DefaultBindCellRenderer();
        this.bindRenderers = new HashMap<String, GridBagCellRenderer>();
        this.classRenderers = new HashMap<Class<?>, GridBagCellRenderer>();
    }

    public void registerBindCellRenderer(String id, GridBagCellRenderer renderer) {
        this.bindRenderers.put(id, renderer);
    }

    public void registerClassCellRenderer(Class<?> cls, GridBagCellRenderer renderer) {
        this.classRenderers.put(cls, renderer);
    }

    public void draw(List<Map<String, Object>> table, List<String> bookmarks) throws IOException {
        int p = 0;
        for (Map<String, Object> data : table) {
            draw(data, bookmarks.get(p));
            p++;
        }
    }

    public PDPage draw(Map<String, Object> data, String bookmark) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        this.pdf.addBookmark(page, bookmark);

        this.gbLayout.load(getWidth(), getHeight());

        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, true, false, false);
        PDFont font = this.pdf.getFont();
        contentStream.setFont(font, 9);

        Point topLeft = getTopLeft();
        for (Grid grid : this.gbLayout.getGrids()) {
            contentStream.setLineWidth(0.5f);

            ArrayList<Cell> colorBorder = new ArrayList<Cell>();
            // draw grid
            if (grid.borderEnabled) {
                for (Cell[] cells : grid.cells) {
                    for (Cell cell : cells) {
                        contentStream.setLineWidth(cell.boderSize);

                        Point cellBottomLeft = cell.bottomLeft(topLeft);
                        if (cell.background != null) {
                            contentStream.setNonStrokingColor(cell.background);
                            contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                            contentStream.fill();
                        }
                        else if (grid.rows[cell.row].background != null) {
                            contentStream.setNonStrokingColor(grid.rows[cell.row].background);
                            contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                            contentStream.fill();
                        }
                        else if (grid.columns[cell.col].background != null) {
                            contentStream.setNonStrokingColor(grid.columns[cell.col].background);
                            contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                            contentStream.fill();
                        }

                        contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                        contentStream.stroke();

                        if (cell.borderColor != null) {
                            colorBorder.add(cell);
                        }
                    }
                }

                // draw border
                for (Cell cell : colorBorder) {
                    Point cellBottomLeft = cell.bottomLeft(topLeft);
                    contentStream.setLineWidth(cell.boderSize);
                    contentStream.setStrokingColor(cell.borderColor);
                    contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                    contentStream.stroke();
                }
            }

            if (data == null) {
                continue;
            }

            // draw data
            int r = 0;
            for (Cell[] cells : grid.cells) {
                int c = 0;
                for (Cell cell : cells) {
                    if (cell.col != c || cell.row != r) {
                        continue;
                    }
                    cell.accept(this, contentStream, cell.bottomLeft(topLeft), data);
                    c++;
                }
                r++;
            }

            if (grid.borderEnabled) {
                contentStream.setStrokingColor(grid.borderColor);
                contentStream.setLineWidth(1.0f);
                contentStream.addRect(topLeft.x + grid.x, topLeft.y - grid.height - grid.y, grid.width, grid.height);
                contentStream.stroke();
            }

            contentStream.setStrokingColor(Color.black);
        }
        contentStream.close();

        return page;
    }

    GridBagCellRenderer getBindRenderer(String id, Object value) {
        if (id == null || value == null) {
            return this.bindRenderer0;
        }

        // id first
        GridBagCellRenderer bindRenderer = this.bindRenderers.get(id);
        if (bindRenderer != null) {
            return bindRenderer;
        }

        // class type
        GridBagCellRenderer classRenderer = this.classRenderers.get(value.getClass());
        if (classRenderer != null) {
            return classRenderer;
        }

        // default
        return this.bindRenderer0;
    }
}
