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

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;

import uia.pdf.ContentView;
import uia.pdf.DescriptionView;
import uia.pdf.PDFDoc;

/**
 * GridBag style description view.
 * 
 * @author Kyle K. Lin
 *
 */
public class GridBagDescriptionView extends DescriptionView {
	
	private final GridBagModel model;

	/**
	 * Constructor.
	 * 
	 * @param pdf The PDF.
	 * @param model The layout model.
	 */
    public GridBagDescriptionView(PDFDoc pdf, GridBagModel model) {
    	super(pdf);
    	this.model = model;
    }

    @Override
    protected void drawOn(ContentView cv, PDPage page, Object value) throws IOException {
    	this.model.arrange(0, cv.getWidth(), cv.getHeight());
    	this.model.drawOn(cv, page, value);
    }
}
