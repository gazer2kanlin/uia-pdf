package uia.pdf.parsers;

public class BooleanParser implements ValueParser {

    @Override
    public String read(Object value) {
        return ((Boolean) value).booleanValue() ? "Y" : "N";
    }

}
