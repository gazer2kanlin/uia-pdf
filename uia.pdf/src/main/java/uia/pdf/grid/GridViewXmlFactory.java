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

import java.util.TreeMap;

import uia.pdf.PDFUtil;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.LayoutType;
import uia.pdf.grid.layout.GridType.Columns;
import uia.pdf.papers.Paper;

public class GridViewXmlFactory extends GridViewFactory {

    private TreeMap<String, GridType> grids;
    
    GridViewXmlFactory(LayoutType layoutType) {
        this.grids = new TreeMap<String, GridType>();
        for (GridType gt : layoutType.getGrid()) {
            this.grids.put(gt.getName(), gt);
        }
    }

    @Override
    public GridModel createModel(String gridName, Paper paper) {
        return createModel(gridName, paper.getContentSize().width);
    }

    @Override
    public GridModel createModel(String gridName, int width) {
        return createModel(this.grids.get(gridName), width);
    }

    private GridModel createModel(GridType gt, int width) {
        if (gt == null) {
            return null;
        }

        int x0 = 0;
        ColumnModel[] cms = new ColumnModel[gt.getColumns().getColumn().size()];
        int c = 0;
        Columns columns = gt.getColumns();
        for (ColumnType ct : columns.getColumn()) {
            int w = PDFUtil.sizing(ct.getWidth(), width, x0);
            AlignmentType at = AlignmentType.CENTER;
            if ("NEAR".equalsIgnoreCase(ct.getAlignment())) {
                at = AlignmentType.NEAR;
            }
            else if ("FAR".equalsIgnoreCase(ct.getAlignment())) {
                at = AlignmentType.FAR;
            }
            ColumnModel cm = new ColumnModel(ct.getBind(), ct.getText(), w, at);
            cm.setWrap(ct.isWrap());
            cm.setBackground(PDFUtil.toColor(ct.getBackground()));
            cm.setFontStyle(ct.getFontStyle().toUpperCase());
            if (ct.getCellRenderer() != null) {
                try {
                    cm.setCellRenderer((CellRenderer) Class.forName(ct.getCellRenderer()).newInstance());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cms[c++] = cm;
            x0 += w;
        }

        DefaultGridModel model = new DefaultGridModel(cms, gt.getFontSize());
        model.setHeaderVisible(columns.isVisible());

        return model;
    }
}
