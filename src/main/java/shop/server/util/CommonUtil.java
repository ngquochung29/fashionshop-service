package shop.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.http.HttpStatus;
import shop.server.exception.FashionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
public class CommonUtil {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isEmpty(List<?> collation) {
        return collation == null || collation.isEmpty();
    }

    public static String writeValue(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new FashionException(HttpStatus.INTERNAL_SERVER_ERROR,"Can't write object to JSON");
        }
    }

    public static <T> T readValue(Class<T> clazz, String json) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new FashionException(HttpStatus.INTERNAL_SERVER_ERROR,"Can't read object from JSON");
        }
    }

    public static <T> List<T> readList(String str, Class<T> type) {
        return readList(str, ArrayList.class, type);
    }

    public static <T> List<T> readList(String str, Class<? extends Collection> type, Class<T> elementType) {
        try {
            return mapper.readValue(str, mapper.getTypeFactory().constructCollectionType(type, elementType));
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    public static String random(){
        Random random = new Random();
        int number = random.nextInt(1_000_000); // 0 - 999999
        return String.format("%06d", number); // thêm 0 ở đầu nếu thiếu
    }

    public static String random4(){
        Random random = new Random();
        int number = random.nextInt(1_000_000); // 0 - 999999
        return String.format("%04d", number); // thêm 0 ở đầu nếu thiếu
    }
}
