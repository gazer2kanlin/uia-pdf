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

package uia.pdf.gridbag;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFException;
import uia.pdf.gridbag.model.Cell;
import uia.pdf.gridbag.model.GridBag;

/**
 *
 * @author Kan Lin
 *
 */
public class GridBagDrawer {

    public final GridBagLayout gbLayout;

    private DefaultBindCellRenderer bindRenderer0;

    private HashMap<String, GridBagCellRenderer> bindIdRenderers;

    private HashMap<Class<?>, GridBagCellRenderer> bindClassRenderers;

    public GridBagDrawer(File layoutFile) throws PDFException {
        try {
            this.gbLayout = new GridBagLayout(GridBagTypeHelper.load(layoutFile));
            this.bindRenderer0 = new DefaultBindCellRenderer();
            this.bindIdRenderers = new HashMap<String, GridBagCellRenderer>();
            this.bindClassRenderers = new HashMap<Class<?>, GridBagCellRenderer>();
        }
        catch (Exception ex) {
            throw new PDFException(layoutFile + " failure", ex);
        }
    }

    public void registerBindIdCellRenderer(String id, GridBagCellRenderer renderer) {
        this.bindIdRenderers.put(id, renderer);
    }

    public void registerBindClassCellRenderer(Class<?> cls, GridBagCellRenderer renderer) {
        this.bindClassRenderers.put(cls, renderer);
    }

    public void drawEx(ContentView cv, PDPage page, Map<String, Map<String, Object>> gridsData) throws IOException {
        this.gbLayout.load(cv.getWidth(), cv.getHeight());

        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, AppendMode.APPEND, false, false);
        PDFont font = cv.getDoc().getFont();
        contentStream.setFont(font, 9);

        Point topLeft = cv.getTopLeft();
        for (GridBag grid : this.gbLayout.getGrids()) {
            contentStream.setLineWidth(0.5f);

            Map<String, Object> data = gridsData.get(grid.name);

            if (grid.background != null) {
                contentStream.setNonStrokingColor(grid.background);
                contentStream.addRect(topLeft.x + grid.x, topLeft.y - grid.height - grid.y, grid.width, grid.height);
                contentStream.fill();
            }

            ArrayList<Cell> colorBorder = new ArrayList<Cell>();
            // draw grid
            if (grid.borderEnabled) {
                for (Cell[] cells : grid.cells) {
                    for (Cell cell : cells) {
                        contentStream.setLineWidth(cell.borderSize);

                        Point cellBottomLeft = cell.bottomLeft(topLeft);
                        Color background = cell.getBackground();
                        if (background != null) {
                            contentStream.setNonStrokingColor(background);
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
                    contentStream.setLineWidth(cell.borderSize);
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
                    if (cell.col != c && cell.row != r) {
                        continue;
                    }
                    cell.accept(cv, this, contentStream, cell.bottomLeft(topLeft), data);
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
    }

    public void draw(ContentView cv, PDPage page, Map<String, Object> data) throws IOException {
        this.gbLayout.load(cv.getWidth(), cv.getHeight());

        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, AppendMode.APPEND, false, false);
        PDFont font = cv.getDoc().getFont();
        contentStream.setFont(font, 9);

        Point topLeft = cv.getTopLeft();
        for (GridBag grid : this.gbLayout.getGrids()) {
            contentStream.setLineWidth(0.5f);

            if (grid.background != null) {
                contentStream.setNonStrokingColor(grid.background);
                contentStream.addRect(topLeft.x + grid.x, topLeft.y - grid.height - grid.y, grid.width, grid.height);
                contentStream.fill();
            }

            ArrayList<Cell> colorBorder = new ArrayList<Cell>();
            // draw grid
            if (grid.borderEnabled) {
                for (Cell[] cells : grid.cells) {
                    for (Cell cell : cells) {
                        contentStream.setLineWidth(cell.borderSize);

                        Point cellBottomLeft = cell.bottomLeft(topLeft);
                        Color background = cell.getBackground();
                        if (background != null) {
                            contentStream.setNonStrokingColor(background);
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
                    contentStream.setLineWidth(cell.borderSize);
                    contentStream.setStrokingColor(cell.borderColor);
                    contentStream.addRect(cellBottomLeft.x, cellBottomLeft.y, cell.getWidth(), cell.getHeight());
                    contentStream.stroke();
                }
            }

            // draw data
            int r = 0;
            for (Cell[] cells : grid.cells) {
                int c = 0;
                for (Cell cell : cells) {
                    if (cell.col != c && cell.row != r) {
                        continue;
                    }
                    cell.accept(cv, this, contentStream, cell.bottomLeft(topLeft), data);
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
    }

    public GridBagCellRenderer getBindRenderer(String id, Object value) {
        if (id == null || value == null) {
            return this.bindRenderer0;
        }

        // id first
        GridBagCellRenderer bindRenderer = this.bindIdRenderers.get(id);
        if (bindRenderer != null) {
            return bindRenderer;
        }

        // class type
        GridBagCellRenderer classRenderer = this.bindClassRenderers.get(value.getClass());
        if (classRenderer != null) {
            return classRenderer;
        }

        // default
        return this.bindRenderer0;
    }
}
