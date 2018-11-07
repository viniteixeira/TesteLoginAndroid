package support.api;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class SupportBase {

    public static Context Context;
    public static int DefaultColor = 0;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public static void setDefaultColor(int color) {
        SupportBase.DefaultColor = color;
    }

    /**
     * Obtem retorno das informações referente a package (APP)
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static PackageInfo getInfoApp(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(context.getPackageName(), 0);
    }

    /**
     * Escreve uma texto em uma stream
     *
     * @param os
     * @param parametro
     * @throws Exception
     */
    public static void writeOutputStream(OutputStream os, String parametro) throws Exception {
        BufferedWriter writer;

        writer = new BufferedWriter(new OutputStreamWriter(os));

        writer.write(parametro);
        writer.flush();

        writer.close();
        os.close();
    }

    /**
     * Le uma stream.
     * Para melhor utilização deste metodo recomendo utilizar no AndroidManifest.xml
     * na parte do <application> a propriedade android:largeHeap="true",
     * isso faz com que o applicativo utilize mais memoria e faz com que não
     * aconteça o problema de 'out of memory error' execption
     *
     * @param is
     * @return
     * @throws Exception
     */
    public synchronized static String readInputStream(InputStream is) throws Exception {
        StringBuilder sb = new StringBuilder();
        int lenByte = 1024;
        byte[] bytes = new byte[lenByte];
        int numRead;

        try {
            while ((numRead = is.read(bytes)) >= 0) {
                sb.append(new String(bytes, 0, numRead));
            }
        } finally {
            is.close();
        }

        return sb.toString();
    }

    /**
     * Obtem diretorio do app
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static File getAppDir(Context context) throws Exception {
        PackageManager packageManager;
        String packageName;
        PackageInfo packageInfo;
        File file;

        packageManager = context.getPackageManager();
        packageName = context.getPackageName();

        packageInfo = packageManager.getPackageInfo(packageName, 0);
        file = new File(packageInfo.applicationInfo.dataDir);

        return file;
    }

    /**
     * ZIP
     *
     * @param files
     * @param zipFile
     * @throws IOException
     */
    public static void zip(File[] files, String zipFile) throws IOException {
        int BUFFER_SIZE = 1024;
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].getName());
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                }
            }
        } finally {
            out.close();
        }
    }

    /**
     * UNZIP
     *
     * @param zipFile
     * @throws IOException
     * @par?am location
     */
    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + File.separator + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        FileOutputStream fout = new FileOutputStream(path, false);
                        try {
                            byte[] buffer = new byte[1024];
                            int read = 0;
                            while ((read = zin.read(buffer)) != -1) {
                                fout.write(buffer, 0, read);
                            }

                            zin.closeEntry();
                        } finally {
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            Log.e("ZIP", "Unzip exception", e);
        }
    }

    /**
     * Fecha/Esconde teclado
     */
    public static void hideKeyBoard(Context context, final EditText editText) {
        SupportBase.hideKeyBoard(context, editText, 200);
    }

    /**
     * Fecha/Esconde teclado
     */
    public static void hideKeyBoard(Context context, final EditText editText, int time) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        editText.clearFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }, time);
    }

    public static void hideKeyBoard(Context context, final View editText, int time) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        editText.clearFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }, time);
    }


    /**
     * Formata moeda
     */
    public static String formataMoeda(double valor) {
        return NumberFormat.getCurrencyInstance().format(valor);
    }

    /**
     * Tenta converter valor string em int, caso der erro retorna valor default
     *
     * @param value
     * @param valueDefault
     * @return
     */
    public static int tryParseInt(String value, int valueDefault) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception err) {
            return valueDefault;
        }
    }

    public static double tryParseDouble(String value, double valueDefault) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception err) {
            return valueDefault;
        }
    }

    /**
     * Obtem width/height total da Activity
     *
     * @param activity
     * @return
     */
    public static HashMap<String, Integer> getXYActivity(Activity activity) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        hashMap.put("width", width);
        hashMap.put("height", height);

        return hashMap;
    }


    @SuppressWarnings("unchecked")
    public static <T> T clone(T objeto) {
        Gson gson = new Gson();
        T target = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(gson.toJson(objeto));
            target = (T) gson.fromJson(jsonObject.toString(), objeto.getClass());
        } catch (Exception err) {
            err.printStackTrace();
        }

        return target;
    }

}
