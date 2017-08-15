package mostcommonwords.ruorlov.ru.mostcommonwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mostcommonwords.ruorlov.ru.mostcommonwords.tools.InstallService;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startService(new Intent(MainActivity.this, InstallService.class));

        setContentView(R.layout.activity_main);

        System.out.println("MainActivity >>>>");

    }
}
