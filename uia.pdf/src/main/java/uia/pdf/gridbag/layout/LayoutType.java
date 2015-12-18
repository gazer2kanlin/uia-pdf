
package uia.pdf.gridbag.layout;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LayoutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LayoutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gridbag" type="{http://gridbag.pdf.uia/layout}GridBagType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LayoutType", propOrder = {
    "gridbag"
})
public class LayoutType {

    @XmlElement(required = true)
    protected List<GridBagType> gridbag;

    /**
     * Gets the value of the gridbag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gridbag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGridbag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GridBagType }
     * 
     * 
     */
    public List<GridBagType> getGridbag() {
        if (gridbag == null) {
            gridbag = new ArrayList<GridBagType>();
        }
        return this.gridbag;
    }

}
