import com.google.gson.Gson;
import java.io.IOException;

public class Mapper {
    public static User[] stringToUsers(String json) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(json,User[].class);
    }
    public static User stringToUser(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,User.class);
    }
}
