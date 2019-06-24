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
	public static final Paper A3 = new A3Paper(false);
	
	/**
	 * A3 landscape
	 */
	public static final Paper A3L = new A3Paper(true);
	
	/**
	 * A4 portrait
	 */
	public static final Paper A4 = new A4Paper(false);
	
	/**
	 * A4 landscape
	 */
	public static final Paper A4L = new A4Paper(true);
	/**
	 * B4 portrait
	 */
	public static final Paper B4 = new B4Paper(false);
	
	/**
	 * B4 landscape
	 */
	public static final Paper B4L = new B4Paper(true);
	
    private final PDRectangle rect;

    private final int leftPadding;

    private final int rightPadding;

    private final int topPadding;

    private final int bottomPadding;

    /**
     * Constructor.<br>
     *
     * @param rect Rectangle of page.
     */
    protected Paper(PDRectangle rect) {
    	this(rect, 30, 60, 30, 60);
    }

    /**
     * Constructor.<br>
     *
     * @param rect Rectangle of page.
     */
    protected Paper(PDRectangle rect, int left, int top, int right, int bottom) {
        this.rect = rect;
        this.leftPadding = left;
        this.topPadding = top;
        this.rightPadding = right;
        this.bottomPadding = bottom;
    }
    
    public Paper padding(int left, int top, int right, int bottom) {
    	return new Paper(this.rect, left, top, right, bottom); 
    }
    
    public Paper paddingLR(int left, int right) {
    	return new Paper(this.rect, left, this.topPadding, right, this.bottomPadding); 
    }
    
    public Paper paddingTB(int top, int bottom) {
    	return new Paper(this.rect, this.leftPadding, top, this.rightPadding, bottom); 
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
     * Get padding size of right side.
     * 
     * @return The size.
     */
    public int getRightPadding() {
        return this.rightPadding;
    }

    /**
     * Get padding size of top side.
     * @return The size.
     */
    public int getTopPadding() {
        return this.topPadding;
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
     * Get the coordinates of the left side of the content view.
     * 
     * @return The coordinates.
     */
    public int getContentLeft() {
        return this.leftPadding;
    }

    /**
     * Get the coordinates of the right side of the content view.
     * 
     * @return The coordinates.
     */
    public int getContentRight() {
        return (int) this.rect.getWidth() - this.rightPadding;
    }

    /**
     * Get x coordinate.
     * @return X coordinate
     */
    public int getCenterX() {
        return (int) (this.rect.getWidth() / 2);
    }

    /**
     * Get the size of the content view.
     * 
     * @return The size.
     */
    public Dimension getContentSize() {
        return new Dimension(getContentRight() - getContentLeft(), getDrawingTop() - getDrawingBottom());
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
    
    @Override
    public String toString() {
    	return String.format("paper:(%s,%s) content:(%s,%s)", 
    			this.rect.getWidth(), 
    			this.rect.getHeight(),
    			getContentSize().width,
    			getContentSize().height);
    }
}
