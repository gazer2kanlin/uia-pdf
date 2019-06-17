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

package uia.pdf.papers;

import java.awt.Dimension;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * Paper definition.
 *
 * @author Kan Lin
 *
 */
public abstract class Paper {
	
    private final PDRectangle rect;

    private int leftPadding;

    private int rightPadding;

    private int topPadding;

    private int bottomPadding;

    /**
     * Constructor.<br>
     * Default value of padding is 30.
     *
     * @param rect Rectangle of page.
     */
    public Paper(PDRectangle rect) {
        this.rect = rect;
        this.leftPadding = 30;
        this.rightPadding = 30;
        this.topPadding = 30;
        this.bottomPadding = 30;
    }

    /**
     * Get padding size of left side.
     * @return Size.
     */
    public int getLeftPadding() {
        return this.leftPadding;
    }

    /**
     * Set padding size of left side.
     * @param leftPadding size.
     */
    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    /**
     * Get padding size of right side.
     * @return Size.
     */
    public int getRightPadding() {
        return this.rightPadding;
    }

    /**
     * Set padding size of right side.
     * @param rightPadding Size.
     */
    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    /**
     * Get padding size of top side.
     * @return Size.
     */
    public int getTopPadding() {
        return this.topPadding;
    }

    /**
     * Set padding size of top side.
     * @param topPadding Size.
     */
    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    /**
     * Get padding size of bottom side.
     * @return Size.
     */
    public int getBottomPadding() {
        return this.bottomPadding;
    }

    /**
     * Set padding size of bottom side.
     * @return bottomPadding Size.
     */
    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    /**
     * Get center x position.
     * @return X position.
     */
    public int getCenterX() {
        return (int) (this.rect.getWidth() / 2);
    }

    /**
     * Get left position of drawable area.
     * @return Left position.
     */
    public int getLeft() {
        return this.leftPadding;
    }

    /**
     * Get left position of drawable area.
     * @return Right position.
     */
    public int getRight() {
        return (int) this.rect.getWidth() - this.rightPadding;
    }

    /**
     * Get top position of drawable area.
     * @return Top position.
     */
    public int getTop() {
        return (int) this.rect.getHeight() - this.topPadding;
    }

    /**
     * Get bottom position of drawable area.
     * @return Botoom position.
     */
    public int getBottom() {
        return this.bottomPadding;
    }

    /**
     * Create a page based on paper size.
     * @return Page.
     */
    public PDPage createPage() {
        return new PDPage(this.rect);
    }

    /**
     * Get drawable size.
     * @return Size.
     */
    public Dimension getDrawableSize() {
        return new Dimension(getRight() - getLeft(), getTop() - getBottom());
    }
}
