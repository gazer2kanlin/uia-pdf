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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Value parser factory.
 *
 * @author Kan Lin
 *
 */
public class ValueParserFactory {

    private final HashMap<Class<?>, ValueParser> parsers;

    /**
     * Constructor.
     */
    public ValueParserFactory() {
        this.parsers = new HashMap<Class<?>, ValueParser>();
        this.parsers.put(String.class, new CommonParser());
        this.parsers.put(Byte.class, new CommonParser());
        this.parsers.put(Short.class, new CommonParser());
        this.parsers.put(Integer.class, new CommonParser());
        this.parsers.put(Long.class, new CommonParser());
        this.parsers.put(Boolean.class, new BooleanParser());
        this.parsers.put(Date.class, new DateParser("yyyy/MM/dd HH:mm"));
    }

    /**
     * Register parser.
     * @param cls Class type.
     * @param parser Parser for specific class.
     */
    public void register(Class<?> cls, ValueParser parser) {
        this.parsers.put(cls, parser);
    }

    /**
     * Register parser.
     * @param vpType Value parser type.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public void register(ValueParserType vpType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
        Class<?> cls = Class.forName(vpType.getClassName());
        if (vpType.getConstructorArgu() == null) {
            ValueParser parser = (ValueParser) Class.forName(vpType.getParserName()).newInstance();
            this.parsers.put(cls, parser);
        }
        else {
            ValueParser parser = (ValueParser) Class.forName(vpType.getParserName()).getConstructor(String.class).newInstance(vpType.getConstructorArgu());
            this.parsers.put(cls, parser);
        }
    }

    /**
     * Register parser.
     * @param vpTypes Value parser type.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public void register(List<ValueParserType> vpTypes) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        for (ValueParserType vpType : vpTypes) {
            register(vpType);
        }
    }

    /**
     * Get parser based on class type of specific value.
     * @param value Value.
     * @return Parser.
     */
    public ValueParser getParser(Object value) {
        ValueParser parser = this.parsers.get(value.getClass());
        return parser == null ? new CommonParser() : parser;
    }

    /**
     * Parse to string based on class type of value.
     * @param value Value.
     * @return String.
     */
    public String parse(Object value) {
        return value == null ? "" : getParser(value).read(value);
    }
}
