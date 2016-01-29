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

    public ContentView(PDFMaker pdf, Paper paper) {
        this.pdf = pdf;
        this.paper = paper;
        this.pages = new ArrayList<PDPage>();
        this.time = new Date();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public DescriptionView getHeaderView() {
        return this.headerView;
    }

    public void setHeaderView(DescriptionView hv) {
        this.headerView = hv;
        if (this.headerView != null) {
            this.headerView.add(this);
        }
    }

    public DescriptionView getFooterView() {
        return this.footerView;
    }

    public void setFooterView(DescriptionView fv) {
        this.footerView = fv;
        if (this.footerView != null) {
            this.footerView.add(this);
        }
    }

    public PDFMaker getDoc() {
        return this.pdf;
    }

    public Paper getPaper() {
        return this.paper;
    }

    public List<PDPage> getPages() {
        return this.pages;
    }

    public int getLeft() {
        return this.paper.getLeft();
    }

    public int getRight() {
        return this.paper.getRight();
    }

    public Point getTopLeft() {
        return new Point(this.paper.getLeft(), getTop());
    }

    public int getWidth() {
        return this.paper.getDrawableSize().width;
    }

    public int getHeight() {
        return getTop() - getBottom();
    }

    public abstract PDPage drawBookmarks(PDPage page, List<PDOutlineItem> ois) throws IOException;

    protected int getTop() {
        return this.headerView == null ? this.paper.getTop() : this.paper.getTop() - this.headerView.getHeight();
    }

    protected int getBottom() {
        return this.footerView == null ? this.paper.getBottom() : this.paper.getBottom() + this.footerView.getHeight();
    }
}
