package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Consumer;

public class ActivityUtils {
    private ActivityUtils() {
    }

    public static void goToActivity(@NonNull Context context, @NonNull Class<? extends AppCompatActivity> activityClass){
        context.startActivity(new Intent(context, activityClass));
    }

    public static void goToActivity(@NonNull Context context, @NonNull Class<? extends AppCompatActivity> activityClass, Consumer<Intent> intentSettings){
        Intent intent = new Intent(context, activityClass);
        intentSettings.accept(intent);
        context.startActivity(intent);
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
