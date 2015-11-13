package uia.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFUtil {

    public static int getStringHeight(PDFont font, int fontSize) {
        return (int) (font.getFontDescriptor().getAscent() / 1000 * fontSize);
    }

    public static int getStringWidth(String content, PDFont font, int fontSize) throws IOException {
        if (content == null) {
            return 0;
        }
        return (int) (font.getStringWidth(content) / 1000 * fontSize);
    }

    public static int[] getStringWditds(String[] content, PDFont font, int fontSize) throws IOException {
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

    public static void split(String content, PDFont font, int fontSize, int maxWidth, List<String> result) throws IOException {
        for (int c = 0; c < content.length(); c++) {
            int w = getStringWidth(content.substring(0, c), font, fontSize);
            if (w >= maxWidth) {
                result.add(content.substring(0, c));
                split(content.substring(c, content.length()), font, fontSize, maxWidth, result);
                return;
            }
        }
        result.add(content);
    }

    public static Color toColor(String rgbString) {
        if (rgbString == null) {
            return null;
        }

        String[] rgb = rgbString.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

}
