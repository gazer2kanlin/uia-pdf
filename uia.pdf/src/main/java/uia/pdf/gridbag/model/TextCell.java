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
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFUtil;
import uia.pdf.gridbag.GridBagDrawer;
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

    public TextCell(TextCellType tct, GridBag grid, int rowIndex, int columnIndex) {
        super(tct, grid, rowIndex, columnIndex);
        this.text = new Text(tct.getText(), this);
        this.subText = new Text(tct.getSubtext(), this);
    }

    @Override
    public void accept(ContentView cv, GridBagDrawer view, PDPageContentStream contentStream, Point bottomLeft, Map<String, Object> data) {
        if (this.text.value == null) {
            return;
        }

        try {
            PDFont font = cv.getDoc().getFont();

            int fontSize1 = PDFUtil.fixFontSzie(this.text.value, font, this.text.getFontSize(), getWidth());
            int cw1 = PDFUtil.getContentWidth(this.text.value, font, fontSize1);
            int ch1 = PDFUtil.getContentHeight(this.text.value, font, fontSize1);

            int fontSize2 = PDFUtil.fixFontSzie(this.subText.value, font, this.subText.getFontSize(), getWidth());
            int cw2 = PDFUtil.getContentWidth(this.subText.value, font, fontSize2);
            int ch2 = PDFUtil.getContentHeight(this.subText.value, font, fontSize2);

            int textLine = bottomLeft.y + getHeight() / 2 - 3;
            if ("NEAR".equals(getVAlignment())) {
                textLine = bottomLeft.y + getHeight() - ch1 - 3;
            }
            else if ("FAR".equals(getVAlignment())) {
                textLine = ch2 == 0 ? bottomLeft.y + 3 : bottomLeft.y + ch2 + 9;
            }
            else {
                textLine = bottomLeft.y + ch2 + (getHeight() - (ch1 + ch2)) / 2 + 1;
            }

            contentStream.setFont(font, fontSize1);
            contentStream.setNonStrokingColor(this.text.foreground);
            contentStream.beginText();
            if ("NEAR".equalsIgnoreCase(getAlignment())) {
                contentStream.newLineAtOffset(bottomLeft.x + 2, textLine);
            }
            else if ("FAR".equalsIgnoreCase(getAlignment())) {
                contentStream.newLineAtOffset(bottomLeft.x + getWidth() - cw1 - 2, textLine);
            }
            else {
                contentStream.newLineAtOffset(bottomLeft.x + (getWidth() - cw1) / 2, textLine);
            }
            contentStream.showText(this.text.value);
            contentStream.endText();

            if (this.subText.value != null) {
                textLine -= ch2;
                contentStream.setFont(font, fontSize2);
                contentStream.setNonStrokingColor(this.subText.foreground);
                contentStream.beginText();
                if ("NEAR".equalsIgnoreCase(getAlignment())) {
                    contentStream.newLineAtOffset(bottomLeft.x + 2, textLine);
                }
                else if ("FAR".equalsIgnoreCase(getAlignment())) {
                    contentStream.newLineAtOffset(bottomLeft.x + getWidth() - cw2 - 2, textLine);
                }
                else {
                    contentStream.newLineAtOffset(bottomLeft.x + (getWidth() - cw2) / 2, textLine);
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
