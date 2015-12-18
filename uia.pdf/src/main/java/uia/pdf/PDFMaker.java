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

package uia.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import uia.pdf.papers.A4Paper;

/**
 * PDF maker.
 *
 * @author Kan Lin
 *
 */
public class PDFMaker {

    private final PDDocument doc;

    private PDDocumentOutline docOutline;

    private final PDFont font;

    private LinkedHashMap<String, String> index;

    private ArrayDeque<PDOutlineItem> hierarchyOI;

    private PDOutlineItem lastOI;

    public PDFMaker(File fontFile) throws IOException {
        this.doc = new PDDocument();
        this.font = PDType0Font.load(this.doc, fontFile);

        this.docOutline = new PDDocumentOutline();
        this.doc.getDocumentCatalog().setDocumentOutline(this.docOutline);

        PDOutlineItem rootOI = new PDOutlineItem();
        rootOI.setTitle("All");
        this.docOutline.addLast(rootOI);

        this.hierarchyOI = new ArrayDeque<PDOutlineItem>();
        this.hierarchyOI.push(rootOI);

        this.index = new LinkedHashMap<String, String>();

    }

    public boolean save(File file) {
        try {
            createIndex();
            this.docOutline.openNode();
            this.doc.save(file);
            this.doc.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public PDDocument getDocument() {
        return this.doc;
    }

    public void addBookmark(PDPage page, String title) {
        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);
        this.lastOI = new PDOutlineItem();
        this.lastOI.setDestination(dest);
        this.lastOI.setTitle(title);
        this.hierarchyOI.peek().addLast(this.lastOI);
        this.index.put(title, "" + this.doc.getNumberOfPages());
    }

    public void beginBookmarkGroup() {
        if (this.hierarchyOI.peek() != this.lastOI) {
            this.hierarchyOI.push(this.lastOI);
        }
    }

    public void endBookmarkGroup() {
        if (this.hierarchyOI.size() > 1) {
            this.hierarchyOI.pop();
        }
    }

    public PDFont getFont() {
        return this.font;
    }

    private void createIndex() throws IOException {
        A4Paper a4 = new A4Paper();

        PDPage page = a4.createPage();
        this.doc.getPages().insertBefore(page, this.doc.getPage(0));

        PDPageContentStream contentStream = new PDPageContentStream(this.doc, page, true, false, false);

        contentStream.setFont(this.font, 16);
        int cw = PDFUtil.getStringWidth("INDEX", this.font, 11);
        contentStream.beginText();
        contentStream.newLineAtOffset(a4.getLeft() + (a4.getDrawableSize().width - cw) / 2, a4.getTop() - 12);
        contentStream.showText("INDEX");
        contentStream.endText();

        contentStream.setFont(this.font, 11);
        int top = a4.getTop() - 20;
        for (Map.Entry<String, String> e : this.index.entrySet()) {
            if (top <= a4.getBottom()) {
                contentStream.close();

                PDPage nextPage = a4.createPage();
                this.doc.getPages().insertAfter(nextPage, page);
                contentStream = new PDPageContentStream(this.doc, nextPage, true, false, false);
                contentStream.setFont(this.font, 11);

                top = a4.getTop() - 20;
            }

            int cw1 = PDFUtil.getStringWidth(e.getKey(), this.font, 11);
            int cw2 = PDFUtil.getStringWidth(e.getValue(), this.font, 11);

            contentStream.moveTo(a4.getLeft() + cw1 + 10, top - 10);
            contentStream.lineTo(a4.getRight() - 18, top - 10);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(a4.getLeft(), top - 10);
            contentStream.showText(e.getKey());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(a4.getRight() - cw2, top - 10);
            contentStream.showText(e.getValue());
            contentStream.endText();

            top -= 15;
        }

        contentStream.close();
    }
}
