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

import java.io.File;
import java.util.TreeMap;

import uia.pdf.PDFUtil;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.LayoutType;
import uia.pdf.papers.Paper;

public class GridModelFactory {

    private TreeMap<String, GridType> grids;

    public GridModelFactory(File file) throws Exception {
        this(GridTypeHelper.load(file));
    }

    public GridModelFactory(LayoutType layoutType) {
        this.grids = new TreeMap<String, GridType>();
        for (GridType gt : layoutType.getGrid()) {
            this.grids.put(gt.getName(), gt);
        }
    }

    public GridModel create(String gridName, Paper paper) {
        return create(gridName, paper.getDrawableSize().width);
    }

    public GridModel create(String gridName, int width) {
        GridType gt = this.grids.get(gridName);
        if (gt == null) {
            return null;
        }

        int x0 = 0;
        ColumnModel[] cms = new ColumnModel[gt.getColumns().getColumn().size()];
        int c = 0;
        for (ColumnType ct : gt.getColumns().getColumn()) {
            int w = PDFUtil.calculateWidth(ct.getWidth(), width, x0);
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
            cms[c++] = cm;
            x0 += w;
        }

        return new DefaultGridModel(cms);
    }
}
