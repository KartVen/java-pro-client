package pl.kartven.javaproapp.utils.creator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.kartven.javaproapp.R;

public class UIElementCreator {
    private UIElementCreator() {
    }

    public static int convertSpToPixels(float sizeSp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) ((int) sizeSp / density);
    }

    public static TextView createTextView(Context context, String text) {
        Resources resources = context.getResources();
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.common__space_size), 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextColor(resources.getColor(R.color.dark_blue, context.getTheme()));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, convertSpToPixels(resources.getDimension(R.dimen.common__text_h3)));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        return textView;
    }

    public static WebView createWebView(Context context, String text) {
        Resources resources = context.getResources();
        WebView webView = new WebView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.common__space_size), 0, 0);
        webView.setLayoutParams(layoutParams);
        webView.setBackgroundColor(resources.getColor(R.color.light_gray, context.getTheme()));
        webView.setPadding(0, resources.getDimensionPixelSize(R.dimen.common__space_size), 0, context.getResources().getDimensionPixelSize(R.dimen.common__space_size));
        webView.loadDataWithBaseURL(
                null,
                "<html><body style=\"font-size: 14px;\">" + text + "<br></body></html>",
                "text/html",
                "UTF-8",
                null
        );
        return webView;
    }
}

