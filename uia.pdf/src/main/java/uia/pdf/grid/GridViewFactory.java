package uia.pdf.grid;

import java.io.File;

import uia.pdf.Layout;
import uia.pdf.PDFMaker;
import uia.pdf.papers.Paper;

public abstract class GridViewFactory {

	/**
	 * Create a factory from a XML file.
	 * @param filename The XML file.
	 * @return The model factory.
	 * @throws Exception Failed to handle the XML file.
	 */
	public static GridViewXmlFactory fromXml(String filename) throws Exception {
		return new GridViewXmlFactory(Layout.GRID_TYPE.fromXml(new File(filename)));
	}
    
    public GridView mainView(PDFMaker pdf, Paper paper, String gridName) {
    	return new GridView(pdf, paper.clone(), createModel(gridName, paper));
    }
    
    public HeaderDescriptionView headerView(PDFMaker pdf, Paper paper, String gridName, int height) {
    	return new HeaderDescriptionView(pdf, paper.clone(), createModel(gridName, paper), height);
    }
    
    public FooterDescriptionView footerView(PDFMaker pdf, Paper paper, String gridName, int height) {
    	return new FooterDescriptionView(pdf, paper.clone(), createModel(gridName, paper), height);
    }

    /**
     * Create a grid model.
     * @param gridName The grid name.
     * @param paper Paper.
     * @return Model.
     */
    public abstract GridModel createModel(String gridName, Paper paper);

    /**
     * Create a grid model.
     * @param gridName The grid name.
     * @param width Width of grid.
     * @return Model.
     */
    public abstract GridModel createModel(String gridName, int width);
}
