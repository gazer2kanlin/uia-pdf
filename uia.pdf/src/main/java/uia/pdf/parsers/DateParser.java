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

package uia.pdf.parsers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date parser. Default format pattern is "yyyy-MM-dd HH:mm:ss".
 *
 * @author Kan Lin
 *
 */
public class DateParser implements ValueParser {

    private String pattern;

    private SimpleDateFormat sdf;

    /**
     * Constructor.
     */
    public DateParser() {
        this.pattern = "yyyy-MM-dd HH:mm:ss";
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    /**
     * Constructor.
     * @return pattern Format pattern.
     */
    public DateParser(String pattern) {
        this.pattern = pattern;
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    /**
     * Get pattern.
     * @return Pattern.
     */
    public String getPattern() {
        return this.pattern;
    }

    /**
     * Set pattern.
     * @param pattern Pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    @Override
    public String read(Object value) {
        return this.sdf.format((Date) value);
    }

}
