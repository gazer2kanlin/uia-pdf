package uia.pdf;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Description view.
 *
 * @author Kan Lin
 *
 */
public abstract class DescriptionView {

    private final ArrayList<ContentView> cvs;

    public DescriptionView() {
        this.cvs = new ArrayList<ContentView>();
    }

    public void draw() throws IOException {
        for (ContentView view : this.cvs) {
            for (PDPage page : view.getPages()) {
                draw(view, page);
            }
        }
    }

    public abstract int getHeight();

    protected int getPageCount() {
        int pc = 0;
        for (ContentView cv : this.cvs) {
            pc += cv.getPages().size();
        }
        return pc;
    }

    public int getPageIndex(PDPage page) {
        int pi = 0;
        for (ContentView cv : this.cvs) {
            int idx = cv.getPages().indexOf(page);
            if (idx >= 0) {
                pi += (idx + 1);
                return pi;
            }
            else {
                pi += cv.getPages().size();
            }
        }
        return 0;
    }

    protected abstract void draw(ContentView cv, PDPage page) throws IOException;

    void add(ContentView view) {
        if (!this.cvs.contains(view)) {
            this.cvs.add(view);
        }
    }
}
