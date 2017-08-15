package mostcommonwords.ruorlov.ru.mostcommonwords.tools;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InstallService extends Service {
    DBHelper dbHelper;

    public InstallService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(InstallService.class.getName()," >>> service created...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(InstallService.class.getName()," >>> service started...");
        dbHelper = new DBHelper(this.getBaseContext());
        FileHandler fileHandler = new FileHandler();
        ArrayList<String> res;
        String[] languages = {"language_de","language_eng", "language_fr", "language_it", "language_pl", "language_ru"};
        for (String lang : languages) {
            res = fileHandler.istreamToArray(loadAssetTextAsString(lang+".csv"));
            dbHelper.insertLang(lang, res);
            res = new ArrayList<>();
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(InstallService.class.getName()," >>> service stopped...");
    }

    public InputStream loadAssetTextAsString(String name) {
        try {
            return getAssets().open(name);
        } catch (IOException e) {
            Log.w(InstallService.class.getName()," >>> error on read file...");
        }
        return null;
    }
}
