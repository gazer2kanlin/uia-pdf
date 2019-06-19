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

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.PDFUtil;
import uia.pdf.gridbag.model.Cell;

/**
 *
 * @author Kan Lin
 *
 */
public class DefaultBindCellRenderer implements GridBagCellRenderer {

    @Override
    public void paint(PDPageContentStream contentStream, Point bottomLeft, ContentView view, Cell cell, Object value) throws Exception {
        PDFont font = view.getDoc().getFont();
        int fontSize = cell.getFontSize();
        contentStream.setFont(font, fontSize);
        String content = view.getDoc().getValueParserFactory().parse(value);
        if (content == null) {
            return;
        }

        int cw = PDFUtil.getContentWidth(content, font, fontSize);
        int ch = PDFUtil.getContentHeight(content, font, fontSize);
        int textLine = bottomLeft.y + (cell.getHeight() - ch) / 2;

        contentStream.setNonStrokingColor(Color.black);
        contentStream.beginText();
        if ("NEAR".equalsIgnoreCase(cell.getAlignment())) {
            contentStream.newLineAtOffset(bottomLeft.x + 2, textLine);
        }
        else if ("FAR".equalsIgnoreCase(cell.getAlignment())) {
            contentStream.newLineAtOffset(bottomLeft.x + cell.getWidth() - cw - 2, textLine);
        }
        else {
            contentStream.newLineAtOffset(bottomLeft.x + (cell.getWidth() - cw) / 2, textLine);
        }
        contentStream.showText(content);
        contentStream.endText();
    }
}
