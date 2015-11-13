package uia.pdf;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

public class PDFUtilTest {

    @Test
    public void testSplit() throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        PDFUtil.split("1234567890ABCDEFG", PDType1Font.COURIER, 12, 50, result);
        for (String text : result) {
            System.out.println(text);
        }
    }
}
