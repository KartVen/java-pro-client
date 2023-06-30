package pl.kartven.javaproapp.utils.utility;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelUtils {
    /**
     * Initializes ViewModel class.
     *
     * @param owner ViewModel Owner
     * @param modelClass The class of the ViewModel.
     */
    public static <T extends ViewModel> T initViewModel(ViewModelStoreOwner owner, Class<T> modelClass) {
        return new ViewModelProvider(owner).get(modelClass);
    }
}
