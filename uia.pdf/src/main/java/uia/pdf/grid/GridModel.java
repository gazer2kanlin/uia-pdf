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
 * Grid model.
 *
 * @author Kan Lin
 *
 */
public interface GridModel {

    /**
     * Get column models.
     * @return Column models.
     */
    public ColumnModel[] getColumnModels();

    /**
     * Get font size.
     * @return
     */
    public int getFontSize();

    /**
     * Get height of the header.
     * 
     * @return Height.
     */
    public int getHeaderHeight();

    /**
     * Test if header is visible or not.
     * 
     * @return Visible or not.
     */
    public boolean isHeaderVisible();

    /**
     * Get cell renderer for specific row and column.
     * 
     * @param row Row index. Start with zero.
     * @param column Column index. Start with zero.
     * @return Cell renderer.
     */
    public CellRenderer getCellRenderer(int row, int column);
}
