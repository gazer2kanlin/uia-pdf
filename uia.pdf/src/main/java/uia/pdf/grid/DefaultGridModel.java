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

    private final ColumnModel[] columnModels;
	
    private int fontSize;

    private int headerHeight;

    private boolean headerVisible;

    private CellRenderer renderer;

    /**
     * Constructor.
     * @param columnModels Column models.
     */
    public DefaultGridModel(ColumnModel[] columnModels, int fontSize, int headerHeight, boolean headerVisible) {
    	this(columnModels, fontSize, headerHeight, headerVisible, new DefaultCellRenderer());
    }

    /**
     * Constructor.
     * @param columnModels Column models.
     * @param renderer Cell renderer.
     */
    public DefaultGridModel(ColumnModel[] columnModels, int fontSize, int headerHeight, boolean headerVisible, CellRenderer renderer) {
        this.columnModels = columnModels;
        this.fontSize = fontSize;
        this.headerHeight = headerHeight;
        this.headerVisible = headerVisible;
        this.renderer = renderer;
    }

    @Override
    public ColumnModel[] getColumnModels() {
        return this.columnModels;
    }

    @Override
    public int getFontSize() {
        return this.fontSize;
    }
    
    @Override
    public int getHeaderHeight() {
    	return this.headerHeight;
    }

    @Override
    public boolean isHeaderVisible() {
        return this.headerVisible;
    }

    @Override
    public CellRenderer getCellRenderer(int row, int column) {
        ColumnModel model = this.columnModels[column];
        return model.getCellRenderer() != null ? model.getCellRenderer() : this.renderer;
    }
}
