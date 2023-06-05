package com.gs.infra.server;

import com.gs.infra.service.IbanResponse;
import com.gs.model.Account;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SwaggerGenerator {


    public static String generate(String description, String title, String path, String summary, int port,
                                  Class<?> request, Class<?> response){
        StringBuffer sb = new StringBuffer(1024);
        sb.append("{\t\"swagger\": \"2.0\",\n\t\"info\": {\n");
        sb.append("\t\t\"description\": \"");
        sb.append(description);
        sb.append("\",\n\t\t\"version\": \"V1 .0\",\n\t\t\"title\": \"");
        sb.append(title);
        sb.append("\",\n\t\t\"contact\": {\n\t\t\t\"name\": \"Gigasapces\",\n\t\t\t\"url\": \"http://gigaspaces.com\"\n");
        sb.append("\t\t}\n\t},\n\t\"host\": \"localhost:");
        sb.append(port);
        sb.append("\",\n\t\"tags\": [{\n\t\t\"name\": \"swagger\"\n\t}],\n");
        sb.append("\t\"schemes\": [\"https\"],\n\t\"consumes\": [\"application/json\"],\n\t\"produces\": [\"application/json\"],\n");
        sb.append("\t\"paths\":{\n\t\t\t\t\"" + path + "\":{\n\t\t\t\"get\": {\n\t\t\t\t");
        sb.append("\"summary\":\"");
        sb.append(summary);
        sb.append("\",\n\t\t\t\t\"produces\":[\"applicaion/json\"],\n\t\t\t\t\"parameters\":[{");
        sb.append("\n\t\t\t\t\t\"in\":\"body\",\n\t\t\t\t\t\"name\":\"body\",\n\t\t\t\t\t\"required\":true,");
        sb.append("\n\t\t\t\t\t\"schema\":{\"$ref\":\"#/definitions/"+ request.getSimpleName() +"\"\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t],");
        sb.append("\n\t\t\t\t\"responses\":{\n\t\t\t\t\t\"200\":\n\t\t\t\t\t\t{\"description\":\"Success\",\n\t\t\t\t\t\t\"schema\":{\"$ref\":\"#/definitions/"+ response.getSimpleName() +"\"}}}}}},");
        sb.append("\n\t\t\t\t\"definitions\":{\n\t\t\t\t\t\"");
        sb.append(response.getSimpleName());
        sb.append("\":[{\n\t\t\t\t\t\t\"type\":\"object\",\n\t\t\t\t\t\t\"properties\": {");
        String delimiter = "";
        boolean isAccount = false;
        for (Field field: response.getDeclaredFields()){
            if (field.getType().getSimpleName().equals("List")) {
                isAccount = true;
                sb.append(",\n\t\t\t\t\t\t\"" + field.getName()+ "\":{");
                sb.append("\n\t\t\t\t\t\t\t\"type\":\"array\",");
                sb.append("\n\t\t\t\t\t\t\t\"items\": {");
                sb.append("\n\t\t\t\t\t\t\t\t\"$ref\":\"#/definitions/Account\"");
                sb.append("\n\t\t\t\t\t\t\t}");
                sb.append("\n\t\t\t\t\t\t}");
            } else {
                sb.append(delimiter);
                sb.append(formatProperty(field.getType(), field.getName()));
                delimiter = ",";
            }
        }
        sb.append("\n\t\t\t\t\t\t}\n\t\t\t\t\t}],\n\t\t\t\t\t\"");
        sb.append(request.getSimpleName());
        sb.append("\":{\"type\":\"object\", \"properties\": {");
        delimiter = "";
        for (Field field: request.getDeclaredFields()){
            sb.append(delimiter);
            sb.append(formatProperty(field.getType(), field.getName()));
            delimiter = ",";
        }


        sb.append("\n\t\t\t\t\t\t}\n\t\t\t\t\t}");

        if (isAccount) {
            sb.append(",\n\t\t\t\t\t\""+Account.class.getSimpleName());
            sb.append("\":{\"type\":\"object\", \"properties\": {");
            delimiter = "";
            for (Field field : Account.class.getDeclaredFields()) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                sb.append(delimiter);
                sb.append(formatProperty(field.getType(), field.getName()));
                delimiter = ",";
            }
            sb.append("\n\t\t\t}\n\t\t}");
        }
        sb.append("\n\t}\n}\n");
        return sb.toString();
    }

    private static String formatProperty(Class<?> type, String name){
        StringBuffer sb = new StringBuffer(64);
        sb.append("\n\t\t\t\t\t\t\"");
        sb.append(name);
        sb.append("\": {\n\t\t\t\t\t\t\t\"type\":\"");
        if (type.getSimpleName().equalsIgnoreCase("short") ||
                type.getSimpleName().equalsIgnoreCase("long") ||
                type.getSimpleName().equalsIgnoreCase("int")) {
            type = Integer.class;
        }
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("\"");
        if(type.getSimpleName().equalsIgnoreCase("integer")){
            sb.append(",\n\t\t\t\t\t\t\t\"format\": \"int64\"");
        }
        sb.append("}");
        return sb.toString();
    }
}
