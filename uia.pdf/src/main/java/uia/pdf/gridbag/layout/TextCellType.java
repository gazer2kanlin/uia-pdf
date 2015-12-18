
package uia.pdf.gridbag.layout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TextCellType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TextCellType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gridbag.pdf.uia/layout}CellType">
 *       &lt;sequence>
 *         &lt;element name="text" type="{http://gridbag.pdf.uia/layout}TextType"/>
 *         &lt;element name="subtext" type="{http://gridbag.pdf.uia/layout}TextType"/>
 *       &lt;/sequence>
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
    protected TextType text;
    @XmlElement(required = true)
    protected TextType subtext;

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setText(TextType value) {
        this.text = value;
    }

    /**
     * Gets the value of the subtext property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSubtext() {
        return subtext;
    }

    /**
     * Sets the value of the subtext property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSubtext(TextType value) {
        this.subtext = value;
    }

}
