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

package uia.pdf.renderers;

import java.awt.Color;
import java.awt.Point;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import uia.pdf.ContentView;
import uia.pdf.DrawingUtils;

/**
 *
 * @author Kan Lin
 *
 */
public class TextDataRenderer implements DataRenderer {

    @Override
    public void paint(PDPageContentStream contentStream, Point topLeft, ContentView view, Object data, DataBlock dataBlock) throws Exception {
        String content = view.getDoc().getValueParserFactory().parse(data);
        if (content == null) {
            return;
        }

        PDFont font = view.getDoc().getFont();
        int cw = DrawingUtils.getContentWidth(content, font, dataBlock.fontSize);
        int cha = DrawingUtils.getContentAscent(font, dataBlock.fontSize);
        int chd = DrawingUtils.getContentDescent(font, dataBlock.fontSize);
        int textLine;
        switch(dataBlock.valignment) {
	        case NEAR:
	            textLine = topLeft.y - cha - 2;
	            break;
	        case FAR:
	            textLine = topLeft.y - dataBlock.height + 3 + chd;
	            break;
	        default:
	            textLine = topLeft.y - (dataBlock.height - cha - chd) / 2 - cha;
        }

        contentStream.setFont(font, dataBlock.fontSize);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.beginText();
        switch(dataBlock.alignment) {
	        case NEAR:
	            contentStream.newLineAtOffset(topLeft.x + 2, textLine);
	            break;
	        case FAR:
	            contentStream.newLineAtOffset(topLeft.x + dataBlock.width - cw - 2, textLine);
	            break;
	        default:
	            contentStream.newLineAtOffset(topLeft.x + (dataBlock.width - cw) / 2, textLine);
        }
        contentStream.showText(content);
        contentStream.endText();
    }
}
