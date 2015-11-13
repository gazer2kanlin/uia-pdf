package uia.pdf;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPage;

import uia.pdf.papers.Paper;
import uia.pdf.parsers.ValueParserFactory;

/**
 * Content view.
 *
 * @author Kan Lin
 *
 */
public abstract class ContentView {

    private String name;

    private Date time;

    protected final PDFMaker pdf;

    protected final Paper paper;

    protected final ArrayList<PDPage> pages;

    protected DescriptionView hv;

    protected DescriptionView fv;

    protected ValueParserFactory factory;

    public ContentView(PDFMaker pdf, Paper paper) {
        this.pdf = pdf;
        this.paper = paper;
        this.pages = new ArrayList<PDPage>();
        this.time = new Date();
        this.factory = new ValueParserFactory();
    }

    public ValueParserFactory getValueParserFactory() {
        return this.factory;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public DescriptionView getHeaderView() {
        return this.hv;
    }

    public void setHeaderView(DescriptionView hv) {
        this.hv = hv;
        this.hv.add(this);
    }

    public DescriptionView getFooterView() {
        return this.fv;
    }

    public void setFooterView(DescriptionView fv) {
        this.fv = fv;
        this.fv.add(this);
    }

    public PDFMaker getDoc() {
        return this.pdf;
    }

    public Paper getPaper() {
        return this.paper;
    }

    public List<PDPage> getPages() {
        return this.pages;
    }

    protected int getTop() {
        return this.hv == null ? this.paper.getTop() : this.paper.getTop() - this.hv.getHeight();
    }

    protected int getBottom() {
        return this.fv == null ? this.paper.getBottom() : this.paper.getBottom() + this.fv.getHeight();
    }

    public int getLeft() {
        return this.paper.getLeft();
    }

    public int getRight() {
        return this.paper.getRight();
    }

    public Point getTopLeft() {
        return new Point(this.paper.getLeft(), getTop());
    }

    public int getWidth() {
        return this.paper.getViewSzie().width;
    }

    public int getHeight() {
        return getTop() - getBottom();
    }
}
