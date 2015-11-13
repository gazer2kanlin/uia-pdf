package uia.pdf.papers;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class A3Paper extends Paper {

    public A3Paper() {
        this(false);
    }

    public A3Paper(boolean landscape) {
        super(landscape ? new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth()) : PDRectangle.A3);
        // super(landscape ? new PDRectangle(PDPage.PAGE_SIZE_A3.getHeight(), PDPage.PAGE_SIZE_A3.getWidth()) : PDPage.PAGE_SIZE_A3);
    }
}
