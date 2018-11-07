package support.api.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Base64OutputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class SupportImagem {

    public static final int RESULT_OPCAO = 0;
    public static final int RESULT_CAMERA = 1;
    public static final int RESULT_GALERIA = 2;

    private Context mContext;
    public static String nameImage;

    public void setNameImage(String nameImage) {
        SupportImagem.nameImage = nameImage;
    }

    public static File mFile;


    public static void setmFile(File mFile) {
        SupportImagem.mFile = mFile;
    }

    public SupportImagem(Context context) {
        mContext = context;
    }

    /**
     * Metodo que obtem file do ResultRequest de retorno da Camera/Galeria
     *
     * @param data
     * @return
     */
    public File obtemImagemIntentData(Intent data) {

        if (data == null)
            return mFile;

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        String nUri = data.getData().getLastPathSegment();

        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        String picturePath;
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        cursor.close();

        return new File(picturePath);

    }

    /**
     * Inicializa camera a partir de uma activity
     *
     * @param activity
     */
    public void showCamera(Activity activity) {
        this.showCameraPadrao(activity);
    }

    /**
     * Inicializa camera a partir de um fragment
     *
     * @param fragment
     */
    public void showCamera(Fragment fragment) {
        this.showCameraPadrao(fragment);
    }

    /**
     * Inicializa camera
     *
     * @param context
     */
    private void showCameraPadrao(Object context) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

        if (context instanceof Fragment)
            ((Fragment) context).startActivityForResult(intent, RESULT_CAMERA);
        else if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent, RESULT_CAMERA);
    }

    /**
     * Inicializa galeria a partir de uma activity
     *
     * @param activity
     */
    public void showGaleria(Activity activity) {
        this.showGaleriaPadrao(activity);
    }

    /**
     * Inicializa galeria a partir de um fragment
     *
     * @param fragment
     */
    public void showGaleria(Fragment fragment) {
        this.showGaleriaPadrao(fragment);
    }

    /**
     * Inicializa galeria
     *
     * @param context
     */
    private void showGaleriaPadrao(Object context) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (context instanceof Fragment)
            ((Fragment) context).startActivityForResult(intent, RESULT_GALERIA);
        else if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent, RESULT_GALERIA);
    }

    /**
     * Inicializa opcao a partir de uma activity
     *
     * @param activity
     */
    public void showOpcao(Activity activity) throws Exception {
        this.showOpcaoPadrao(activity);
    }

    /**
     * Inicializa camera a partir de um fragment
     *
     * @param fragment
     */
    public void showOpcao(Fragment fragment) throws Exception {
        this.showOpcaoPadrao(fragment);
    }

    /**
     * Inicializa opcao
     *
     * @param object
     */
    private void showOpcaoPadrao(Object object) throws Exception {

        Intent intentGaleria = new Intent(Intent.ACTION_PICK);
        intentGaleria.setType("image/*");

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

        Intent chooserIntent = Intent.createChooser(intentGaleria, "Selecione uma imagem");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentCamera});

        Intent intentMultiImage = Intent.createChooser(intentGaleria, "Selecione uma imagem");
        intentMultiImage.setType("image/*");
        intentMultiImage.setAction(Intent.ACTION_GET_CONTENT);
        intentMultiImage.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);


        if (object instanceof Fragment)
            ((Fragment) object).startActivityForResult(intentMultiImage, RESULT_OPCAO);
        else if (object instanceof Activity)
            ((Activity) object).startActivityForResult(intentMultiImage, RESULT_OPCAO);
    }

    /**
     * Metodo encontrado no site do Android para mostrar corretamento um bitmap
     *
     * @param bytes
     * @param reqWidth
     * @param reqHeight
     * @return
     * @link http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
     */
    public static Bitmap decodeSampledBitmap(byte[] bytes, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) throws FileNotFoundException {
        // calculating image size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(new FileInputStream(file), null, options);

        int scale = calculateInSampleSize(options, reqWidth, reqHeight);

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);

    }

    static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image.
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Retorna bitmap com a posição da foto ajustada
     *
     * @param file
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws Exception
     */
    public static Bitmap ajustOrientation(File file, int reqWidth, int reqHeight) throws Exception {
        Matrix matrix;
        Bitmap bitmap = SupportImagem.decodeSampledBitmapFromFile(file, reqWidth, reqHeight);
        ExifInterface exif = new ExifInterface(file.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        if (rotate > 0) {
            matrix = new Matrix();
            matrix.postRotate(rotate);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        }

        return bitmap;
    }

    /**
     * Converte o arquivo em base64
     *
     * @return
     * @throws Exception
     */
    public static String convertBase64(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Converte o arquivo em base64
     *
     * @return
     * @throws Exception
     */
    public static String convertBase64(File file, int width, int height) throws Exception {

        Bitmap bitmap = SupportImagem.decodeSampledBitmapFromFile(file, width, height);
        return SupportImagem.convertBase64(bitmap);
    }

    /**
     * Converte base64 em bitmap
     *
     * @return
     * @throws Exception
     */
    public static Bitmap convertBase64(String base64, int width, int height) throws Exception {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return SupportImagem.decodeSampledBitmap(decodedString, width, height);
    }

    /**
     * Converte base64 em bitmap
     *
     * @return
     * @throws Exception
     */
    public static Bitmap convertBase64(String base64) throws Exception {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    // Converting File to Base64.encode String type using Method
    public static String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }
}
