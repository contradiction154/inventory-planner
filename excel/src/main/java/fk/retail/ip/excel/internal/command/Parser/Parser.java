package fk.retail.ip.excel.internal.command.Parser;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public interface Parser {
    default List<Map<String, Object>> parse(InputStream inputStream) {
        return null;
    }
}
