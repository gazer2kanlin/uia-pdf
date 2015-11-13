package uia.pdf.parsers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser implements ValueParser {

    private String pattern;

    private SimpleDateFormat sdf;

    public DateParser() {
        this.pattern = "yyyy-MM-dd HH:mm:ss";
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    public DateParser(String pattern) {
        this.pattern = pattern;
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        this.sdf = new SimpleDateFormat(this.pattern);
    }

    @Override
    public String read(Object value) {
        return this.sdf.format((Date) value);
    }

}
