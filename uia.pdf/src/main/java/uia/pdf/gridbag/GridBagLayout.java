package uia.pdf.gridbag;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import uia.pdf.PDFUtil;
import uia.pdf.gridbag.layout.BindCellType;
import uia.pdf.gridbag.layout.CellType;
import uia.pdf.gridbag.layout.ColumnType;
import uia.pdf.gridbag.layout.GridBagType;
import uia.pdf.gridbag.layout.GridType;
import uia.pdf.gridbag.layout.RowType;
import uia.pdf.gridbag.layout.TextCellType;

public class GridBagLayout {

    private GridBagType gbType;

    private ArrayList<Grid> grids;

    public GridBagLayout(GridBagType gbType) {
        this.grids = new ArrayList<Grid>();
        this.gbType = gbType;
    }

    public List<Grid> getGrids() {
        return this.grids;
    }

    /**
     * Convert coordinates to PdfBox system.
     * @param left Left point.
     * @param top Top point.
     */
    public void convertCoordinates(int left, int top) {
    }

    public void load(int width, int height) {
        this.grids.clear();
        for (GridType gt : this.gbType.getLayout().getGrid()) {
            Grid grid = new Grid(gt.getColumns().getColumn().size(), gt.getRow().size());
            grid.x = calculate(gt.getX(), width, 0);
            grid.y = calculate(gt.getY(), height, 0);
            grid.width = calculate(gt.getWidth(), width, grid.x);
            grid.height = calculate(gt.getHeight(), height, grid.y);
            grid.borderEnabled = gt.isBorderEnabled();
            grid.borderSize = gt.getBorderSize();
            grid.borderColor = PDFUtil.toColor(gt.getBorderColor());
            this.grids.add(grid);

            int colx = grid.x;
            int coli = 0;
            for (ColumnType ct : gt.getColumns().getColumn()) {
                Column col = new Column();
                col.grid = grid;
                col.index = coli;
                col.x = coli == 0 ? colx : grid.columns[coli - 1].x + grid.columns[coli - 1].width;
                col.width = calculate(ct.getWidth(), grid.width, colx - grid.x);
                if (ct.getBackground() != null) {
                    col.background = PDFUtil.toColor(ct.getBackground());
                }
                grid.columns[coli] = col;

                colx += col.width;
                coli++;
            }

            int ri = 0;
            int rowy = grid.y;
            for (RowType rt : gt.getRow()) {
                Row row = new Row();
                row.grid = grid;
                row.index = ri;
                row.y = rowy;
                row.height = calculate(rt.getHeight(), grid.height, rowy - grid.y);
                if (rt.getBackground() != null) {
                    row.background = PDFUtil.toColor(rt.getBackground());
                }
                grid.rows[ri] = row;

                int celli = 0;
                for (CellType ct : rt.getTextCellOrBindCellOrLayoutCell()) {
                    while (grid.cells[ri][celli] != null) {
                        celli++;
                    }

                    Color background = null;
                    if (ct.getBackground() != null) {
                        background = PDFUtil.toColor(ct.getBackground());
                    }

                    Cell cell = null;
                    if (ct instanceof TextCellType) {
                        TextCellType tc = ((TextCellType) ct);

                        Text text;
                        if (tc.getText() == null) {
                            text = new Text();
                        }
                        else {
                            int tfs = tc.getText().getFontSize() != null ? tc.getText().getFontSize() : rt.getFontSize() != null ? rt.getFontSize() : gt.getFontSize();
                            text = new Text(tc.getText().getValue(), tc.getText().getForeground(), tfs);
                        }

                        Text subText;
                        if (tc.getSubtext() == null) {
                            subText = new Text();
                        }
                        else {
                            int tfs = tc.getSubtext().getFontSize() != null ? tc.getSubtext().getFontSize() : rt.getFontSize() != null ? rt.getFontSize() - 2 : gt.getFontSize() - 2;
                            subText = new Text(tc.getSubtext().getValue(), tc.getSubtext().getForeground(), tfs);
                        }

                        cell = new TextCell(
                                grid,
                                ri,
                                celli,
                                ct.getRowspan(),
                                ct.getColspan(),
                                background,
                                ct.getBorderSize(),
                                PDFUtil.toColor(ct.getBorderColor()),
                                tc.getAlignment(),
                                text,
                                subText);
                    }
                    else if (ct instanceof BindCellType) {
                        BindCellType bc = (BindCellType) ct;
                        cell = new BindCell(
                                grid,
                                ri,
                                celli,
                                ct.getRowspan(),
                                ct.getColspan(),
                                background,
                                ct.getBorderSize(),
                                PDFUtil.toColor(ct.getBorderColor()),
                                bc.getId());
                    }
                    else {
                        cell = new TextCell(
                                grid,
                                ri,
                                celli,
                                ct.getRowspan(),
                                ct.getColspan(),
                                background,
                                ct.getBorderSize(),
                                PDFUtil.toColor(ct.getBorderColor()),
                                "NEAR",
                                new Text(),
                                new Text());
                    }

                    for (int cs = 0; cs < cell.colspan; cs++) {
                        for (int rs = 0; rs < cell.rowspan; rs++) {
                            grid.cells[ri + rs][celli] = cell;
                        }
                        grid.cells[ri][celli] = cell;
                        celli++;
                    }

                }

                rowy += row.height;
                ri++;
            }
        }
    }

    private int calculate(String value, int total, int offset) {
        if (value.endsWith("%")) {
            if (value.startsWith("+")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return (total - offset) * p / 100;
            }
            else if (value.startsWith("*")) {
                int p = Integer.parseInt(value.substring(1, value.length() - 1));
                return total * p / 100 - offset;
            }
            else {
                int p = Integer.parseInt(value.substring(0, value.length() - 1));
                return total * p / 100;
            }
        }
        else {
            return Integer.parseInt(value);
        }
    }
}
