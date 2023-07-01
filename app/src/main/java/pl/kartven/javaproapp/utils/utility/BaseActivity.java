package pl.kartven.javaproapp.utils.utility;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import java.util.function.Function;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;

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

    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
    }

    @SuppressWarnings("deprecation")
    protected <T> T getVariableFromBundle(@Nullable Bundle savedInstanceState, @NonNull Function<Bundle, T> bundleFunction) {
        return Option.of(savedInstanceState)
                .map(bundleFunction)
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundleFunction)
                                .getOrNull()
                );
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
