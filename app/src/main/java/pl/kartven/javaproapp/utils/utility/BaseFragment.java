package pl.kartven.javaproapp.utils.utility;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

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
        if (showToast) ActivityUtils.showToast(requireActivity(), Constant.Expression.INTERNAL_ERROR);
        action.run();
        requireActivity().finish();
    }
}
