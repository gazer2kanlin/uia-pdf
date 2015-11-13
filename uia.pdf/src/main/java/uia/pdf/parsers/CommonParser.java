package uia.pdf.parsers;

public class CommonParser implements ValueParser {

    @Override
    public String read(Object value) {
        return value.toString();
    }

}
