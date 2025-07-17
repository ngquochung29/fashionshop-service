package shop.server.util;

import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
public class CommonUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isEmpty(List<?> collation) {
        return collation == null || collation.isEmpty();
    }
}
