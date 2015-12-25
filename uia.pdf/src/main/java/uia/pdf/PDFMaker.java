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
import java.util.ArrayList;

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

    private ArrayList<BookmarkPage> bookmarkPages;

    private ArrayDeque<PDOutlineItem> hierarchyOI;

    private PDOutlineItem lastOI;

    private ArrayList<PDOutlineItem> temp;

    /**
     * Constructor.
     * @param font Font.
     * @throws IOException
     */
    public PDFMaker(PDFont font) throws IOException {
        this.temp = new ArrayList<PDOutlineItem>();
        this.doc = new PDDocument();
        this.font = font;

        this.docOutline = new PDDocumentOutline();
        this.doc.getDocumentCatalog().setDocumentOutline(this.docOutline);

        PDOutlineItem rootOI = new PDOutlineItem();
        rootOI.setTitle("All");
        this.docOutline.addLast(rootOI);

        this.hierarchyOI = new ArrayDeque<PDOutlineItem>();
        this.hierarchyOI.push(rootOI);

        this.bookmarkPages = new ArrayList<BookmarkPage>();
    }

    /**
     * Constructor.
     * @param fontFile TTF font file.
     * @throws IOException
     */
    public PDFMaker(File fontFile) throws IOException {
        this.temp = new ArrayList<PDOutlineItem>();
        this.doc = new PDDocument();
        this.font = PDType0Font.load(this.doc, fontFile);

        this.docOutline = new PDDocumentOutline();
        this.doc.getDocumentCatalog().setDocumentOutline(this.docOutline);

        PDOutlineItem rootOI = new PDOutlineItem();
        rootOI.setTitle("All");
        this.docOutline.addLast(rootOI);

        this.hierarchyOI = new ArrayDeque<PDOutlineItem>();
        this.hierarchyOI.push(rootOI);

        this.bookmarkPages = new ArrayList<BookmarkPage>();
    }

    /**
     * Save
     * @param file PDF file.
     * @throws IOException
     */
    public void save(File file) throws IOException {
        createIndex();
        this.docOutline.openNode();
        this.doc.save(file);
        this.doc.close();
    }

    /**
     * Get document.
     * @return PDF document.
     */
    public PDDocument getDocument() {
        return this.doc;
    }

    /**
     * Get font.
     * @return Font.
     */
    public PDFont getFont() {
        return this.font;
    }

    /**
     * Add bookmark.
     * @param page Page.
     * @param text bookmark text.
     * @throws IOException
     */
    public void addBookmark(ContentView view, PDPage page, String text) throws IOException {
        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);

        for (PDOutlineItem oi : this.temp) {
            oi.setDestination(dest);
        }

        if (text != null && text.trim().length() > 0) {
            this.lastOI = new PDOutlineItem();
            this.lastOI.setDestination(dest);
            this.lastOI.setTitle(text);
            this.hierarchyOI.peek().addLast(this.lastOI);
            this.bookmarkPages.add(new BookmarkPage(text, "" + this.doc.getNumberOfPages()));
        }

        this.temp.add(this.lastOI);
        view.drawBookmarks(page, this.temp);
        this.temp.clear();
    }

    /**
     * Begin bookmark group.
     */
    public void beginBookmarkGroup() {
        if (this.hierarchyOI.peek() != this.lastOI) {
            this.hierarchyOI.push(this.lastOI);
        }
    }

    /**
     *
     * @param text
     */
    public void beginBookmarkGroup(String text) {
        this.lastOI = new PDOutlineItem();
        this.lastOI.setTitle(text);
        this.hierarchyOI.peek().addLast(this.lastOI);
        this.bookmarkPages.add(new BookmarkPage(text, "" + (this.doc.getNumberOfPages() + 1)));
        this.temp.add(this.lastOI);

        this.hierarchyOI.push(this.lastOI);
    }

    /**
     * End bookmark group.
     */
    public void endBookmarkGroup() {
        if (this.hierarchyOI.size() > 1) {
            this.hierarchyOI.pop();
        }
    }

    private void createIndex() throws IOException {
        A4Paper a4 = new A4Paper();

        PDPage page = a4.createPage();
        this.doc.getPages().insertBefore(page, this.doc.getPage(0));

        PDPageContentStream contentStream = new PDPageContentStream(this.doc, page, true, false, false);

        contentStream.setFont(this.font, 16);
        int cw = PDFUtil.getContentWidth("INDEX", this.font, 11);
        contentStream.beginText();
        contentStream.newLineAtOffset(a4.getLeft() + (a4.getDrawableSize().width - cw) / 2, a4.getTop() - 12);
        contentStream.showText("INDEX");
        contentStream.endText();

        contentStream.setFont(this.font, 11);
        int top = a4.getTop() - 20;
        for (BookmarkPage bp : this.bookmarkPages) {
            if (top <= a4.getBottom()) {
                contentStream.close();

                PDPage nextPage = a4.createPage();
                this.doc.getPages().insertAfter(nextPage, page);
                contentStream = new PDPageContentStream(this.doc, nextPage, true, false, false);
                contentStream.setFont(this.font, 11);

                top = a4.getTop() - 20;
            }

            int cw1 = PDFUtil.getContentWidth(bp.text, this.font, 11);
            int cw2 = PDFUtil.getContentWidth(bp.pageNo, this.font, 11);

            int right = a4.getRight() - 18;
            int w = 4 * (int) Math.ceil((right - (a4.getLeft() + cw1 + 10)) / 4);
            contentStream.setLineDashPattern(new float[] { 1, 3 }, 0);
            contentStream.moveTo(right - w - 1, top - 10);
            contentStream.lineTo(right, top - 10);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(a4.getLeft(), top - 10);
            contentStream.showText(bp.text);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(a4.getRight() - cw2, top - 10);
            contentStream.showText(bp.pageNo);
            contentStream.endText();

            top -= 15;
        }

        contentStream.close();
    }

    class BookmarkPage {

        final String text;

        final String pageNo;

        public BookmarkPage(String text, String pageNo) {
            this.text = text;
            this.pageNo = pageNo;
        }
    }
}
