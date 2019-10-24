package cn.ommiao.base.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static final String FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/cn.ommiao.autotask/files";

    public static final String ORDER_DIR = FILE_DIR + "/order";

    public static boolean copyAssetFile(Context context, String fromPath, String toPath) {
        if (context == null || TextUtils.isEmpty(fromPath) || TextUtils.isEmpty(toPath)) {
            return false;
        }
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            is = context.getAssets().open(fromPath);
            File toFile = new File(toPath);
            if (toFile.exists()) {
                toFile.delete();
            }
            toFile.createNewFile();
            toFile.setReadable(true, false);
            toFile.setWritable(true, false);
            toFile.setExecutable(true, false);
            fos = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            // 循环从输入流读取数据到 buffer 中
            while ((byteCount = is.read(buffer)) != -1) {
                // 将读取的输入流写入到输出流
                fos.write(buffer, 0, byteCount);
            }
            // 刷新缓冲区
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void writeTask(String taskJson){
        writeTxtToFile(ORDER_DIR + "/task.json", taskJson);
    }

    public static void writeTxtToFile(String filePath, String content)
    {
        FileOutputStream fos = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File file = new File(filePath);
            if(file.getParentFile() != null && !file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            // 先清空内容再写入
            try
            {
                fos = new FileOutputStream(file);

                byte[] buffer = content.getBytes();
                fos.write(buffer);
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (fos != null)
                    {
                        fos.close();
                    }
                }
                catch (Exception e)
                {
                    e.fillInStackTrace();
                }

            }

        }

    }

    public static String readTask(){
        return readTxtFromFile(ORDER_DIR + "/task.json");
    }

    public static String readTxtFromFile(String filePath)
    {

        StringBuilder sb = new StringBuilder();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            try
            {
                File file = new File(filePath);
                if(!file.exists()){
                    return "";
                }
                FileInputStream inputStream = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                while (len > 0)
                {
                    sb.append(new String(buffer, 0, len));

                    len = inputStream.read(buffer);
                }
                inputStream.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
