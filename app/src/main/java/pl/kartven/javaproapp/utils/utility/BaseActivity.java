package pl.kartven.javaproapp.utils.utility;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Initializes ViewModel class.
     *
     * @param modelClass The class of the ViewModel.
     */
    protected <T extends ViewModel> T initViewModel(Class<T> modelClass) {
        return ViewModelUtils.initViewModel(this, modelClass);
    }


    /**
     * Initializes values from instance saved.
     *
     * @param savedInstanceState Data supplied in onSaveInstanceState. Can be null.
     */
    @SuppressWarnings("deprecation")
    protected void initBundle(@Nullable Bundle savedInstanceState){

    }

    /**
     * Initializes all actions in the Activity.
     */
    protected void initActions() {
    }

    /**
     * Initializes all content in the Activity.
     */
    protected void initContent() {
    }



    protected final void handleError(@NonNull Runnable action) {
        handleError(true, action);
    }

    protected final void handleError(boolean showToast, @NonNull Runnable action) {
        if (showToast) ActivityUtils.showToast(this, Constant.Expression.INTERNAL_ERROR);
        action.run();
        finish();
    }
}
