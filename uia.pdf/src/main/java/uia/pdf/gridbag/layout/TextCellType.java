//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2015.11.13 於 12:31:05 PM CST 
//


package uia.pdf.gridbag.layout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>TextCellType complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="TextCellType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gridbag.pdf.uia/layout}CellType">
 *       &lt;sequence>
 *         &lt;element name="text">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,0" />
 *                 &lt;attribute name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="subtext">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,0" />
 *                 &lt;attribute name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="alignment" type="{http://www.w3.org/2001/XMLSchema}string" default="CENTER" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextCellType", propOrder = {
    "text",
    "subtext"
})
public class TextCellType
    extends CellType
{

    @XmlElement(required = true)
    protected TextCellType.Text text;
    @XmlElement(required = true)
    protected TextCellType.Subtext subtext;
    @XmlAttribute(name = "alignment")
    protected String alignment;

    /**
     * 取得 text 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link TextCellType.Text }
     *     
     */
    public TextCellType.Text getText() {
        return text;
    }

    /**
     * 設定 text 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link TextCellType.Text }
     *     
     */
    public void setText(TextCellType.Text value) {
        this.text = value;
    }

    /**
     * 取得 subtext 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link TextCellType.Subtext }
     *     
     */
    public TextCellType.Subtext getSubtext() {
        return subtext;
    }

    /**
     * 設定 subtext 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link TextCellType.Subtext }
     *     
     */
    public void setSubtext(TextCellType.Subtext value) {
        this.subtext = value;
    }

    /**
     * 取得 alignment 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlignment() {
        if (alignment == null) {
            return "CENTER";
        } else {
            return alignment;
        }
    }

    /**
     * 設定 alignment 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlignment(String value) {
        this.alignment = value;
    }


    /**
     * <p>anonymous complex type 的 Java 類別.
     * 
     * <p>下列綱要片段會指定此類別中包含的預期內容.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,0" />
     *       &lt;attribute name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Subtext {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "foreground")
        protected String foreground;
        @XmlAttribute(name = "fontSize")
        protected Integer fontSize;

        /**
         * 取得 value 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * 設定 value 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 取得 foreground 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getForeground() {
            if (foreground == null) {
                return "0,0,0";
            } else {
                return foreground;
            }
        }

        /**
         * 設定 foreground 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setForeground(String value) {
            this.foreground = value;
        }

        /**
         * 取得 fontSize 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getFontSize() {
            return fontSize;
        }

        /**
         * 設定 fontSize 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setFontSize(Integer value) {
            this.fontSize = value;
        }

    }


    /**
     * <p>anonymous complex type 的 Java 類別.
     * 
     * <p>下列綱要片段會指定此類別中包含的預期內容.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,0" />
     *       &lt;attribute name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Text {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "foreground")
        protected String foreground;
        @XmlAttribute(name = "fontSize")
        protected Integer fontSize;

        /**
         * 取得 value 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * 設定 value 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 取得 foreground 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getForeground() {
            if (foreground == null) {
                return "0,0,0";
            } else {
                return foreground;
            }
        }

        /**
         * 設定 foreground 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setForeground(String value) {
            this.foreground = value;
        }

        /**
         * 取得 fontSize 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getFontSize() {
            return fontSize;
        }

        /**
         * 設定 fontSize 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setFontSize(Integer value) {
            this.fontSize = value;
        }

    }

}
