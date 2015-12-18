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

package uia.pdf.gridbag.model;

import java.awt.Color;

import uia.pdf.PDFUtil;
import uia.pdf.gridbag.layout.TextType;

/**
 * Text.
 *
 * @author Kan Lin
 *
 */
public class Text {

    public final String value;

    public final Color foreground;

    public final TextCell cell;

    private final TextType tt;

    Text(TextType tt, TextCell cell) {
        this.tt = tt;
        this.cell = cell;
        if (tt != null) {
            this.value = tt.getValue();
            this.foreground = PDFUtil.toColor(tt.getForeground());
        }
        else {
            this.value = null;
            this.foreground = Color.black;
        }
    }

    public int getFontSize() {
        if (this.tt == null) {
            return 9;
        }

        if (this.tt.getFontSize() != null) {
            return this.tt.getFontSize().intValue();
        }
        else {
            return this.cell.getFontSize();
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
