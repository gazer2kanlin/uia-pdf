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

import uia.pdf.PDFUtil;
import uia.pdf.grid.ColumnModel.AlignmentType;
import uia.pdf.grid.layout.ColumnType;
import uia.pdf.grid.layout.GridType;
import uia.pdf.papers.Paper;

public abstract class GridModelFactory {

    public abstract GridModel create(String gridName, Paper paper);

    public abstract GridModel create(String gridName, int width);

    protected GridModel create(GridType gt, int width) {
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
            cm.setFontStyle(ct.getFontStyle().toUpperCase());
            cms[c++] = cm;
            x0 += w;
        }

        DefaultGridModel model = new DefaultGridModel(cms, gt.getFontSize());
        model.setHeaderVisible(gt.getColumns().isVisible());

        return model;
    }
}
