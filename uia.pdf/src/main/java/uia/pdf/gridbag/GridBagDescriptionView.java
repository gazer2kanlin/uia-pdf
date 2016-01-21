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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.pdfbox.pdmodel.PDPage;

import uia.pdf.ContentView;
import uia.pdf.DescriptionView;
import uia.pdf.PDFException;

public class GridBagDescriptionView extends DescriptionView {

    private GridBagDrawer gbView;

    private Map<String, Object> data;

    private int height;

    public GridBagDescriptionView(File file, String name) throws PDFException {
        this(file, name, new TreeMap<String, Object>());
    }

    public GridBagDescriptionView(File file, String name, Map<String, Object> data) throws PDFException {
        try {
            this.gbView = new GridBagDrawer(file);
            this.data = data;
            this.height = Integer.parseInt(this.gbView.gbLayout.getGridBagType(name).getHeight());  // TODO: bug!!
        }
        catch (PDFException ex1) {
            throw ex1;
        }
        catch (Exception ex2) {
            throw new PDFException(ex2.getMessage(), ex2);
        }
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return this.height + 3;
    }

    @Override
    protected void draw(ContentView cv, PDPage page) throws IOException {
        this.gbView.draw(cv, page, this.data);
    }

}
