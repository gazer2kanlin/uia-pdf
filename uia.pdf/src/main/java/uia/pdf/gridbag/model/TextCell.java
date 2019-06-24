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

package uia.pdf.gridbag.model;

import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.DrawingUtils;
import uia.pdf.gridbag.GridBagModel;
import uia.pdf.gridbag.layout.TextCellType;

/**
 * Text cell.
 *
 * @author Kan Lin
 *
 */
public class TextCell extends Cell {

    public final Text text;

    public final Text subText;

    public TextCell(TextCellType tct, GridBagModel grid, int rowIndex, int columnIndex) {
        super(tct, grid, rowIndex, columnIndex);
        this.text = new Text(tct.getText(), this);
        this.subText = new Text(tct.getSubtext(), this);
    }

    @Override
    public void draw(PDPageContentStream contentStream, Point topLeft, ContentView cv, Object data) {
        if (this.text.value == null) {
            return;
        }

        try {
        	PDFont font = cv.getDoc().getFont();
        	
            // text
            int fontSize1 = DrawingUtils.fixFontSize(this.text.value, font, this.text.getFontSize(), getWidth());
            int cw1 = DrawingUtils.getContentWidth(this.text.value, font, fontSize1);
            int ch1a = DrawingUtils.getContentAscent(font, fontSize1);
            int ch1d = DrawingUtils.getContentDescent(font, fontSize1);

            // subText
            int fontSize2 = DrawingUtils.fixFontSize(this.subText.value, font, this.subText.getFontSize(), getWidth());
            int cw2 = DrawingUtils.getContentWidth(this.subText.value, font, fontSize2);
            int ch2a = DrawingUtils.getContentAscent(font, fontSize2);
            int ch2d = DrawingUtils.getContentDescent(font, fontSize2);

            int textLine = 0;
            if ("NEAR".equals(getVAlignment())) {
                textLine = topLeft.y - 2 - ch1a;
            }
            else if ("FAR".equals(getVAlignment())) {
                textLine = topLeft.y - getHeight() + 3 + ch2d + ch2a + 3 + ch1d;
            }
            else {
                textLine = topLeft.y - getHeight() / 2 + ch1d + 1;
            }

            contentStream.setFont(font, fontSize1);
            contentStream.setNonStrokingColor(this.text.foreground);
            contentStream.beginText();
            if ("NEAR".equalsIgnoreCase(getAlignment())) {
                contentStream.newLineAtOffset(topLeft.x + 2, textLine);
            }
            else if ("FAR".equalsIgnoreCase(getAlignment())) {
                contentStream.newLineAtOffset(topLeft.x + getWidth() - cw1 - 2, textLine);
            }
            else {
                contentStream.newLineAtOffset(topLeft.x + (getWidth() - cw1) / 2, textLine);
            }
            contentStream.showText(this.text.value);
            contentStream.endText();

            if (this.subText.value != null) {
                textLine = textLine - ch1d - 3 - ch2a ;
                contentStream.setFont(font, fontSize2);
                contentStream.setNonStrokingColor(this.subText.foreground);
                contentStream.beginText();
                if ("NEAR".equalsIgnoreCase(getAlignment())) {
                    contentStream.newLineAtOffset(topLeft.x + 2, textLine);
                }
                else if ("FAR".equalsIgnoreCase(getAlignment())) {
                    contentStream.newLineAtOffset(topLeft.x + getWidth() - cw2 - 2, textLine);
                }
                else {
                    contentStream.newLineAtOffset(topLeft.x + (getWidth() - cw2) / 2, textLine);
                }
                contentStream.showText(this.subText.value);
                contentStream.endText();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("textCell[%2s,%2s](x=%3s,y=%3s,w=%3s,h=%3s) text:%s, subText:%s",
                this.row,
                this.col,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                this.text,
                this.subText);
    }
}
