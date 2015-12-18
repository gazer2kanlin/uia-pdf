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

package uia.pdf.grid;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.PDFUtil;
import uia.pdf.grid.ColumnModel.AlignmentType;

/**
 * Default implementation of cell renderer.
 *
 * @author Kan Lin
 *
 */
public class DefaultCellRenderer implements CellRenderer {

    @Override
    public int paint(PDPageContentStream contentStream, Point topLeft, GridView view, ColumnModel cm, Object value, int row, int col) {
        if (cm.isWrap()) {
            return wrap(contentStream, topLeft, view, cm, value, row, col);
        }
        else {
            return nowrap(contentStream, topLeft, view, cm, value, row, col);
        }

    }

    private int nowrap(PDPageContentStream contentStream, Point topLeft, GridView view, ColumnModel cm, Object value, int row, int col) {
        int h = view.getFontSize();

        try {
            PDFont font = view.getDoc().getFont();
            h = PDFUtil.getStringHeight("", font, view.getFontSize());

            String content = view.getValueParserFactory().parse(value);
            int contentWidth = PDFUtil.getStringWidth(content, font, view.getFontSize());
            if (cm.getWidth() - contentWidth < 4) {
                content = PDFUtil.cutString(content, font, view.getFontSize(), cm.getWidth() - 4);
                contentWidth = PDFUtil.getStringWidth(content, font, view.getFontSize());
            }
            contentWidth = Math.min(cm.getWidth() - 4, contentWidth);

            int offset = 3;
            if (cm.getHorizontalAlignment() == AlignmentType.FAR) {
                offset = cm.getWidth() - contentWidth - 3;
            }
            else if (cm.getHorizontalAlignment() == AlignmentType.CENTER) {
                offset = (cm.getWidth() - contentWidth) / 2;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(topLeft.x + offset, topLeft.y - h - 3);
            contentStream.showText(content);
            contentStream.endText();

        }
        catch (Exception ex) {

        }

        return h + 8;
    }

    private int wrap(PDPageContentStream contentStream, Point topLeft, GridView view, ColumnModel cm, Object value, int row, int col) {
        int h = view.getFontSize();

        try {
            PDFont font = view.getDoc().getFont();
            h = PDFUtil.getStringHeight("", font, view.getFontSize());

            String content = view.getValueParserFactory().parse(value);

            ArrayList<String> cs = new ArrayList<String>();
            PDFUtil.split(content, font, view.getFontSize(), cm.getWidth() - 4, cs);

            int ht = 0;
            for (int i = 0; i < cs.size(); i++) {
                int contentWidth = PDFUtil.getStringWidth(cs.get(i), font, view.getFontSize());
                contentWidth = Math.min(cm.getWidth() - 4, contentWidth);

                int offset = 3;
                if (cm.getHorizontalAlignment() == AlignmentType.FAR) {
                    offset = cm.getWidth() - contentWidth - 3;
                }
                else if (cm.getHorizontalAlignment() == AlignmentType.CENTER) {
                    offset = (cm.getWidth() - contentWidth) / 2;
                }
                contentStream.beginText();
                contentStream.newLineAtOffset(topLeft.x + offset, topLeft.y - ht - h - 3);
                contentStream.showText(cs.get(i));
                contentStream.endText();

                ht += 12;
            }

            ht += 5;

            return ht;
        }
        catch (Exception ex) {
            return h;
        }

    }
}
