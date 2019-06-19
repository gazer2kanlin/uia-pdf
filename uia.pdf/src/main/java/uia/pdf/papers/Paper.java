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
 * The coordinate of (0,0) is at bottom-left.<br>
 * The coordinate of (width,height) is at top-right.<br>
 * 
 * @author Kyle K. Lin
 *
 */
public class Paper {
	
	/**
	 * A3 portrait
	 */
	public static final Paper A3 = A3Paper.portrait();
	
	/**
	 * A3 landscape
	 */
	public static final Paper A3L = A3Paper.landscape();
	
	/**
	 * A4 portrait
	 */
	public static final Paper A4 = A4Paper.portrait();
	
	/**
	 * A4 landscape
	 */
	public static final Paper A4L = A4Paper.landscape();
	
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
    protected Paper(PDRectangle rect) {
        this.rect = rect;
        this.leftPadding = 30;
        this.rightPadding = 30;
        this.topPadding = 30;
        this.bottomPadding = 60;
    }
    
    public Paper clone() {
    	return new Paper(this.rect);
    }

    /**
     * Get padding size of left side.
     * 
     * @return The size.
     */
    public int getLeftPadding() {
        return this.leftPadding;
    }

    /**
     * Get padding size at the left.
     * 
     * @param leftPadding size.
     */
    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    /**
     * Get padding size of right side.
     * 
     * @return The size.
     */
    public int getRightPadding() {
        return this.rightPadding;
    }

    /**
     * Get padding size at the right.
     * 
     * @param rightPadding The size.
     */
    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    /**
     * Get padding size of top side.
     * @return The size.
     */
    public int getTopPadding() {
        return this.topPadding;
    }

    /**
     * Get padding size of top side.
     * 
     * @param topPadding The size.
     */
    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    /**
     * Get padding size at the bottom.
     * 
     * @return The size.
     */
    public int getBottomPadding() {
        return this.bottomPadding;
    }

    /**
     * Get padding size of bottom side.
     * 
     * @return bottomPadding The size.
     */
    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    /**
     * Get x coordinate.
     * @return X coordinate
     */
    public int getCenterX() {
        return (int) (this.rect.getWidth() / 2);
    }

    /**
     * Get the coordinates of the left side of the content view.
     * 
     * @return The coordinates.
     */
    public int getLeft() {
        return this.leftPadding;
    }

    /**
     * Get the coordinates of the right side of the content view.
     * 
     * @return The coordinates.
     */
    public int getRight() {
        return (int) this.rect.getWidth() - this.rightPadding;
    }

    /**
     * Get the coordinates of the top side of the content view.
     * 
     * @return The coordinates.
     */
    public int getDrawingTop() {
        return (int) this.rect.getHeight() - this.topPadding;
    }

    /**
     * Get the coordinates of the bottom side of the content view.
     * 
     * @return The coordinates.
     */
    public int getDrawingBottom() {
        return this.bottomPadding;
    }

    /**
     * Create a page.
     * 
     * @return A page.
     */
    public PDPage createPage() {
        return new PDPage(this.rect);
    }

    /**
     * Get the size of the content view.
     * 
     * @return The size.
     */
    public Dimension getContentSize() {
        return new Dimension(getRight() - getLeft(), getDrawingTop() - getDrawingBottom());
    }
    
    @Override
    public String toString() {
    	return String.format("paper:(%s,%s) viewport:(%s,%s)", 
    			this.rect.getWidth(), 
    			this.rect.getHeight(),
    			getContentSize().width,
    			getContentSize().height);
    }
}
