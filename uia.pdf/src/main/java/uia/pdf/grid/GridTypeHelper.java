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

package uia.pdf.grid;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import uia.pdf.grid.layout.LayoutType;

public class GridTypeHelper {

    static Unmarshaller UNMARSHALLER;

    /**
     * Load TMD XML and convert to model.
     * @param file TMD XML file.
     * @return Model.
     * @throws Exception Load failure.
     */
    public static LayoutType load(File file) throws Exception {
        return load(new FileInputStream(file));
    }

    /**
     * Load TMD XML and convert to model.
     * @param stream TDM XML string stream.
     * @return Model.
     * @throws Exception Load failure.
     */
    public static LayoutType load(InputStream stream) throws Exception {
        if (UNMARSHALLER == null) {
            initial();
        }

        // Create the XMLReader
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = factory.newSAXParser().getXMLReader();

        // The filter class to set the correct namespace
        XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
        reader.setContentHandler(UNMARSHALLER.getUnmarshallerHandler());

        SAXSource source = new SAXSource(xmlFilter, new InputSource(stream));

        @SuppressWarnings("unchecked")
        JAXBElement<LayoutType> elem = (JAXBElement<LayoutType>) UNMARSHALLER.unmarshal(source);
        return elem.getValue();
    }

    /**
     * Load TMD XML and convert to model.
     * @param content TDM XML string content.
     * @return Model.
     * @throws Exception Load failure.
     */
    public static LayoutType load(String content) throws Exception {
        if (UNMARSHALLER == null) {
            initial();
        }

        // Create the XMLReader
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = factory.newSAXParser().getXMLReader();

        // The filter class to set the correct namespace
        XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
        reader.setContentHandler(UNMARSHALLER.getUnmarshallerHandler());

        InputStream inStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
        SAXSource source = new SAXSource(xmlFilter, new InputSource(inStream));

        @SuppressWarnings("unchecked")
        JAXBElement<LayoutType> elem = (JAXBElement<LayoutType>) UNMARSHALLER.unmarshal(source);
        return elem.getValue();
    }

    static void initial() throws Exception {
        try {
            JAXBContext jc = JAXBContext.newInstance("uia.pdf.grid.layout");
            UNMARSHALLER = jc.createUnmarshaller();
        }
        catch (JAXBException ex) {
            UNMARSHALLER = null;
            throw ex;
        }
    }

    static class XMLNamespaceFilter extends XMLFilterImpl {

        public XMLNamespaceFilter(XMLReader reader) {
            super(reader);
        }

        @Override
        public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
            super.startElement("http://grid.pdf.uia/layout", localName, qName, attributes);
        }
    }
}
