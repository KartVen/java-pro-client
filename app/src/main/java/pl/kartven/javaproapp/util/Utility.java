package pl.kartven.javaproapp.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public interface Utility {

    default void goToActivity(@NonNull Context context, @NonNull Class<? extends AppCompatActivity> activityClass){
        context.startActivity(new Intent(context, activityClass));
    }

    default void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
