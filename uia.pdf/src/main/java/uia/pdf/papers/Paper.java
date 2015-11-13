package uia.pdf.papers;

import java.awt.Dimension;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public abstract class Paper {

    private final PDRectangle rect;

    private int leftPadding;

    private int rightPadding;

    private int topPadding;

    private int bottomPadding;

    public Paper(PDRectangle rect) {
        this.rect = rect;
        this.leftPadding = 30;
        this.rightPadding = 30;
        this.topPadding = 30;
        this.bottomPadding = 30;
    }

    public int getLeftPadding() {
        return this.leftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public int getRightPadding() {
        return this.rightPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public int getTopPadding() {
        return this.topPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public int getBottomPadding() {
        return this.bottomPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public int getCenterX() {
        return (int) (this.rect.getWidth() / 2);
    }

    public int getLeft() {
        return this.leftPadding;
    }

    public int getRight() {
        return (int) this.rect.getWidth() - this.rightPadding;
    }

    public int getTop() {
        return (int) this.rect.getHeight() - this.topPadding;
    }

    public int getBottom() {
        return this.bottomPadding;
    }

    public PDPage createPage() {
        return new PDPage(this.rect);
    }

    public Dimension getViewSzie() {
        return new Dimension(getRight() - getLeft(), getTop() - getBottom());
    }
}
