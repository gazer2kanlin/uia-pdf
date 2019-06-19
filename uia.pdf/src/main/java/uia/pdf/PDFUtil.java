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

public class PDFUtil {

    /**
     * Get height.
     * @param content
     * @param font
     * @param fontSize
     * @return
     */
    public static int getContentHeight(String content, PDFont font, int fontSize) {
        return content == null ? 0 : (int) (font.getFontDescriptor().getAscent() / 1000 * fontSize);
    }

    public static int getContentWidth(String content, PDFont font, int fontSize) throws Exception {
        if (content == null) {
            return 0;
        }
        try {
        	return (int) (font.getStringWidth(content) / 1000 * fontSize);
        }
        catch(Exception ex) {
        	System.out.println(content + " font failed");
        	throw ex;
        }
    }

    public static int[] getContentWidths(String[] content, PDFont font, int fontSize) throws Exception {
        int[] widths = new int[content.length];
        for (int i = 0; i < content.length; i++) {
            widths[i] = getContentWidth(content[i], font, fontSize);
        }
        return widths;
    }

    public static int fixFontSize(String content, PDFont font, int fontSize, int maxWidth) throws Exception {
        int w = getContentWidth(content, font, fontSize);
        return w < maxWidth || fontSize < 0 ? fontSize : fixFontSize(content, font, fontSize - 1, maxWidth);
    }

    /**
     * Cut content based on max width.
     * @param content Content.
     * @param font Font
     * @param fontSize font size.
     * @param maxWidth Max width.
     * @return Cut content.
     * @throws IOException
     */
    public static String cutContent(String content, PDFont font, int fontSize, int maxWidth) throws Exception {
        if (getContentWidth(content, font, fontSize) < maxWidth) {
            return content;
        }
        else {
            return cutContent(content.substring(0, content.length() - 1), font, fontSize, maxWidth);
        }
    }

    /**
     * Split content based on max width.
     * @param content Content.
     * @param font Font
     * @param fontSize font size.
     * @param maxWidth Max width.
     * @param result Split result.
     * @throws IOException
     */
    public static int splitContent(String content, PDFont font, int fontSize, int maxWidth, List<String> result) throws Exception {
        if (content == null) {
            return getContentHeight("", font, fontSize) + 8;
        }

        for (int c = 2; c <= content.length(); c++) {
            if (getContentWidth(content.substring(0, c), font, fontSize) >= maxWidth) {
                result.add(content.substring(0, c - 1));
                splitContent(content.substring(c - 1, content.length()), font, fontSize, maxWidth, result);

                int h = getContentHeight("", font, fontSize);
                return result.size() * (h + 8);
            }
        }
        result.add(content);

        int h = getContentHeight("", font, fontSize);
        return result.size() * (h + 8);
    }

    /**
     *
     * Value has 4 format: fixed value, % value, % value with + prefix, % value with * prefix.<br>
     * fixed value: actual width.<br>
     * % value: calculate based on full size.<br>
     * % value with + prefix: calculate based on (full size - offset).<br>
     * % value with * prefix: calculate based on full size then reduce offset.<br>
     *
     * @param sizeStr
     * @param fullSize
     * @param offset
     * @return
     */
    public static int sizing(String sizeStr, int fullSize, int offset) {
        if (sizeStr.endsWith("%")) {
            if (sizeStr.startsWith("+")) {
            	// +20%, 100, 15 = (100 - 15) * 0.2
                int p = Integer.parseInt(sizeStr.substring(1, sizeStr.length() - 1));
                return (fullSize - offset) * p / 100;
            }
            else if (sizeStr.startsWith("*")) {
            	// *20%, 100, 15 = 100 * 0.2 - 15
                int p = Integer.parseInt(sizeStr.substring(1, sizeStr.length() - 1));
                return fullSize * p / 100 - offset;
            }
            else {
            	// 20%, 100, 15 = 100 * 0.2
                int p = Integer.parseInt(sizeStr.substring(0, sizeStr.length() - 1));
                return fullSize * p / 100;
            }
        }
        else {
        	// 20, 100, 15 = 20
            return Integer.parseInt(sizeStr);
        }
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
