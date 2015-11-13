package uia.pdf.papers;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class A4Paper extends Paper {

    public A4Paper() {
        this(false);
    }

    public A4Paper(boolean landscape) {
        super(landscape ? new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()) : PDRectangle.A4);
        // super(landscape ? new PDRectangle(PDPage.PAGE_SIZE_A4.getHeight(), PDPage.PAGE_SIZE_A4.getWidth()) : PDPage.PAGE_SIZE_A4);
    }
}
