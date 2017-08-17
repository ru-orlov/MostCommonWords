package mostcommonwords.ruorlov.ru.mostcommonwords;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import mostcommonwords.ruorlov.ru.mostcommonwords.tools.DBHelper;
import mostcommonwords.ruorlov.ru.mostcommonwords.tools.InstallService;

public class MainActivity extends AppCompatActivity {
    DBHelper dbhelper;
    ListView leftListView, rightListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, InstallService.class));
        setContentView(R.layout.activity_main);

        final Spinner leftSpinner = (Spinner)findViewById(R.id.left_lang);
        final Spinner rightSpinner = (Spinner)findViewById(R.id.rihgt_lang);
        ArrayAdapter<String> adapterLeft = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, initLangArray());
        ArrayAdapter<String> adapterRight = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, initLangArray());
        adapterLeft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinner.setAdapter(adapterLeft);
        rightSpinner.setAdapter(adapterRight);
        rightSpinner.setSelection(1);
        fillListsOnStart();
    }

    private String[] initLangArray(){
        return new String[]{"English", "German", "French", "Italian", "Polish", "Russian"};
    }

    private void fillListsOnStart(){
        leftListView = (ListView) findViewById(R.id.wordsList);
        //rightListView = (ListView) findViewById(R.id.rightList);
        Cursor leftCursor = getTwoLanguage("language_eng", "language_de");
        //Cursor rightCursor = getLanguage("language_de");
        String[] from = new String[] {"firstword", "secondword" };
        int[] to = new int[] { R.id.left_lang_word, R.id.right_lang_word};

        SimpleCursorAdapter left_words = new SimpleCursorAdapter(this,
                R.layout.lang_row, leftCursor, from, to);
        leftListView.setAdapter(left_words);

    }

//    public Cursor getLanguage(String table_lang) {
//        dbhelper = new DBHelper(this.getBaseContext());
//        SQLiteDatabase db = dbhelper.getWritableDatabase();
//        return db.query(table_lang, new String[] { "_id", "word_id", "word", "word_description" }, null,
//                null, null, null, null);
//    }

    public Cursor getTwoLanguage(String first_lang, String second_lang) {
        dbhelper = new DBHelper(this.getBaseContext());
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        return db.rawQuery("select t1._id AS _id, " +
                "t1.word AS firstword, " +
                "t2.word AS secondword "+
                "from " + first_lang +" t1, "+
                second_lang+" t2 " +
                "WHERE t1.word_id = t2.word_id" +
                " GROUP BY 1 ORDER by 1", null);
    }
}
