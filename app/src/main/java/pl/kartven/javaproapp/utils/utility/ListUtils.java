package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class ListUtils {
    private ListUtils() {
    }

    public static <T> List<T> extractList(Resource<List<T>> data) {
        if (data == null) return Collections.emptyList();
        if (data.isSuccess()) return data.getData();
        return Collections.emptyList();
    }

    public static <T> List<T> extractList(Resource<List<T>> data, @NonNull Context contextForErrorInfo) {
        if (data == null) return Collections.emptyList();
        if (data.isSuccess()) return data.getData();
        Toast.makeText(contextForErrorInfo, data.getMessage(), Toast.LENGTH_SHORT).show();
        return Collections.emptyList();
    }
}
