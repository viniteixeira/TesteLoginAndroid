package support.api.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DAOHelper {

    public interface Adapter<T extends DAOHelper> {
        void onRead(T dao, Cursor cursor) throws Exception;
    }

    private SQLiteDatabase mDB;

    public DAOHelper(SQLiteDatabase db) throws Exception {
        mDB = db;
    }

    public SQLiteDatabase getDB() {
        return mDB;
    }

    /**
     * Executa query e verifica se tem parametros. Query sem retorno
     *
     * @param query
     * @param param
     */

    public int deletar(String query, Object... param) {

        SQLiteStatement command;
        int position = 0;

        command = mDB.compileStatement(query);

        if (param.length > 0) {
            command.clearBindings();
            for (Object object : param) {
                position++;
                if (object instanceof String) {
                    command.bindString(position, (String) object);
                } else if (object instanceof Integer) {
                    command.bindLong(position, (Integer) object);
                } else if (object instanceof Boolean) {
                    command.bindLong(position, ((Boolean) object) ? 1 : 0);
                } else if (object instanceof Double || object instanceof Float) {
                    command.bindDouble(position, (Double) object);
                } else if (object instanceof byte[]) {
                    command.bindBlob(position, (byte[]) object);
                } else if (object == null) {
                    command.bindNull(position);
                }
            }
        }

        try {
            return command.executeUpdateDelete();
        } finally {
            command.close();
        }
    }

    public int update(String query, Object... param) {

        SQLiteStatement command;
        int position = 0;

        command = mDB.compileStatement(query);

        if (param.length > 0) {
            command.clearBindings();
            for (Object object : param) {
                position++;
                if (object instanceof String) {
                    command.bindString(position, (String) object);
                } else if (object instanceof Integer) {
                    command.bindLong(position, (Integer) object);
                } else if (object instanceof Boolean) {
                    command.bindLong(position, ((Boolean) object) ? 1 : 0);
                } else if (object instanceof Double || object instanceof Float) {
                    command.bindDouble(position, (Double) object);
                } else if (object instanceof byte[]) {
                    command.bindBlob(position, (byte[]) object);
                } else if (object == null) {
                    command.bindNull(position);
                }
            }
        }

        try {
            return command.executeUpdateDelete();
        } finally {
            command.close();
        }
    }


    public void executaNoQuery(String query, Object... param) {

        SQLiteStatement command;
        int position = 0;


        command = mDB.compileStatement(query);

        if (param.length > 0) {
            command.clearBindings();
            for (Object object : param) {
                position++;

                if (object instanceof String) {
                    command.bindString(position, (String) object);
                } else if (object instanceof Integer) {
                    command.bindLong(position, (Integer) object);
                } else if (object instanceof Boolean) {
                    command.bindLong(position, ((Boolean) object) ? 1 : 0);
                } else if (object instanceof Double || object instanceof Float) {
                    command.bindDouble(position, (Double) object);
                } else if (object instanceof byte[]) {
                    command.bindBlob(position, (byte[]) object);
                } else if (object == null) {
                    command.bindNull(position);
                }
            }
        }
        try {
            command.execute();

        } catch (Exception e) {
            Log.e("Erro no banco", e.getMessage());
            e.printStackTrace();
        }
        command.close();
    }


    /**
     * Executa query e verifica se tem parametros. Query com retorno
     *
     * @param query
     * @param param
     */

    public Cursor executaQuery(String query, Object... param) {
        ArrayList<String> arrString = new ArrayList<>();
        for (Object object : param) {
            arrString.add(String.valueOf(object));
        }
        String[] paramObj = Arrays.copyOf(
                arrString.toArray(),
                arrString.size(),
                String[].class
        );
        Cursor cursor = mDB.rawQuery(query, paramObj);
        cursor.moveToFirst();
        return cursor;
    }

    public String getString(Cursor cursor, String campo) {
        return cursor.getString(cursor.getColumnIndex(campo));
    }

    public int getInt(Cursor cursor, String campo) {
        return cursor.getInt(cursor.getColumnIndex(campo));
    }


    public double getDouble(Cursor cursor, String campo) {
        return cursor.getDouble(cursor.getColumnIndex(campo));
    }

    public byte[] getBlob(Cursor cursor, String campo) {
        return cursor.getBlob(cursor.getColumnIndex(campo));
    }

    public void doRead(final Cursor cursor, @NonNull final DAOHelper.Adapter adapter) throws Exception {
        //Verifica se o cursor não esta nulo
        if (cursor != null) {
            try {
                //Verifica se é um cursor valido
                if (cursor.moveToFirst() && cursor.getCount() > 0) {
                    do {
                        adapter.onRead(this, cursor);
                    } while (cursor.moveToNext()); //Loop
                }
            } finally {
                cursor.close();
            }
        }
    }
}
