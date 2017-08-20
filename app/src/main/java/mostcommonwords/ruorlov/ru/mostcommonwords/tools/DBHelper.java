package mostcommonwords.ruorlov.ru.mostcommonwords.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "mostcommonwords.db";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_LANGUAGES = "CREATE TABLE languages " +
            "(_id integer primary key autoincrement, "+
            "lang_name TEXT not null, " +
            "lang_description TEXT," +
            "lang_code TEXT," +
            "status TEXT);";

    private static final String CREATE_LANGUAGES_IND = "CREATE INDEX " +
            "ix_languages_lang_name ON languages (lang_name);";

    private static final String CREATE_ENG_LANG = "CREATE TABLE language_eng " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_ENG_IND = "CREATE INDEX " +
            "ix_lang_eng_word_id ON language_eng (word_id);";

    private static final String CREATE_DE_LANG = "CREATE TABLE language_de " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_DE_IND = "CREATE INDEX " +
            "ix_lang_de_word_id ON language_de (word_id);";

    private static final String CREATE_PL_LANG = "CREATE TABLE language_pl " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_PL_IND = "CREATE INDEX " +
            "ix_lang_pl_word_id ON language_pl (word_id);";

    private static final String CREATE_RUS_LANG = "CREATE TABLE language_ru " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_RUS_IND = "CREATE INDEX " +
            "ix_lang_RUS_word_id ON language_ru (word_id);";

    private static final String CREATE_FR_LANG = "CREATE TABLE language_fr " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_FR_IND = "CREATE INDEX " +
            "ix_language_fr_word_id ON language_fr (word_id);";

    private static final String CREATE_IT_LANG = "CREATE TABLE language_it " +
            "(_id integer primary key autoincrement, "+
            "word_id INTEGER not null, " +
            "word TEXT not null, " +
            "word_description TEXT);";

    private static final String CREATE_IT_IND = "CREATE INDEX " +
            "ix_language_it_word_id ON language_it (word_id);";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_LANGUAGES);
        Log.w(DBHelper.class.getName(),"languages created...");
        database.execSQL(CREATE_LANGUAGES_IND);
        Log.w(DBHelper.class.getName(),"indexes for languages created...");

        database.execSQL(CREATE_ENG_LANG);
        Log.w(DBHelper.class.getName(),"lang_eng created...");
        database.execSQL(CREATE_ENG_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_eng created...");

        database.execSQL(CREATE_DE_LANG);
        Log.w(DBHelper.class.getName(),"lang_de created...");
        database.execSQL(CREATE_DE_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_de created...");

        database.execSQL(CREATE_PL_LANG);
        Log.w(DBHelper.class.getName(),"lang_pl created...");
        database.execSQL(CREATE_PL_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_pl created...");

        database.execSQL(CREATE_RUS_LANG);
        Log.w(DBHelper.class.getName(),"lang_rus created...");
        database.execSQL(CREATE_RUS_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_rus created...");

        database.execSQL(CREATE_FR_LANG);
        Log.w(DBHelper.class.getName(),"lang_fr created...");
        database.execSQL(CREATE_FR_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_fr created...");

        database.execSQL(CREATE_IT_LANG);
        Log.w(DBHelper.class.getName(),"lang_it created...");
        database.execSQL(CREATE_IT_IND);
        Log.w(DBHelper.class.getName(),"indexes for lang_it created...");
    }

    public void insertLang(String tableName, ArrayList strings){
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
                for (int i = 0; i < strings.size(); i++){
                    contentValues.put("word_id", i);
                    contentValues.put("word", String.valueOf(strings.get(i)).trim());
                    database.insert(tableName,null,contentValues);
                }
            database.setTransactionSuccessful();
        } catch (Exception e){
            Log.e("Error in transaction", e.toString());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS languages");
        database.execSQL("DROP TABLE IF EXISTS lang_eng");
        database.execSQL("DROP TABLE IF EXISTS lang_de");
        database.execSQL("DROP TABLE IF EXISTS lang_pl");
        database.execSQL("DROP TABLE IF EXISTS lang_rus");
        database.execSQL("DROP TABLE IF EXISTS lang_fr");
        database.execSQL("DROP TABLE IF EXISTS lang_it");
        onCreate(database);
    }

    public Cursor getPairLanguage(String first_lang, String second_lang) {
        return db.rawQuery("select t1._id AS _id, " +
                "t1.word AS firstword, " +
                "t2.word AS secondword "+
                "from " + first_lang +" t1, "+
                second_lang+" t2 " +
                "WHERE t1.word_id = t2.word_id" +
                " GROUP BY 1 ORDER by 1", null);
    }
}
