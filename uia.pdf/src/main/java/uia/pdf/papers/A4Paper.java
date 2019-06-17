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

package uia.pdf.papers;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * Paper of A4 size.
 *
 * @author Kan Lin
 *
 */
public class A4Paper extends Paper {

    public static final int WIDTH = (int) PDRectangle.A4.getWidth();

    public static final int HEIGTH = (int) PDRectangle.A4.getHeight();

    public static A4Paper landscape() {
        return new A4Paper(true);
    }
    
    public static A4Paper portrait() {
        return new A4Paper(false);
    }

    /**
     * Constructor.
     */
    public A4Paper(boolean landscape) {
        super(landscape ? new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()) : PDRectangle.A4);
    }
}
