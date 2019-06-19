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

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.papers.Paper;

/**
 * Footer view.
 *
 * @author Kan Lin
 *
 */
public class SimpleFooterView extends DescriptionView {

    private final String leftText;

    private final String rightText;

    private final int fontSize;
    
    private int y;

    /**
     * Constructor.
     * 
     * @param leftText
     * @param rightText
     * @param fontSize
     */
    public SimpleFooterView(String leftText, String rightText, int fontSize) {
        this.leftText = leftText;
        this.rightText = rightText;
        this.fontSize = fontSize;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getHeight() {
        return 30;
    }
    
    @Override
    public void arrange(Paper paper) {
    	this.y = paper.getContentSize().height;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws Exception {
        PDFont font = cv.getDoc().getFont();

        int fontH = PDFUtil.getContentHeight("A", font, this.fontSize) + 15;
        
        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, AppendMode.APPEND, false, false);
        contentStream.setFont(font, this.fontSize);

        contentStream.moveTo(cv.getPaper().getLeft(), cv.getDrawingBottom() - 10);
        contentStream.lineTo(cv.getPaper().getRight(), cv.getDrawingBottom() - 10);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getLeft(), cv.getPaper().getDrawingBottom() - fontH);
        contentStream.showText(this.leftText);
        contentStream.endText();

        String pageIndex = getPageIndex(page) + " / " + getPageCount();
        int cx = PDFUtil.getContentWidth(pageIndex, font, this.fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getCenterX() - cx / 2, cv.getPaper().getDrawingBottom() - fontH);
        contentStream.showText(pageIndex);
        contentStream.endText();

        int rx = PDFUtil.getContentWidth(this.rightText, font, this.fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getRight() - rx, cv.getPaper().getDrawingBottom() - fontH);
        contentStream.showText(this.rightText);
        contentStream.endText();

        contentStream.close();
    }
}
