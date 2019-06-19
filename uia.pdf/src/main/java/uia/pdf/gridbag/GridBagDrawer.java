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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFException;
import uia.pdf.gridbag.model.Cell;

/**
 *
 * @author Kan Lin
 *
 */
public class GridBagDrawer {

    private final List<GridBagModel> models;

    private DefaultBindCellRenderer bindRenderer0;

    private HashMap<String, GridBagCellRenderer> bindIdRenderers;

    private HashMap<Class<?>, GridBagCellRenderer> bindClassRenderers;

    public GridBagDrawer(List<GridBagModel> models) throws PDFException {
        this.models = models;
        this.bindRenderer0 = new DefaultBindCellRenderer();
        this.bindIdRenderers = new HashMap<String, GridBagCellRenderer>();
        this.bindClassRenderers = new HashMap<Class<?>, GridBagCellRenderer>();
    }

    public List<GridBagModel> getModels() {
		return models;
	}

	public void registerBindIdCellRenderer(String id, GridBagCellRenderer renderer) {
        this.bindIdRenderers.put(id, renderer);
    }

    public void registerBindClassCellRenderer(Class<?> cls, GridBagCellRenderer renderer) {
        this.bindClassRenderers.put(cls, renderer);
    }

    public void drawEx(ContentView cv, PDPage page, Map<String, Map<String, Object>> data) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, AppendMode.APPEND, false, false);
        PDFont font = cv.getDoc().getFont();
        contentStream.setFont(font, 9);

        Point topLeft = cv.getDrawingTopLeft();
        for (GridBagModel model : this.models) {
            contentStream.setLineWidth(0.5f);
            if (model.background != null) {
                contentStream.setNonStrokingColor(model.background);
                contentStream.addRect(
                		topLeft.x + model.getX(), 
                		topLeft.y - model.getHeight() - model.getY(), 
                		model.getWidth(), 
                		model.getHeight());
                contentStream.fill();
            }

            ArrayList<Cell> colorBorder = new ArrayList<Cell>();
            if (model.borderEnabled) {
                for (Cell[] cells : model.cells) {
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

            Map<String, Object> modelData = data.get(model.name);
            if (modelData == null) {
                continue;
            }

            int r = 0;
            for (Cell[] cells : model.cells) {
                int c = 0;
                for (Cell cell : cells) {
                    if (cell.col != c && cell.row != r) {
                        continue;
                    }
                    cell.accept(cv, this, contentStream, cell.bottomLeft(topLeft), modelData);
                    c++;
                }
                r++;
            }

            if (model.borderEnabled) {
                contentStream.setStrokingColor(model.borderColor);
                contentStream.setLineWidth(1.0f);
                contentStream.addRect(
                		topLeft.x + model.getX(), 
                		topLeft.y - model.getHeight() - model.getY(), 
                		model.getWidth(), 
                		model.getHeight());
                contentStream.stroke();
            }

            contentStream.setStrokingColor(Color.black);
        }
        contentStream.close();
    }

    public void draw(ContentView cv, PDPage page, Map<String, Object> data) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, AppendMode.APPEND, false, false);
        PDFont font = cv.getDoc().getFont();
        contentStream.setFont(font, 9);

        Point topLeft = cv.getDrawingTopLeft();
        for (GridBagModel model : this.models) {
            contentStream.setLineWidth(0.5f);

            if (model.background != null) {
                contentStream.setNonStrokingColor(model.background);
                contentStream.addRect(
                		topLeft.x + model.getX(), 
                		topLeft.y - model.getHeight() - model.getY(), 
                		model.getWidth(), 
                		model.getHeight());
                contentStream.fill();
            }

            ArrayList<Cell> colorBorder = new ArrayList<Cell>();
            // draw grid
            if (model.borderEnabled) {
                for (Cell[] cells : model.cells) {
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
            for (Cell[] cells : model.cells) {
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

            if (model.borderEnabled) {
                contentStream.setStrokingColor(model.borderColor);
                contentStream.setLineWidth(1.0f);
                contentStream.addRect(
                		topLeft.x + model.getX(), 
                		topLeft.y - model.getHeight() - model.getY(), 
                		model.getWidth(), 
                		model.getHeight());
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
