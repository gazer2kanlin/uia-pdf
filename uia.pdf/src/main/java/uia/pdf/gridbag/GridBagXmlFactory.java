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

import java.util.ArrayList;
import java.util.TreeMap;

import uia.pdf.PDFDoc;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.LayoutType;
import uia.pdf.papers.Paper;

public class GridBagXmlFactory extends GridBagFactory{

    private final LayoutType layoutType;

    private final TreeMap<String, GridBagType> gbts;
    
    GridBagXmlFactory(LayoutType layoutType) {
        this.layoutType = layoutType;
        this.gbts = new TreeMap<String, GridBagType>();
        for (GridBagType gbt : this.layoutType.getGridbag()) {
            this.gbts.put(gbt.getName(), gbt);
        }
    }
    
    public GridBagView mainView(PDFDoc pdf, Paper paper) {
    	ArrayList<GridBagModel> models = new ArrayList<GridBagModel>();
    	for(GridBagType gbt : this.layoutType.getGridbag()) {
    		models.add(createModel(gbt));
    	}
    	return new GridBagView(pdf, paper, models);
    }
    
    public GridBagDescriptionView descView(PDFDoc pdf, String name) {
    	return new GridBagDescriptionView(
    			pdf, 
    			createModel(this.gbts.get(name)));
    }
}
