package uia.pdf.gridbag;

import java.awt.Color;

import uia.pdf.PDFUtil;

public class Text {

    public final String value;

    public final int fontSize;

    public final Color foreground;

    Text() {
        this.value = "";
        this.foreground = Color.BLACK;
        this.fontSize = 9;
    }

    public Text(String value, String rgbString, int fontSize) {
        this.value = value;
        this.foreground = rgbString == null ? Color.BLACK : PDFUtil.toColor(rgbString);
        this.fontSize = fontSize;
    }

    public Text(String value, Color foreground, int fontSize) {
        this.value = value;
        this.foreground = foreground;
        this.fontSize = fontSize;
    }
}
