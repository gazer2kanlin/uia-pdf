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

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class DrawingUtils {

    public static int getContentWidth(String content, PDFont font, int fontSize) throws IOException {
        if (content == null) {
            return 0;
        }
        try {
        	return (int) (font.getStringWidth(content) / 1000 * fontSize);
        }
        catch(IOException ex) {
        	System.out.println(content + " font failed");
        	throw ex;
        }
    }

    public static int[] getContentWidths(String[] content, PDFont font, int fontSize) throws IOException {
        int[] widths = new int[content.length];
        for (int i = 0; i < content.length; i++) {
            widths[i] = getContentWidth(content[i], font, fontSize);
        }
        return widths;
    }

    public static int getContentAscent(PDFont font, int fontSize) {
    	float h = font.getFontDescriptor().getAscent();
        return (int) (fontSize * h / 1000);
    }

    public static int getContentHeight(PDFont font, int fontSize) {
    	float h = font.getFontDescriptor().getDescent() + font.getFontDescriptor().getAscent() ;
        return (int) (h / 1000 * fontSize);
    }

    public static int getContentDescent(PDFont font, int fontSize) {
    	float h = font.getFontDescriptor().getDescent();
        return (int) (fontSize * h / 1000);
    }

    public static int getContentWrapHeight(String content, PDFont font, int fontSize, int maxWidth, List<String> splitResult) throws Exception {
        int h = getContentHeight(font, fontSize);
        if (content == null) {
            return h;
        }

        for (int c = 2; c <= content.length(); c++) {
            if (getContentWidth(content.substring(0, c), font, fontSize) >= maxWidth) {
                splitResult.add(content.substring(0, c - 1));
                getContentWrapHeight(content.substring(c - 1, content.length()), font, fontSize, maxWidth, splitResult);
                return splitResult.size() * (h + 2);
            }
        }
        splitResult.add(content);
        return splitResult.size() * (h + 2);
    }

    /**
     * Trim content to fit the max width.
     * 
     * @param content Content.
     * @param font Font
     * @param fontSize font size.
     * @param maxWidth Max width.
     * @return Result.
     * @throws IOException
     */
    public static String trimContent(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
        if (getContentWidth(content, font, fontSize) < maxWidth) {
            return content;
        }
        else {
            return trimContent(content.substring(0, content.length() - 1), font, fontSize, maxWidth);
        }
    }

    public static int fixFontSize(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
        int w = getContentWidth(content, font, fontSize);
        return w < maxWidth || fontSize <= 2 ? fontSize : fixFontSize(content, font, fontSize - 1, maxWidth);
    }

    /**
     *
     * @param rgbString
     * @return
     */
    public static Color toColor(String rgbString) {
        if (rgbString == null) {
            return null;
        }

        String[] rgb = rgbString.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

}
