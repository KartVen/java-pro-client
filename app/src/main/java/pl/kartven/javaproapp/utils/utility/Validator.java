package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.apache.commons.lang3.StringUtils;

import pl.kartven.javaproapp.R;

public abstract class Validator {
    @Nullable
    protected String currentState = null;
    protected final MutableLiveData<Integer> errorState = new MutableLiveData<>();
    private boolean isActived = false;

    public final Validator validate() {
        errorState.postValue(checkRules());
        return this;
    }

    @Nullable
    public String getCurrentState() {
        return currentState;
    }

    public boolean isActived() {
        return isActived;
    }

    public final Validator setCurrentState(@NonNull String currentState) {
        this.currentState = currentState;
        isActived = true;
        return this;
    }

    public final LiveData<Integer> getErrorState() {
        return errorState;
    }

    @Nullable
    protected Integer checkRules() {
        return StringUtils.isEmpty(currentState) ? R.string.field_is_required : null;
    }

    public static <T extends TextView> void showWarning(@NonNull Context context, @NonNull T textViewInfo, @Nullable Integer integer) {
        if (integer == null) {
            textViewInfo.setVisibility(View.GONE);
            return;
        }
        textViewInfo.setVisibility(View.VISIBLE);
        textViewInfo.setText(context.getString(integer));
    }
}
