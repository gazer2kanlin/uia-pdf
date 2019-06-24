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

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import uia.pdf.ContentView;
import uia.pdf.PDFDoc;
import uia.pdf.papers.Paper;

/**
 *
 * @author Kan Lin
 *
 */
public class GridBagView extends ContentView {

	private final List<GridBagModel> models;

    public GridBagView(PDFDoc pdf, Paper paper, List<GridBagModel> models) {
        super(pdf, paper);
        this.models = models;
        arrange();
    }
    
    public void arrange() {
        int w = getWidth();
    	int h = getHeight();
    	int startY = 0;
    	for(GridBagModel model : this.models) {
    		model.arrange(startY, w, h);
    		startY += model.getHeight();
    	}
    }

    public PDPage addPage(Object gridsData, String bookmark, boolean draw) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        this.pdf.addBookmark(this, page, bookmark, draw);
        return addPage(page, gridsData);
    }
    
    public PDPage addPage(Object gridsData) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        return addPage(page, gridsData);
    }

    @Override
    public PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException {

        PDFont font = this.pdf.getFont();
        PDPageContentStream contentStream = new PDPageContentStream(this.pdf.getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, 14);

        int rowV = getDrawingTop();
        for (PDOutlineItem oi : ois) {
            contentStream.beginText();
            contentStream.newLineAtOffset(this.paper.getContentLeft(), rowV - 16);
            contentStream.showText(oi.getTitle().trim());
            contentStream.endText();
            rowV -= 16;
        }
        contentStream.close();

        return page;
    }

    private PDPage addPage(PDPage page, Object gridData) throws IOException {
        for (GridBagModel model : this.models) {
        	model.drawOn(this, page, gridData);
        }
        return page;

    }
}
