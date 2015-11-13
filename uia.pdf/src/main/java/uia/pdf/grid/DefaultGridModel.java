package uia.pdf.grid;

/**
 * Default implementation of grid model.
 *
 * @author Kan Lin
 *
 */
public class DefaultGridModel implements GridModel {

    private final ColumnModel[] columnModels;

    private CellRenderer renderer;

    /**
     * Constructor.
     * @param columnModels Column models.
     */
    public DefaultGridModel(ColumnModel[] columnModels) {
        this.columnModels = columnModels;
        this.renderer = new DefaultCellRenderer();
    }

    /**
     * Constructor.
     * @param columnModels Column models.
     * @param renderer Cell renderer.
     */
    public DefaultGridModel(ColumnModel[] columnModels, CellRenderer renderer) {
        this.columnModels = columnModels;
        this.renderer = renderer;
    }

    @Override
    public ColumnModel[] getColumnModels() {
        return this.columnModels;
    }

    @Override
    public CellRenderer getCellRenderer(int row, int column) {
        return this.renderer;
    }

}
