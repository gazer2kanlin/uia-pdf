package uia.pdf;

import javax.xml.bind.JAXBException;

public class Layout {

	public static JaxbTool<uia.pdf.grid.layout.LayoutType> GRID_TYPE;

	public static JaxbTool<uia.pdf.gridbag.layout.LayoutType> GRIDBAG_TYPE;
	
	static {
		try {
			GRID_TYPE = new JaxbTool<uia.pdf.grid.layout.LayoutType>(
					uia.pdf.grid.layout.LayoutType.class,  
					"layout", 
					"http://grid.pdf.uia/layout", 
					"uia.pdf.grid.layout"); 
			
			GRIDBAG_TYPE = new JaxbTool<uia.pdf.gridbag.layout.LayoutType>(
					uia.pdf.gridbag.layout.LayoutType.class, 
					"layout", 
					"http://gridbag.pdf.uia/layout", 
					"uia.pdf.gridbag.layout");
		
		} catch (JAXBException e) {


		}  

	}
}
