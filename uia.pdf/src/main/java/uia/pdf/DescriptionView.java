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

package uia.pdf;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Description view is used to present header & footer.
 *
 * @author Kan Lin
 *
 */
public abstract class DescriptionView {
	
	private final PDFDoc pdf;

    private final ArrayList<ContentView> cvs;

    protected DescriptionView(PDFDoc pdf) {
    	this.pdf = pdf;
        this.cvs = new ArrayList<ContentView>();
    }

    public void draw() throws Exception {
        for (ContentView view : this.cvs) {
            for (PDPage page : view.getPages()) {
                drawOn(view, page, null);
            }
        }
    }

    public void draw(Object value) throws Exception {
        for (ContentView view : this.cvs) {
            for (PDPage page : view.getPages()) {
                drawOn(view, page, value);
            }
        }
    }

    /**
     * Get index of specific page.
     * 
     * @param page The page.
     * @return Index.
     */
    public int getPageIndex(PDPage page) {
        int pi = 0;
        for (ContentView cv : this.cvs) {
            int idx = cv.getPages().indexOf(page);
            if (idx >= 0) {
                pi += (idx + 1);
                return pi;
            }
            else {
                pi += cv.getPages().size();
            }
        }
        return 0;
    }
    
    public PDFDoc getDoc() {
    	return this.pdf;
    }

    /**
     * Get page count this view handles.
     * 
     * @return Count.
     */
    protected int getPageCount() {
        int pc = 0;
        for (ContentView cv : this.cvs) {
            pc += cv.getPages().size();
        }
        return pc;
    }

    protected abstract void drawOn(ContentView cv, PDPage page, Object value) throws Exception;

    void add(ContentView view) {
        if (!this.cvs.contains(view)) {
            this.cvs.add(view);
        }
    }
}
