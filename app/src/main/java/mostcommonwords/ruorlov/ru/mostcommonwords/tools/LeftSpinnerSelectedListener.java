package mostcommonwords.ruorlov.ru.mostcommonwords.tools;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import mostcommonwords.ruorlov.ru.mostcommonwords.MainActivity;
import mostcommonwords.ruorlov.ru.mostcommonwords.R;

import static mostcommonwords.ruorlov.ru.mostcommonwords.tools.ConstantsApp.LEFTLANG;

public class LeftSpinnerSelectedListener implements OnItemSelectedListener {

    private MainActivity activity = null;

    public LeftSpinnerSelectedListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.activity.spinnerPosition = this.activity.langList;
        this.activity.languages.put(LEFTLANG, parent.getResources().getStringArray(R.array.table_list)[position]);
        this.activity.fillListsOnStart();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.activity.spinnerPosition = Integer.parseInt(null);
        this.activity.fillListsOnStart();
    }

}
