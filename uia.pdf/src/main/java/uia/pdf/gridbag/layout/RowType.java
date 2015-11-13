//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2015.11.13 於 12:31:05 PM CST 
//


package uia.pdf.gridbag.layout;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RowType complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="RowType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="textCell" type="{http://gridbag.pdf.uia/layout}TextCellType"/>
 *           &lt;element name="bindCell" type="{http://gridbag.pdf.uia/layout}BindCellType"/>
 *           &lt;element name="layoutCell" type="{http://gridbag.pdf.uia/layout}LayoutCellType"/>
 *           &lt;element name="bindLayoutCell" type="{http://gridbag.pdf.uia/layout}BindLayoutCellType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="borderSize" type="{http://www.w3.org/2001/XMLSchema}float" default="0.5" />
 *       &lt;attribute name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType", propOrder = {
    "textCellOrBindCellOrLayoutCell"
})
public class RowType {

    @XmlElements({
        @XmlElement(name = "textCell", type = TextCellType.class),
        @XmlElement(name = "bindCell", type = BindCellType.class),
        @XmlElement(name = "layoutCell", type = LayoutCellType.class),
        @XmlElement(name = "bindLayoutCell", type = BindLayoutCellType.class)
    })
    protected List<CellType> textCellOrBindCellOrLayoutCell;
    @XmlAttribute(name = "background")
    protected String background;
    @XmlAttribute(name = "height", required = true)
    protected String height;
    @XmlAttribute(name = "borderSize")
    protected Float borderSize;
    @XmlAttribute(name = "fontSize")
    protected Integer fontSize;

    /**
     * Gets the value of the textCellOrBindCellOrLayoutCell property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textCellOrBindCellOrLayoutCell property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextCellOrBindCellOrLayoutCell().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextCellType }
     * {@link BindCellType }
     * {@link LayoutCellType }
     * {@link BindLayoutCellType }
     * 
     * 
     */
    public List<CellType> getTextCellOrBindCellOrLayoutCell() {
        if (textCellOrBindCellOrLayoutCell == null) {
            textCellOrBindCellOrLayoutCell = new ArrayList<CellType>();
        }
        return this.textCellOrBindCellOrLayoutCell;
    }

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
     * 取得 height 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeight() {
        return height;
    }

    /**
     * 設定 height 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeight(String value) {
        this.height = value;
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
