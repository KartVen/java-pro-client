package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.utils.adapter.TextWatcherAdapter;

public class FieldUtils {
    private FieldUtils() {
    }

    public static class EmailValidator extends Validator {
        private static final String REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        private final Pattern pattern = Pattern.compile(REGEX);

        @Nullable
        @Override
        protected Integer checkRules() {
            if (StringUtils.isEmpty(currentState)) return R.string.field_is_required;
            if (!pattern.matcher(currentState).matches()) return R.string.bad_email_format;
            return null;
        }
    }

    public static class NicknameValidator extends Validator {
    }

    public static class PasswordValidator extends Validator {
        private static final String REGEX = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + ".{8,20}$";
        private final Pattern pattern = Pattern.compile(REGEX);

        @Nullable
        @Override
        protected Integer checkRules() {
            if (StringUtils.isEmpty(currentState)) return R.string.field_is_required;
            if (!pattern.matcher(currentState).matches()) return R.string.bad_password_format;
            return null;
        }
    }
}