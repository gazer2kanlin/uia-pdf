package uia.pdf.parsers;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ValueParserFactory {

    private final HashMap<Class<?>, ValueParser> parsers;

    public ValueParserFactory() {
        this.parsers = new HashMap<Class<?>, ValueParser>();
        this.parsers.put(String.class, new CommonParser());
        this.parsers.put(Byte.class, new CommonParser());
        this.parsers.put(Short.class, new CommonParser());
        this.parsers.put(Integer.class, new CommonParser());
        this.parsers.put(Long.class, new CommonParser());
        this.parsers.put(Boolean.class, new BooleanParser());
        this.parsers.put(Date.class, new DateParser("yyyy/MM/dd HH:mm"));
    }

    public void register(Class<?> cls, ValueParser parser) {
        this.parsers.put(cls, parser);
    }

    public void register(ValueType vt) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
        Class<?> cls = Class.forName(vt.getClassName());
        if (vt.getArgs() == null) {
            ValueParser parser = (ValueParser) Class.forName(vt.getParserName()).newInstance();
            this.parsers.put(cls, parser);
        }
        else {
            ValueParser parser = (ValueParser) Class.forName(vt.getParserName()).getConstructor(String.class).newInstance(vt.getArgs());
            this.parsers.put(cls, parser);
        }
    }

    public void register(List<ValueType> types) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        for (ValueType vt : types) {
            register(vt);
        }
    }

    public ValueParser getParser(Object value) {
        ValueParser parser = this.parsers.get(value.getClass());
        return parser == null ? new CommonParser() : parser;
    }

    public String read(Object value) {
        return value == null ? "" : getParser(value).read(value);
    }
}
