package pl.kartven.javaproapp.utils.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

    protected void initActions(){
    }
}
