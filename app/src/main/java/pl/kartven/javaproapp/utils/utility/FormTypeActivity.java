package pl.kartven.javaproapp.utils.utility;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import java.util.function.Consumer;
import java.util.function.Supplier;

import pl.kartven.javaproapp.utils.adapter.TextWatcherAdapter;

public abstract class FormTypeActivity extends BaseActivity {
    public final  <T extends EditText, R extends TextView> void handleField(
            T field,
            R fieldInfo,
            final Consumer<String> updateState,
            final Supplier<LiveData<Integer>> getError,
            final Supplier<Boolean> isFieldActivated
    ) {
        field.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                updateState.accept(s.toString());
            }
        });

        getError.get().observe(this, integer -> {
            if (isFieldActivated.get())
                Validator.showWarning(this, fieldInfo, integer);
        });
    }
}
