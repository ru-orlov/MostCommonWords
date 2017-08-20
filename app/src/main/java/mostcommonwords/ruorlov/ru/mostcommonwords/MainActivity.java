package mostcommonwords.ruorlov.ru.mostcommonwords;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.HashMap;

import mostcommonwords.ruorlov.ru.mostcommonwords.tools.DBHelper;
import mostcommonwords.ruorlov.ru.mostcommonwords.tools.InstallService;

public class MainActivity extends AppCompatActivity {
    DBHelper dbhelper;
    ListView leftListView;
    HashMap<String, String> languages = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, InstallService.class));
        setContentView(R.layout.activity_main);

        languages.put("leftlang", "language_eng");
        languages.put("rightlang", "language_de");

        final Spinner leftSpinner = (Spinner)findViewById(R.id.left_lang);
        final Spinner rightSpinner = (Spinner)findViewById(R.id.rihgt_lang);
        ArrayAdapter<?> adapterLeft = ArrayAdapter.createFromResource(this, R.array.lang_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> adapterRight = ArrayAdapter.createFromResource(this, R.array.lang_list, android.R.layout.simple_spinner_item);
        adapterLeft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinner.setAdapter(adapterLeft);
        rightSpinner.setAdapter(adapterRight);
//        leftSpinner.setOnItemSelectedListener(new SpinnerSelectedListener("left"));
//        rightSpinner.setOnItemSelectedListener(new SpinnerSelectedListener("right"));
        //TODO: move this shit into SpinnerSelectedListener class
        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] lang_labels = parent.getResources().getStringArray(R.array.lang_list);
                String[] lang_tables = parent.getResources().getStringArray(R.array.table_list);
                if (!languages.get("leftlang").equals(lang_labels[position])){
                    languages.put("leftlang", lang_tables[position]);
                    fillListsOnStart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //TODO: move this shit into SpinnerSelectedListener class
        rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] lang_labels = parent.getResources().getStringArray(R.array.lang_list);
                String[] lang_tables = parent.getResources().getStringArray(R.array.table_list);
                if (!languages.get("rightlang").equals(lang_labels[position])){
                    languages.put("rightlang", lang_tables[position]);
                    fillListsOnStart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rightSpinner.setSelection(1);
        fillListsOnStart();
    }

    private void fillListsOnStart(){
        leftListView = (ListView) findViewById(R.id.wordsList);
        Cursor leftCursor = getPairLanguage(languages.get("leftlang"), languages.get("rightlang"));
        String[] from = new String[] {"firstword", "secondword" };
        int[] to = new int[] { R.id.left_lang_word, R.id.right_lang_word};

        SimpleCursorAdapter left_words = new SimpleCursorAdapter(this, R.layout.lang_row, leftCursor, from, to);
        leftListView.setAdapter(left_words);

    }

    public Cursor getPairLanguage(String first_lang, String second_lang) {
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
