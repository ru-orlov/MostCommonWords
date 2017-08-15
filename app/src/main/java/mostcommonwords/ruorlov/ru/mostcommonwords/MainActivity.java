package mostcommonwords.ruorlov.ru.mostcommonwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import mostcommonwords.ruorlov.ru.mostcommonwords.tools.InstallService;

public class MainActivity extends AppCompatActivity {

    private String[] arrayLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, InstallService.class));
        setContentView(R.layout.activity_main);

        final Spinner leftSpinner = (Spinner)findViewById(R.id.left_lang);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, initLangArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinner.setAdapter(adapter);
    }

    private String[] initLangArray(){
        return this.arrayLang = new String[] {
                "English", "German", "French", "Italian", "Polish", "Russian"
        };
    }
}
