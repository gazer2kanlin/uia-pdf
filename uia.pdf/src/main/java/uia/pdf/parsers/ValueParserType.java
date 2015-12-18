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

/**
 * Value parser definition.
 *
 * @author Kan Lin
 *
 */
public class ValueParserType {

    private String className;

    private String parserName;

    private String argu;

    /**
     * Get class name.
     * @return Class name.
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Set class name.
     * @param className Class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Get parser name.
     * @return Parser name.
     */
    public String getParserName() {
        return this.parserName;
    }

    /**
     * Set parser name.
     * @param parserName Parser name.
     */
    public void setParserName(String parserName) {
        this.parserName = parserName;
    }

    /**
     * Get value of constructor argument.
     * @return value of constructor argument.
     */
    public String getConstructorArgu() {
        return this.argu;
    }

    /**
     * Set value of constructor argument.
     * @param argu value of constructor argument.
     */
    public void setConstructorArgu(String argu) {
        this.argu = argu;
    }

}
