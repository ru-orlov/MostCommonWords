package mostcommonwords.ruorlov.ru.mostcommonwords.tools;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FileHandler {
    public ArrayList<String> istreamToArray(InputStream is) {
        BufferedReader in = null;
        try {
            ArrayList<String> result = new ArrayList<>();
            in = new BufferedReader(new InputStreamReader(is));
            String str;
            while ( (str = in.readLine()) != null ) {
                result.add(str);
            }
            return result;
        } catch (IOException e) {
            Log.e(TAG, "Error read input ");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error read input ");
                }
            }
        }

        return null;
    }
}
