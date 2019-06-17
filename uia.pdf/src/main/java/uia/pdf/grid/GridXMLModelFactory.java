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

import uia.pdf.Layout;
import uia.pdf.grid.layout.GridType;
import uia.pdf.grid.layout.LayoutType;
import uia.pdf.papers.Paper;

public class GridXMLModelFactory extends GridModelFactory {

    private TreeMap<String, GridType> grids;
    
    public GridXMLModelFactory() {
    	
    }

    public GridXMLModelFactory(File file) throws Exception {
        this(Layout.GRID_TYPE.fromXml(file));
    }

    public GridXMLModelFactory(LayoutType layoutType) {
        this.grids = new TreeMap<String, GridType>();
        for (GridType gt : layoutType.getGrid()) {
            this.grids.put(gt.getName(), gt);
        }
    }

    @Override
    public GridModel create(String gridName, Paper paper) {
        return create(gridName, paper.getDrawableSize().width);
    }

    @Override
    public GridModel create(String gridName, int width) {
        return create(this.grids.get(gridName), width);
    }
}
