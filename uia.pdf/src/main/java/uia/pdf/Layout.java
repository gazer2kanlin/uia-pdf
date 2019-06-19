package uia.pdf;

import javax.xml.bind.JAXBException;

import uia.utils.JaxbUtils;

public class Layout {

	public static JaxbUtils<uia.pdf.grid.layout.LayoutType> GRID_TYPE;

	public static JaxbUtils<uia.pdf.gridbag.layout.LayoutType> GRIDBAG_TYPE;
	
	static {
		try {
			GRID_TYPE = new JaxbUtils<uia.pdf.grid.layout.LayoutType>(
					uia.pdf.grid.layout.LayoutType.class,  
					"layout", 
					"http://grid.pdf.uia/layout", 
					"uia.pdf.grid.layout"); 
			
			GRIDBAG_TYPE = new JaxbUtils<uia.pdf.gridbag.layout.LayoutType>(
					uia.pdf.gridbag.layout.LayoutType.class, 
					"layout", 
					"http://gridbag.pdf.uia/layout", 
					"uia.pdf.gridbag.layout");
		
		} catch (JAXBException e) {


		}  
	}
}
