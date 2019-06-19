package uia.pdf;

import java.io.IOException;
import java.io.InputStream;

public class FontUtils {

	public static InputStream simplified() throws IOException {
		return FontUtils.class
				.getResource("fonts/simplified.ttf")
				.openStream();
	}

	public static InputStream traditional() throws IOException {
		return FontUtils.class
				.getResource("fonts/traditional.ttf")
				.openStream();
	}
}
