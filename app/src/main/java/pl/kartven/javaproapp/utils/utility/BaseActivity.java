package pl.kartven.javaproapp.utils.utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.function.Consumer;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {

    public static <T extends ViewModel> T initViewModel(ViewModelStoreOwner owner, Class<T> modelClass) {
        return new ViewModelProvider(owner).get(modelClass);
    }

    protected <T extends ViewModel> T initViewModel(Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }

    protected void initHeader() {
    }

    protected void initContent() {
    }

    protected void initActions() {
    }

    protected final void handleError(@NonNull Runnable action) {
        handleError(true, action);
    }

    protected final void handleError(boolean showToast, @NonNull Runnable action) {
        if (showToast) ActivityUtils.showToast(this, Constant.Expression.INTERNAL_ERROR);
        action.run();
        return;
    }
}
