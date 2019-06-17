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

    public static int getContentWidth(String content, PDFont font, int fontSize) throws IOException {
        if (content == null) {
            return 0;
        }
        return (int) (font.getStringWidth(content) / 1000 * fontSize);
    }

    public static int[] getContentWdidths(String[] content, PDFont font, int fontSize) throws IOException {
        int[] widths = new int[content.length];
        for (int i = 0; i < content.length; i++) {
            widths[i] = getContentWidth(content[i], font, fontSize);
        }
        return widths;
    }

    public static int fixFontSzie(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
        int w = getContentWidth(content, font, fontSize);
        return w < maxWidth ? fontSize : fixFontSzie(content, font, fontSize - 1, maxWidth);
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
    public static String cutContent(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
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
    public static int split(String content, PDFont font, int fontSize, int maxWidth, List<String> result) throws IOException {
        if (content == null) {
            return getContentHeight("", font, fontSize) + 8;
        }

        for (int c = 2; c <= content.length(); c++) {
            if (getContentWidth(content.substring(0, c), font, fontSize) >= maxWidth) {
                result.add(content.substring(0, c - 1));
                split(content.substring(c - 1, content.length()), font, fontSize, maxWidth, result);

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
     * @param value
     * @param fullSize
     * @param offset
     * @return
     */
    public static int calculateWidth(String value, int fullSize, int offset) {
        if (value.endsWith("%")) {
            if (value.startsWith("+")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return (fullSize - offset) * p / 100;
            }
            else if (value.startsWith("*")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return fullSize * p / 100 - offset;
            }
            else {
                int p = Integer.parseInt(value.substring(0, value.length() - 1));
                return fullSize * p / 100;
            }
        }
        else {
            if (value.startsWith("-")) {
                return fullSize + Integer.parseInt(value) - offset;
            }
            else {
                return Integer.parseInt(value);
            }
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
