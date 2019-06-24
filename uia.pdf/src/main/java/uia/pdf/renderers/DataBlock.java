package uia.pdf.renderers;

public class DataBlock {
	
	public static enum AlignmentType {
		NEAR,
		
		FAR,
		
		CENTER
	}

	public final int x;

	public final int y;

	public final int width;

	public final int height;
	
	public final int fontSize;
	
	public final AlignmentType alignment;
	
	public final AlignmentType valignment;
	
	public DataBlock(int x, int y, int width, int height, int fontSize, AlignmentType alignment, AlignmentType valignment) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.alignment = alignment;
		this.valignment = valignment;
	}
}
