package com.ece.bot.dto.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebAppData {
    private String data_check_string;
    private String auth_date;
    private String hash;

    public static UserWebAppData getUserData(String stringForCheck){
        UserWebAppData userWebAppData = new UserWebAppData();
        String userJson = stringForCheck.split("user=")[1].split("\n")[0];
        JsonObject userObject = JsonParser.parseString(userJson).getAsJsonObject();
        try {
            userWebAppData.id = userObject.get("id").getAsString();
            userWebAppData.username = userObject.get("username").getAsString();
            userWebAppData.first_name = userObject.get("first_name").getAsString();
            userWebAppData.last_name = userObject.get("last_name").getAsString();
            userWebAppData.language_code = userObject.get("language_code").getAsString();
        }catch (Exception e){
            return null;
        }

        return userWebAppData;
    }

    public static class UserWebAppData {
        public String id;
        public String first_name;
        public String last_name;
        public String username;
        public String language_code;
        public boolean is_premium;
        public boolean allows_write_to_pm;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", username='" + username + '\'' +
                    ", language_code='" + language_code + '\'' +
                    ", is_premium=" + is_premium +
                    ", allows_write_to_pm=" + allows_write_to_pm +
                    '}';
        }
    }
}
