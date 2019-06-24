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

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import uia.pdf.papers.Paper;

/**
 * Content view.
 *
 * @author Kan Lin
 *
 */
public abstract class ContentView {

    private String name;

    private Date time;

    protected final PDFDoc pdf;

    protected final Paper paper;

    protected final ArrayList<PDPage> pages;

    protected DescriptionView headerView;

    protected DescriptionView footerView;

    /**
     * Constructor.
     * @param pdf PDF maker.
     * @param paper paper.
     */
    protected ContentView(PDFDoc pdf, Paper paper) {
        this.pdf = pdf;
        this.paper = paper;
        this.pages = new ArrayList<PDPage>();
        this.time = new Date();
    }

    /**
     * Get the name.
     * @return Name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name.
     * @param name Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get time.
     * @return Time.
     */
    public Date getTime() {
        return this.time;
    }

    /**
     * Set time.
     * @param time Time.
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Get header view.
     * @return Header view.
     */
    public DescriptionView getHeaderView() {
        return this.headerView;
    }

    /**
     * Setup header view.
     * @param hv Header view.
     */
    public void setHeaderView(DescriptionView hv) {
        this.headerView = hv;
        if (this.headerView != null) {
            this.headerView.add(this);
        }
    }

    /**
     * Get footer view.
     * @return Footer view.
     */
    public DescriptionView getFooterView() {
        return this.footerView;
    }

    /**
     * Setup footer view.
     * @param fv Footer view.
     */
    public void setFooterView(DescriptionView fv) {
        this.footerView = fv;
        if (this.footerView != null) {
            this.footerView.add(this);
        }
    }

    /**
     * Get paper.
     * @return Paper.
     */
    public Paper getPaper() {
        return this.paper;
    }

    /**
     * Get created pages.
     * @return Pages.
     */
    public List<PDPage> getPages() {
        return this.pages;
    }

    /**
     * Get top-left coordinates for drawing.
     * 
     * @return Top-left coordinates.
     */
    public Point getDrawingTopLeft() {
        return new Point(this.paper.getContentLeft(), getDrawingTop());
    }

    /**
     * Get width of the content
     * @return Width.
     */
    public int getWidth() {
        return this.paper.getContentSize().width;
    }

    public int getHeight() {
        return this.paper.getDrawingTop() - this.paper.getDrawingBottom();
    }

    /**
     * Draw bookmarks on the page.
     * 
     * @param page A page.
     * @param ois Outline items.
     * @return Page.
     * @throws IOException IO exception.
     */
    public abstract PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException;

    public PDFDoc getDoc() {
        return this.pdf;
    }

    @Override
    public String toString() {
    	return String.format("content:(%s,%s)", getWidth(), getHeight());
    }

    protected int getDrawingTop() {
		return this.paper.getDrawingTop();
    }

    protected int getDrawingBottom() {
		return this.paper.getDrawingBottom();
    }
}
