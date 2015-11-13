package uia.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

/**
 * PDF maker.
 *
 * @author Kan Lin
 *
 */
public class PDFMaker {

    public static File SIMPLIEFIED;

    public static File TRADITIONAL;

    private final PDDocument doc;

    private PDDocumentOutline docOutline;

    private PDOutlineItem pagesOI;

    private final PDFont font;

    static {
        try {
            SIMPLIEFIED = new File(PDFMaker.class.getResource("simplified.ttf").toURI());
            TRADITIONAL = new File(PDFMaker.class.getResource("traditional.ttf").toURI());
        }
        catch (Exception ex) {

        }
    }

    public PDFMaker(File fontFile) throws IOException {
        this.doc = new PDDocument();
        this.font = PDType0Font.load(this.doc, fontFile);

        this.docOutline = new PDDocumentOutline();
        this.doc.getDocumentCatalog().setDocumentOutline(this.docOutline);

        this.pagesOI = new PDOutlineItem();
        this.pagesOI.setTitle("All");
        this.docOutline.addLast(this.pagesOI);

    }

    public boolean save(File file) {
        try {
            this.docOutline.openNode();
            this.doc.save(file);
            this.doc.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public PDDocument getDocument() {
        return this.doc;
    }

    public PDOutlineItem addBookmark(PDPage page, String title) {
        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);
        PDOutlineItem bookmark = new PDOutlineItem();
        bookmark.setDestination(dest);
        bookmark.setTitle(title);
        this.pagesOI.addLast(bookmark);
        return bookmark;
    }

    public PDOutlineItem addBookmark(PDPage page, String title, PDOutlineItem parentBookmark) {
        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);
        PDOutlineItem bookmark = new PDOutlineItem();
        bookmark.setDestination(dest);
        bookmark.setTitle(title);
        parentBookmark.addLast(bookmark);
        return bookmark;
    }

    public PDFont getFont() {
        return this.font;
    }
}
