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

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Header view.
 *
 * @author Kan Lin
 *
 */
public class SimpleHeaderView extends DescriptionView {

    private String title;

    private int fontSize;

    /**
     *
     * @param title
     * @param fontSize
     */
    public SimpleHeaderView(String title, int fontSize) {
        this.title = title;
        this.fontSize = fontSize;
    }

    @Override
    public int getHeight() {
        return this.fontSize + 12;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        PDFont font = cv.getDoc().getFont();

        PDPageContentStream contentStream = new PDPageContentStream(cv.getDoc().getDocument(), page, true, false, false);
        contentStream.setFont(font, this.fontSize);

        contentStream.beginText();
        contentStream.newLineAtOffset(cv.getPaper().getLeft(), cv.getPaper().getTop() - this.fontSize);
        contentStream.showText(this.title);
        contentStream.endText();

        contentStream.close();
    }
}
