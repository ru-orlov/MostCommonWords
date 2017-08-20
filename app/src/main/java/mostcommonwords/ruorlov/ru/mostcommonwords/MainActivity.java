package mostcommonwords.ruorlov.ru.mostcommonwords;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.HashMap;

import mostcommonwords.ruorlov.ru.mostcommonwords.tools.DBHelper;
import mostcommonwords.ruorlov.ru.mostcommonwords.tools.InstallService;
import mostcommonwords.ruorlov.ru.mostcommonwords.tools.LeftSpinnerSelectedListener;

import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.DELANG;
import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.ENGLANG;
import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.FIRSTWORD;
import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.LEFTLANG;
import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.RIGHTLANG;
import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.SECONDWORD;

public class MainActivity extends AppCompatActivity {
    DBHelper dbhelper;
    ListView leftListView;
    public Spinner leftSpinner = null;
    Spinner rightSpinner = null;
    public int spinnerPosition;
    public HashMap<String, String> languages = new HashMap<>();
    public int langList = R.array.lang_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, InstallService.class));
        setContentView(R.layout.activity_main);

        languages.put(LEFTLANG, ENGLANG);
        languages.put(RIGHTLANG, DELANG);

        leftSpinner = (Spinner) findViewById(R.id.left_lang);
        rightSpinner = (Spinner) findViewById(R.id.rihgt_lang);
        ArrayAdapter<?> adapterLeft = ArrayAdapter.createFromResource(this, R.array.lang_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> adapterRight = ArrayAdapter.createFromResource(this, R.array.lang_list, android.R.layout.simple_spinner_item);
        adapterLeft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinner.setAdapter(adapterLeft);
        rightSpinner.setAdapter(adapterRight);
        leftSpinner.setOnItemSelectedListener(new LeftSpinnerSelectedListener(this));
//        rightSpinner.setOnItemSelectedListener(new SpinnerSelectedListener("right"));
        //TODO: move this shit into SpinnerSelectedListener class
//        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String[] lang_labels = parent.getResources().getStringArray(R.array.lang_list);
//                String[] lang_tables = parent.getResources().getStringArray(R.array.table_list);
//                if (!languages.get(LEFTLANG).equals(lang_labels[position])){
//                    languages.put(LEFTLANG, lang_tables[position]);
//                    fillListsOnStart();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        //TODO: move this shit into SpinnerSelectedListener class
//        rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String[] lang_labels = parent.getResources().getStringArray(R.array.lang_list);
//                String[] lang_tables = parent.getResources().getStringArray(R.array.table_list);
//                if (!languages.get(RIGHTLANG).equals(lang_labels[position])){
//                    languages.put(RIGHTLANG, lang_tables[position]);
//                    fillListsOnStart();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        rightSpinner.setSelection(1);
        fillListsOnStart();
    }

    public void fillListsOnStart(){
        leftListView = (ListView) findViewById(R.id.wordsList);
        Cursor leftCursor = dbhelper.getPairLanguage(languages.get(LEFTLANG), languages.get(RIGHTLANG));
        String[] from = new String[] {FIRSTWORD, SECONDWORD };
        int[] to = new int[] { R.id.left_lang_word, R.id.right_lang_word};

        SimpleCursorAdapter left_words = new SimpleCursorAdapter(this, R.layout.lang_row, leftCursor, from, to, 0);
        leftListView.setAdapter(left_words);

    }

}
