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

package uia.pdf.grid;

/**
 * Default implementation of grid model.
 *
 * @author Kan Lin
 *
 */
public class DefaultGridModel implements GridModel {

    private int fontSize;

    private final ColumnModel[] columnModels;

    private CellRenderer renderer;

    private boolean headerVisible;

    /**
     * Constructor.
     * @param columnModels Column models.
     */
    public DefaultGridModel(ColumnModel[] columnModels, int fontSize) {
        this.fontSize = fontSize;
        this.columnModels = columnModels;
        this.renderer = new DefaultCellRenderer();
        this.headerVisible = true;
    }

    /**
     * Constructor.
     * @param columnModels Column models.
     * @param renderer Cell renderer.
     */
    public DefaultGridModel(ColumnModel[] columnModels, CellRenderer renderer) {
        this.fontSize = 9;
        this.columnModels = columnModels;
        this.renderer = renderer;
        this.headerVisible = true;
    }

    @Override
    public int getFontSize() {
        return this.fontSize;
    }

    @Override
    public ColumnModel[] getColumnModels() {
        return this.columnModels;
    }

    @Override
    public CellRenderer getCellRenderer(int row, int column) {
        return this.renderer;
    }

    @Override
    public boolean isHeaderVisible() {
        return this.headerVisible;
    }

    public void setHeaderVisible(boolean headerVisible) {
        this.headerVisible = headerVisible;
    }

}
