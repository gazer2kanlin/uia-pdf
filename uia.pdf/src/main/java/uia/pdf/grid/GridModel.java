package uia.pdf.grid;

/**
 * Grid model.
 *
 * @author Kan Lin
 *
 */
public interface GridModel {

    /**
     * Get column models.
     * @return Column models.
     */
    public ColumnModel[] getColumnModels();

    /**
     * Get cell renderer for specific row and column.
     * @param row Row index. Start with zero.
     * @param column Column index. Start with zero.
     * @return Cell renderer.
     */
    public CellRenderer getCellRenderer(int row, int column);
}
