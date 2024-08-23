package cn.goindog.amsm.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserDetailUtils {
    private JsonArray users;
    public static UserDetailUtils initByFile(File config) throws IOException {
        String configStr = FileUtils.readFileToString(config, StandardCharsets.UTF_8);
        UserDetailUtils utils = new UserDetailUtils();
        utils.users = new Gson().fromJson(configStr, JsonObject.class).getAsJsonArray("users");
        return utils;
    }

    public boolean verify(String username, String password) {
        for (JsonElement user : users) {
            if (
                    user.getAsJsonObject().get("username").getAsString().equals(username) &&
                            user.getAsJsonObject().get("password").getAsString().equals(password)
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        JsonObject object = new JsonObject();
        object.add("users", users);
        return object.toString();
    }
}
