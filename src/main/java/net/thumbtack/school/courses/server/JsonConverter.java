package net.thumbtack.school.courses.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;

public class JsonConverter {

    private static volatile JsonConverter jsonConverter;
    private final Gson gson;

    private JsonConverter(){
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public static JsonConverter getInstance(){
        JsonConverter result = jsonConverter;
        if(result != null) {
            return result;
        }
        synchronized (JsonConverter.class){
            if(jsonConverter == null){
                jsonConverter = new JsonConverter();
            }
            return jsonConverter;
        }
    }


    public <T> String serialize(T obj) {
        return gson.toJson(obj);
    }

    public <T> T getClassFromJson(String json, Class<T> tClass) throws ServerException {
        try {
            return gson.fromJson(json, tClass);
        }
        catch (JsonSyntaxException e){
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }

}
