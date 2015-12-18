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

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Description view. Use to present header & footer.
 *
 * @author Kan Lin
 *
 */
public abstract class DescriptionView {

    private final ArrayList<ContentView> cvs;

    /**
     * Constructor.
     */
    public DescriptionView() {
        this.cvs = new ArrayList<ContentView>();
    }

    /**
     * Draw this view.
     * @throws IOException
     */
    public void draw() throws IOException {
        for (ContentView view : this.cvs) {
            for (PDPage page : view.getPages()) {
                draw(view, page);
            }
        }
    }

    /**
     * Get index of specific page.
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

    /**
     * Get height of this view.
     * @return Height.
     */
    public abstract int getHeight();

    /**
     * Get page count this view handles.
     * @return Count.
     */
    protected int getPageCount() {
        int pc = 0;
        for (ContentView cv : this.cvs) {
            pc += cv.getPages().size();
        }
        return pc;
    }

    protected abstract void draw(ContentView cv, PDPage page) throws IOException;

    void add(ContentView view) {
        if (!this.cvs.contains(view)) {
            this.cvs.add(view);
        }
    }
}
