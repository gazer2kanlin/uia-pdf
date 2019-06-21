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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import uia.pdf.ContentView;
import uia.pdf.PDFException;
import uia.pdf.PDFMaker;
import uia.pdf.papers.Paper;

/**
 *
 * @author Kan Lin
 *
 */
public class GridBagView extends ContentView {

    private GridBagDrawer drawer;

    public GridBagView(PDFMaker pdf, Paper paper, List<GridBagModel> models) throws PDFException {
        super(pdf, paper);
        this.drawer = new GridBagDrawer(models);
        arrange();
    }
    
    public void arrange() {
        int w = getWidth();
    	int h = getHeight();
    	int startY = 0;
    	for(GridBagModel model : this.drawer.getModels()) {
    		model.arrange(startY, w, h);
    		startY += model.getHeight();
    	}
    }

    public void registerBindIdCellRenderer(String id, GridBagCellRenderer renderer) {
        this.drawer.registerBindIdCellRenderer(id, renderer);
    }

    public void registerBindClassCellRenderer(Class<?> cls, GridBagCellRenderer renderer) {
        this.drawer.registerBindClassCellRenderer(cls, renderer);
    }

    public PDPage addPageEx(Map<String, Map<String, Object>> gridsData, String bookmark, boolean draw) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        this.pdf.addBookmark(this, page, bookmark, draw);
        return addPageEx(page, gridsData);
    }

    public PDPage addPageEx(Map<String, Map<String, Object>> gridsData) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        return addPageEx(page, gridsData);
    }

    public PDPage addPage(Map<String, Object> data, String bookmark, boolean draw) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        this.pdf.addBookmark(this, page, bookmark, draw);
        return addPage(page, data);
    }

    public PDPage addPage(Map<String, Object> data) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        return addPage(page, data);
    }

    @Override
    public PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException {

        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, 14);

        int rowV = getDrawingTop();
        for (PDOutlineItem oi : ois) {
            contentStream.beginText();
            contentStream.newLineAtOffset(getLeft(), rowV - 16);
            contentStream.showText(oi.getTitle().trim());
            contentStream.endText();
            rowV -= 16;
        }
        contentStream.close();

        return page;
    }

    private PDPage addPageEx(PDPage page, Map<String, Map<String, Object>> gridsData) throws IOException {
        this.drawer.drawEx(this, page, gridsData);
        return page;
    }

    private PDPage addPage(PDPage page, Map<String, Object> data) throws IOException {
        this.drawer.draw(this, page, data);
        return page;
    }

    GridBagCellRenderer getBindRenderer(String id, Object value) {
        return this.drawer.getBindRenderer(id, value);
    }
}
