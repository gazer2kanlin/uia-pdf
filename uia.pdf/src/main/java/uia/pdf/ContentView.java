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

    protected final PDFMaker pdf;

    protected final Paper paper;

    protected final ArrayList<PDPage> pages;

    protected DescriptionView headerView;

    protected DescriptionView footerView;

    /**
     * Constructor.
     * @param pdf PDF maker.
     * @param paper paper.
     */
    public ContentView(PDFMaker pdf, Paper paper) {
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
     * Get PDF document.
     * @return PDF document.
     */
    public PDFMaker getDoc() {
        return this.pdf;
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
     * Get left  margin coordinate.
     * @return Left  margin coordinate.
     */
    public int getLeft() {
        return this.paper.getLeft();
    }

    /**
     * Get right margin coordinate.
     * @return Right margin coordinate.
     */
    public int getRight() {
        return this.paper.getRight();
    }

    /**
     * Get top-left margin coordinate.
     * @return Top-left coordinate.
     */
    public Point getTopLeft() {
        return new Point(this.paper.getLeft(), getTop());
    }

    /**
     * Get width of the content
     * @return Width.
     */
    public int getWidth() {
        return this.paper.getDrawableSize().width;
    }

    /**
     * Get height of the content.
     * @return Height.
     */
    public int getHeight() {
        return getTop() - getBottom();
    }

    /**
     * Draw bookmarks on page.
     * @param page Page.
     * @param ois Outline items.
     * @return Page.
     * @throws IOException IO exception.
     */
    public abstract PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException;

    protected int getTop() {
        return this.headerView == null ? this.paper.getTop() : this.paper.getTop() - this.headerView.getHeight();
    }

    protected int getBottom() {
        return this.footerView == null ? this.paper.getBottom() : this.paper.getBottom() + this.footerView.getHeight();
    }
}
