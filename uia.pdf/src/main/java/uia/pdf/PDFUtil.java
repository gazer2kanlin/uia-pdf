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

    public static int getStringHeight(String content, PDFont font, int fontSize) {
        return content == null ? 0 : (int) (font.getFontDescriptor().getAscent() / 1000 * fontSize);
    }

    public static int getStringWidth(String content, PDFont font, int fontSize) throws IOException {
        if (content == null) {
            return 0;
        }
        return (int) (font.getStringWidth(content) / 1000 * fontSize);
    }

    public static int[] getStringWdidths(String[] content, PDFont font, int fontSize) throws IOException {
        int[] widths = new int[content.length];
        for (int i = 0; i < content.length; i++) {
            widths[i] = getStringWidth(content[i], font, fontSize);
        }
        return widths;
    }

    public static int fixFontSzie(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
        int w = getStringWidth(content, font, fontSize);
        return w < maxWidth ? fontSize : fixFontSzie(content, font, fontSize - 1, maxWidth);
    }

    public static String cutString(String content, PDFont font, int fontSize, int maxWidth) throws IOException {
        if (getStringWidth(content, font, fontSize) < maxWidth) {
            return content;
        }
        else {
            return cutString(content.substring(0, content.length() - 1), font, fontSize, maxWidth);
        }
    }

    public static void split(String content, PDFont font, int fontSize, int maxWidth, List<String> result) throws IOException {
        if (content == null) {
            return;
        }

        for (int c = 0; c <= content.length(); c++) {
            if (getStringWidth(content.substring(0, c), font, fontSize) >= maxWidth) {
                result.add(content.substring(0, c - 1));
                split(content.substring(c - 1, content.length()), font, fontSize, maxWidth, result);
                return;
            }
        }
        result.add(content);
    }

    public static int calculate(String value, int total, int offset) {
        if (value.endsWith("%")) {
            if (value.startsWith("+")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return (total - offset) * p / 100;
            }
            else if (value.startsWith("*")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return total * p / 100 - offset;
            }
            else {
                int p = Integer.parseInt(value.substring(0, value.length() - 1));
                return total * p / 100;
            }
        }
        else {
            return Integer.parseInt(value);
        }
    }

    public static Color toColor(String rgbString) {
        if (rgbString == null) {
            return null;
        }

        String[] rgb = rgbString.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

}
