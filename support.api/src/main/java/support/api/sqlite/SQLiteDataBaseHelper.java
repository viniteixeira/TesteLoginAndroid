package support.api.sqlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import support.api.utils.LogTrace;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    public static final String PREFS = "PREFS_DB";
    public static final String PREFS_VERSAO = "PREFS_DB_VERSAO";

    public static final String RES = "raw";
    public static final String FILECREATE = "create";
    public static final String FILEDROP = "drop";

    static File sdcard = Environment.getExternalStorageDirectory();
    public final static File SDDIRETORIO	=  new File(sdcard.getAbsolutePath()+"/kleyhertz");
    public final static String DATABASE_NAME = SDDIRETORIO.getAbsolutePath()+"/";

    static Context mContext;
    public static String mNomeDB = "";

    private  static SQLiteDatabase instaceDb = null;

    private SQLiteDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);

        mNomeDB = name;
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Executa leitura e executação do arquivo
        this.executaLeitura(db, SQLiteDataBaseHelper.FILECREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)                                         {
        //Executa leitura e executação do arquivo
        this.executaLeitura(db, SQLiteDataBaseHelper.FILEDROP);

        //Re-cria o banco
        this.onCreate(db);
    }


    private static void copyDataBase() throws IOException{
        //String[] filelist = dbContext.getAssets().list("");
        InputStream myInput = mContext.getAssets().open("kleyhertz.db");

        // It have to be matched with the directory in SDCard
        if (!SDDIRETORIO.exists()) {
            SDDIRETORIO.mkdirs();
        }

        String outFileName = DATABASE_NAME + "kleyhertz.db";
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Leitura e execução das querys em determinado arquivo
     * @param db
     * @param file
     */
    private void executaLeitura(SQLiteDatabase db, String file) {
        BufferedReader reader;
        InputStream inputStream;
        String linhaQuery;
        int fileId;

        try {

            //Obtem ID do arquivo
            fileId = mContext.getResources().getIdentifier(file, SQLiteDataBaseHelper.RES, mContext.getPackageName());

            //Verifica se o ID existe
            if (fileId > 0) {

                //Obtem input stream do arquivo @FILETABLES no res @RAW
                inputStream = mContext.getResources().openRawResource(fileId);

                //Verifica se tem dados no inputStream
                if (inputStream.available() > 0) {

                    //Inicializa BufferReader com todos os dados do inputStream
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    //Inicializa transaction
                    db.beginTransaction();

                    //Percorre linha a linha
                    while ((linhaQuery = reader.readLine()) != null) {
                        linhaQuery = linhaQuery.trim();
                        if (!linhaQuery.isEmpty() && !linhaQuery.startsWith("--"))
                            db.execSQL(linhaQuery);
                    }

                    //Fecha reader
                    reader.close();

                    //Finaliza transaction
                    db.setTransactionSuccessful();
                }
            }
        }
        catch (Exception err) {
            LogTrace.logCatch(mContext, this.getClass(), err, true);
        } finally {
            //Finaliza transaction
            if(db.inTransaction())
                db.endTransaction();
        }
    }

    /**
     * Recebi versao do banco
     * @param context
     * @return
     */
    public static int getVersao(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SQLiteDataBaseHelper.PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SQLiteDataBaseHelper.PREFS_VERSAO, 1);
    }

    /**
     * Metodo que cria o banco de dados
     * @param context
     * @param versao
     * @return
     */
    public static void criaDB(Context context, String nome, int versao) throws Exception {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SQLiteDataBaseHelper.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;

        editor = sharedPreferences.edit();
        editor.putInt(SQLiteDataBaseHelper.PREFS_VERSAO, versao);
        editor.apply();

        SQLiteDatabase db = SQLiteDataBaseHelper.openDB(context, nome);

        //copyDataBase();

        SQLiteDataBaseHelper.closeDB(db);
    }


    /**
     * Abre banco de dados
     * @param context
     * @return
     */
    public static SQLiteDatabase openDB(Context context) throws Exception {
        return SQLiteDataBaseHelper.openDB(context, mNomeDB);
    }

    /**
     * Abre banco de dados
     * @param context
     * @return
     */
    public static SQLiteDatabase openDB(Context context, String nome) throws Exception
    {
        int versao = SQLiteDataBaseHelper.getVersao(context);

        SQLiteDataBaseHelper db = new SQLiteDataBaseHelper(context, nome, versao);

        return db.getWritableDatabase();
    }

    /**
     * Fecha banco de dados
     * @param db
     */
    public static void closeDB(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            if (db.inTransaction())
                db.endTransaction();

            db.close();
        }
    }
}
