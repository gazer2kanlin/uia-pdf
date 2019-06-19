package uia.pdf.grid;

import java.awt.Point;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import uia.pdf.papers.Paper;

public class ImageDescCellRenderer implements CellRenderer {

    @Override
    public int paint(PDPageContentStream contentStream, Point topLeft, AbstractGridView view, ColumnModel cm, Object value, int row, int col) {
        PDImageXObject img = null;

        @SuppressWarnings("unchecked")
        Map<String, Object> obj = (Map<String, Object>) value;
        try {
            img = PDImageXObject.createFromFile(
                    (String) obj.get("imagePath"),
                    view.getDoc().getDocument());

            Paper paper = view.getPaper();
            double wp = (double) Math.max(img.getWidth(), cm.getWidth()) / paper.getContentSize().width;
            double hp = (double) img.getHeight() / paper.getContentSize().height;
            double pct = 1.0 / Math.max(wp, hp);
            int w = (int) (img.getWidth() * pct);
            int h = (int) (img.getHeight() * pct);

            contentStream.drawImage(img, topLeft.x, topLeft.y - h, w, h);

            DefaultCellRenderer textRenderer = new DefaultCellRenderer();
            h += textRenderer.paint(contentStream, new Point(topLeft.x, topLeft.y - h), view, cm, obj.get("imageText"), row, col);

            return h;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
