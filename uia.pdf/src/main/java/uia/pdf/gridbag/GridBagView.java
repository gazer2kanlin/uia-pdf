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

package uia.pdf.gridbag;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;

import uia.pdf.ContentView;
import uia.pdf.PDFMaker;
import uia.pdf.papers.Paper;

/**
 *
 * @author Kan Lin
 *
 */
public class GridBagView extends ContentView {

    private GridBagDrawer drawer;

    private String bookmarkGroup;

    /**
     *
     * @param pdf
     * @param paper
     * @param layoutFile
     * @throws Exception
     */
    public GridBagView(PDFMaker pdf, Paper paper, File layoutFile) throws Exception {
        super(pdf, paper);
        this.drawer = new GridBagDrawer(layoutFile);
    }

    public void registerBindIdCellRenderer(String id, GridBagCellRenderer renderer) {
        this.drawer.registerBindIdCellRenderer(id, renderer);
    }

    public void registerBindClassCellRenderer(Class<?> cls, GridBagCellRenderer renderer) {
        this.drawer.registerBindClassCellRenderer(cls, renderer);
    }

    @Override
    public void beginBookmarkGroup() {
        this.pdf.beginBookmarkGroup();
    }

    @Override
    public void beginBookmarkGroup(String bookmarkGroup) {
        this.bookmarkGroup = bookmarkGroup;
    }

    @Override
    public void endBookmarkGroup() {
        this.bookmarkGroup = null;
        this.pdf.endBookmarkGroup();
    }

    /**
     *
     * @param gridsData
     * @param bookmark
     * @return
     * @throws IOException
     */
    public PDPage addPageEx(Map<String, Map<String, Object>> gridsData, String bookmark) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        if (this.bookmarkGroup != null) {
            this.pdf.addBookmark(page, this.bookmarkGroup);
            this.bookmarkGroup = null;
            this.pdf.beginBookmarkGroup();
        }
        this.pdf.addBookmark(page, bookmark);
        return addPageEx(page, gridsData);
    }

    /**
     *
     * @param gridsData
     * @return
     * @throws IOException
     */
    public PDPage addPageEx(Map<String, Map<String, Object>> gridsData) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        return addPageEx(page, gridsData);
    }

    /**
     *
     * @param data
     * @param bookmark
     * @return
     * @throws IOException
     */
    public PDPage addPage(Map<String, Object> data, String bookmark) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        if (this.bookmarkGroup != null) {
            this.pdf.addBookmark(page, this.bookmarkGroup);
            this.bookmarkGroup = null;
            this.pdf.beginBookmarkGroup();
        }
        this.pdf.addBookmark(page, bookmark);
        return addPage(page, data);
    }

    /**
     *
     * @param data
     * @return
     * @throws IOException
     */
    public PDPage addPage(Map<String, Object> data) throws IOException {
        PDPage page = this.paper.createPage();
        this.pdf.getDocument().addPage(page);
        this.pages.add(page);
        return addPage(page, data);
    }

    private PDPage addPageEx(PDPage page, Map<String, Map<String, Object>> gridsData) throws IOException {
        this.drawer.drawEx(this, page, gridsData);
        return page;
    }

    private PDPage addPage(PDPage page, Map<String, Object> data) throws IOException {
        this.drawer.draw(this, page, data);
        return page;
    }

    GridBagCellRenderer getBindRenderer(String id, Object value) {
        return this.drawer.getBindRenderer(id, value);
    }
}
