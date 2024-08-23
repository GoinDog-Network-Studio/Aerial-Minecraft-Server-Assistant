package cn.goindog.amsm;

import cn.goindog.amsm.utils.UserDetailUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerFields {
    public static File user_config = new File(".amsm", "user.json");
    public static UserDetailUtils userDetailUtils;

    static {
        try {
            if (!user_config.exists()) {
                JsonObject object = new JsonObject();
                JsonArray user_list = new JsonArray();

                JsonObject root_user = new JsonObject();
                root_user.addProperty("username", "root");
                root_user.addProperty("password", "root");

                user_list.add(root_user);

                object.add("users", user_list);

                FileUtils.writeStringToFile(user_config, object.toString(), StandardCharsets.UTF_8);
            }
            userDetailUtils = UserDetailUtils.initByFile(user_config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
