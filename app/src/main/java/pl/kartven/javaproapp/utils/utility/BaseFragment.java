package pl.kartven.javaproapp.utils.utility;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.function.Consumer;

import io.vavr.control.Option;

public abstract class BaseFragment extends Fragment {
    /**
     * Initializes ViewModel class.
     *
     * @param modelClass The class of the ViewModel.
     */
    protected <T extends ViewModel> T initViewModel(Class<T> modelClass) {
        return ViewModelUtils.initViewModel(this, modelClass);
    }

    /**
     * Get Activity ViewModel.
     *
     * @param modelClass The class of the ViewModel.
     */
    protected <T extends ViewModel> T getActivityViewModel(Class<T> modelClass) {
        return ViewModelUtils.initViewModel(requireActivity(), modelClass);
    }

    /**
     * Initializes all actions in the Fragment.
     */
    protected void initActions() {
    }

    /**
     * Initializes all content in the Fragment.
     */
    protected void initContent() {
    }

    protected final void handleError(@NonNull Runnable action) {
        handleError(true, action);
    }

    protected final void handleError(boolean showToast, @NonNull Runnable action) {
        Log.d("ErrorHandling", this.getClass().getSimpleName() + ".handleError");
        if (showToast)
            ActivityUtils.showToast(requireActivity(), Constant.Expression.INTERNAL_ERROR);
        action.run();
        requireActivity().finish();
    }

    protected final void updateSupportActionBar(@Nullable Consumer<ActionBar> actionBarConsumer) {
        Option.of(getActivity())
                .map(AppCompatActivity.class::cast)
                .map(AppCompatActivity::getSupportActionBar)
                .peek(actionBarConsumer);
    }
}
