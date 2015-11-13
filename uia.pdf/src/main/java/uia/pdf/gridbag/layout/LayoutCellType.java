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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LayoutCellType complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="LayoutCellType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gridbag.pdf.uia/layout}CellType">
 *       &lt;sequence>
 *         &lt;element name="gridbag">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="grid" type="{http://gridbag.pdf.uia/layout}GridType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LayoutCellType", propOrder = {
    "gridbag"
})
@XmlSeeAlso({
    BindLayoutCellType.class
})
public class LayoutCellType
    extends CellType
{

    @XmlElement(required = true)
    protected LayoutCellType.Gridbag gridbag;

    /**
     * 取得 gridbag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link LayoutCellType.Gridbag }
     *     
     */
    public LayoutCellType.Gridbag getGridbag() {
        return gridbag;
    }

    /**
     * 設定 gridbag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutCellType.Gridbag }
     *     
     */
    public void setGridbag(LayoutCellType.Gridbag value) {
        this.gridbag = value;
    }


    /**
     * <p>anonymous complex type 的 Java 類別.
     * 
     * <p>下列綱要片段會指定此類別中包含的預期內容.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="grid" type="{http://gridbag.pdf.uia/layout}GridType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "grid"
    })
    public static class Gridbag {

        @XmlElement(required = true)
        protected List<GridType> grid;

        /**
         * Gets the value of the grid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the grid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGrid().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GridType }
         * 
         * 
         */
        public List<GridType> getGrid() {
            if (grid == null) {
                grid = new ArrayList<GridType>();
            }
            return this.grid;
        }

    }

}
