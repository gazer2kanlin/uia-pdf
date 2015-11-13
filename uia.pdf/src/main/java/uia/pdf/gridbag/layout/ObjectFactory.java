//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2015.11.13 於 12:31:05 PM CST 
//


package uia.pdf.gridbag.layout;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the uia.pdf.gridbag.layout package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Gridbag_QNAME = new QName("http://gridbag.pdf.uia/layout", "gridbag");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uia.pdf.gridbag.layout
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GridType }
     * 
     */
    public GridType createGridType() {
        return new GridType();
    }

    /**
     * Create an instance of {@link LayoutCellType }
     * 
     */
    public LayoutCellType createLayoutCellType() {
        return new LayoutCellType();
    }

    /**
     * Create an instance of {@link TextCellType }
     * 
     */
    public TextCellType createTextCellType() {
        return new TextCellType();
    }

    /**
     * Create an instance of {@link GridBagType }
     * 
     */
    public GridBagType createGridBagType() {
        return new GridBagType();
    }

    /**
     * Create an instance of {@link ColumnType }
     * 
     */
    public ColumnType createColumnType() {
        return new ColumnType();
    }

    /**
     * Create an instance of {@link BindCellType }
     * 
     */
    public BindCellType createBindCellType() {
        return new BindCellType();
    }

    /**
     * Create an instance of {@link BindLayoutCellType }
     * 
     */
    public BindLayoutCellType createBindLayoutCellType() {
        return new BindLayoutCellType();
    }

    /**
     * Create an instance of {@link RowType }
     * 
     */
    public RowType createRowType() {
        return new RowType();
    }

    /**
     * Create an instance of {@link GridType.Columns }
     * 
     */
    public GridType.Columns createGridTypeColumns() {
        return new GridType.Columns();
    }

    /**
     * Create an instance of {@link LayoutCellType.Gridbag }
     * 
     */
    public LayoutCellType.Gridbag createLayoutCellTypeGridbag() {
        return new LayoutCellType.Gridbag();
    }

    /**
     * Create an instance of {@link TextCellType.Text }
     * 
     */
    public TextCellType.Text createTextCellTypeText() {
        return new TextCellType.Text();
    }

    /**
     * Create an instance of {@link TextCellType.Subtext }
     * 
     */
    public TextCellType.Subtext createTextCellTypeSubtext() {
        return new TextCellType.Subtext();
    }

    /**
     * Create an instance of {@link GridBagType.Layout }
     * 
     */
    public GridBagType.Layout createGridBagTypeLayout() {
        return new GridBagType.Layout();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GridBagType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gridbag.pdf.uia/layout", name = "gridbag")
    public JAXBElement<GridBagType> createGridbag(GridBagType value) {
        return new JAXBElement<GridBagType>(_Gridbag_QNAME, GridBagType.class, null, value);
    }

}
