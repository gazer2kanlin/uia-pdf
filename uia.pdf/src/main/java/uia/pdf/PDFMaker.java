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
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import uia.pdf.gridbag.GridBagView;
import uia.pdf.papers.A3Paper;
import uia.pdf.papers.A4Paper;
import uia.pdf.papers.Paper;
import uia.pdf.papers.PaperType;
import uia.pdf.parsers.ValueParserFactory;

/**
 * PDF maker.
 *
 * @author Kan Lin
 *
 */
public class PDFMaker {
	
    private final PDDocument doc;

    private final ValueParserFactory factory;

    private final PDFont font;

    private PDDocumentOutline docOutline;

    private ArrayList<BookmarkPage> bookmarkPages;

    private ArrayDeque<PDOutlineItem> hierarchyOI;

    private PDOutlineItem lastOI;

    private ArrayList<PDOutlineItem> temp;
    
    static {
    	// https://pdfbox.apache.org/2.0/getting-started.html
    	System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    	System.setProperty("org.apache.pdfbox.rendering.UsePureJavaCMYKConversion", "true");
    }

    /**
     * Constructor.
     * @param font Font.
     * @throws IOException
     */
    public PDFMaker(PDFont font) throws IOException {
        this.temp = new ArrayList<PDOutlineItem>();
        this.doc = new PDDocument();
        this.font = font;
        this.factory = new ValueParserFactory();

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
        this.factory = new ValueParserFactory();

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
     *
     * @return
     */
    public ValueParserFactory getValueParserFactory() {
        return this.factory;
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
     * @param textAsContent Draw bookmark text or not.
     * @throws IOException
     */
    public PDPage addBookmark(ContentView view, PDPage page, String text, boolean textAsContent) throws IOException {
        text = text == null ? "" : text.trim();
        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);

        for (PDOutlineItem oi : this.temp) {
            oi.setDestination(dest);
        }

        if (text.trim().length() > 0) {
            this.lastOI = new PDOutlineItem();
            this.lastOI.setDestination(dest);
            this.lastOI.setTitle(text);
            this.hierarchyOI.peek().addLast(this.lastOI);
            this.bookmarkPages.add(new BookmarkPage(text, "" + this.doc.getNumberOfPages()));

            this.temp.add(this.lastOI);
        }

        if(textAsContent) {
            page = view.drawBookmarks(page, this.temp);
        }
        this.temp.clear();

        return page;
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
        text = text == null ? "" : text.trim();
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
        A4Paper a4 = new A4Paper(false);

        PDPage page = a4.createPage();
        if (this.doc.getPages().getCount() > 0) {
            this.doc.getPages().insertBefore(page, this.doc.getPage(0));
        }
        else {
            this.doc.addPage(page);
        }

        PDPageContentStream contentStream = new PDPageContentStream(this.doc, page, AppendMode.APPEND, false, false);

        contentStream.setFont(this.font, 16);
        int cw = PDFUtil.getContentWidth("INDEX", this.font, 11);
        contentStream.beginText();
        contentStream.newLineAtOffset(a4.getLeft() + (a4.getDrawableSize().width - cw) / 2, a4.getTop() - 12);
        contentStream.showText("INDEX");
        contentStream.endText();

        contentStream.setFont(this.font, 11);
        contentStream.setLineWidth(0.5f);
        int top = a4.getTop() - 20;
        for (BookmarkPage bp : this.bookmarkPages) {
            if (top <= a4.getBottom()) {
                contentStream.close();

                PDPage nextPage = a4.createPage();
                this.doc.getPages().insertAfter(nextPage, page);
                contentStream = new PDPageContentStream(this.doc, nextPage, AppendMode.APPEND, false, false);
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
    
    public GridBagView createGridBag(PaperType paperType, File layoutFile) throws PDFException {
    	Paper paper;
    	switch(paperType) {
    		case A4_LANDSCAPE:
    			paper = new A4Paper(true);
    			break;
    		case A3:
    			paper = new A3Paper();
    			break;
    		case A3_LANDSCAPE:
    			paper = new A3Paper(true);
    			break;
    		default:
    			paper = new A4Paper(false);
    	}
        return new GridBagView(this, paper, layoutFile);
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
