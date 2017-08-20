package mostcommonwords.ruorlov.ru.mostcommonwords.tools;

import android.view.View;
import android.widget.AdapterView;

import mostcommonwords.ruorlov.ru.mostcommonwords.R;

public class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

    private String initialValue;

    public SpinnerSelectedListener() {
        super();
    }

    public SpinnerSelectedListener(String initialValue) {
        this();
        this.initialValue = initialValue;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] choose = parent.getResources().getStringArray(R.array.lang_list);
        System.out.println(">>>>>> Selected: "+choose[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
