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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CellType complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="CellType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rowspan" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="colspan" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="borderSize" type="{http://www.w3.org/2001/XMLSchema}float" default="0.5" />
 *       &lt;attribute name="borderColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CellType")
@XmlSeeAlso({
    TextCellType.class,
    BindCellType.class,
    LayoutCellType.class
})
public abstract class CellType {

    @XmlAttribute(name = "background")
    protected String background;
    @XmlAttribute(name = "rowspan")
    protected Integer rowspan;
    @XmlAttribute(name = "colspan")
    protected Integer colspan;
    @XmlAttribute(name = "borderSize")
    protected Float borderSize;
    @XmlAttribute(name = "borderColor")
    protected String borderColor;

    /**
     * 取得 background 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackground() {
        return background;
    }

    /**
     * 設定 background 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * 取得 rowspan 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRowspan() {
        if (rowspan == null) {
            return  1;
        } else {
            return rowspan;
        }
    }

    /**
     * 設定 rowspan 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRowspan(Integer value) {
        this.rowspan = value;
    }

    /**
     * 取得 colspan 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getColspan() {
        if (colspan == null) {
            return  1;
        } else {
            return colspan;
        }
    }

    /**
     * 設定 colspan 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setColspan(Integer value) {
        this.colspan = value;
    }

    /**
     * 取得 borderSize 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getBorderSize() {
        if (borderSize == null) {
            return  0.5F;
        } else {
            return borderSize;
        }
    }

    /**
     * 設定 borderSize 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBorderSize(Float value) {
        this.borderSize = value;
    }

    /**
     * 取得 borderColor 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * 設定 borderColor 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorderColor(String value) {
        this.borderColor = value;
    }

}
