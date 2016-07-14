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

import java.awt.Color;

/**
 * Column model.
 *
 * @author Kan Lin
 *
 */
public class ColumnModel {

    /**
     *
     * @author Kan Lin
     *
     */
    public static enum AlignmentType {
        NEAR,

        CENTER,

        FAR
    }

    private String id;

    private String displayName;

    private int width;

    private AlignmentType horizontalAlignment;

    private Color background;

    private boolean wrap;

    private String fontStyle;

    private CellRenderer cellRenderer;

    /**
     * Constructor.
     * @param id Id.
     * @param displayName Name displayed at column header.
     * @param width Width.
     * @param horizontalAlignment Alignment.
     */
    public ColumnModel(String id, String displayName, int width, AlignmentType horizontalAlignment) {
        this.id = id;
        this.displayName = displayName;
        this.width = width;
        this.horizontalAlignment = horizontalAlignment;
        this.wrap = false;
        this.fontStyle = "PLAIN";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public AlignmentType getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(AlignmentType horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public Color getBackground() {
        return this.background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public boolean isWrap() {
        return this.wrap;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public String getFontStyle() {
        return this.fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public boolean isBold() {
        return "BOLD".equalsIgnoreCase(this.fontStyle);
    }

    public CellRenderer getCellRenderer() {
        return this.cellRenderer;
    }

    public void setCellRenderer(CellRenderer cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    @Override
    public ColumnModel clone() {
        ColumnModel other = new ColumnModel(this.id, this.displayName, this.width, this.horizontalAlignment);
        other.background = this.background;
        other.fontStyle = this.fontStyle;
        other.wrap = this.wrap;
        other.cellRenderer = this.cellRenderer;
        return other;
    }
}
