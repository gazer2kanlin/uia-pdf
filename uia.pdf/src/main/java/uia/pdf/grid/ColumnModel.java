package uia.pdf.grid;

/**
 * Column model.
 *
 * @author Kan Lin
 *
 */
public class ColumnModel {

    /**
     *
     * @author Kan Lin
     *
     */
    public static enum AlignmentType {
        /**
         *
         */
        NEAR,

        /**
         *
         */
        CENTER,

        /**
         *
         */
        FAR
    }

    private String id;

    private String displayName;

    private int width;

    private AlignmentType horizontalAlignment;

    /**
     * Constructor.
     * @param id Id.
     * @param displayName Name displayed at column header.
     * @param width Width.
     * @param horizontalAlignment Alignment.
     */
    public ColumnModel(String id, String displayName, int width, AlignmentType horizontalAlignment) {
        this.id = id;
        this.displayName = displayName;
        this.width = width;
        this.horizontalAlignment = horizontalAlignment;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public AlignmentType getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(AlignmentType horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

}
