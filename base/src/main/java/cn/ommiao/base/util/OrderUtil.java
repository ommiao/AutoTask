package cn.ommiao.base.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OrderUtil {

    public static String readOrders(Context context){
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("task.json");
            String str = getString(inputStream);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
