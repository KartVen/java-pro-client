package pl.kartven.javaproapp.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivitySectionCodesDetailsBinding;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.Constants;
import pl.kartven.javaproapp.util.Resource;

@AndroidEntryPoint
public class SectionCodesDetailsActivity extends AppCompatActivity implements ActivityUtility {

    private ActivitySectionCodesDetailsBinding binding;
    private SectionCodesDetailsViewModel viewModel;
    private TopicDomain topic;
    private SectionDomain section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySectionCodesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topic = Option.of(savedInstanceState)
                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                                .getOrNull()
                );

        section = Option.of(savedInstanceState)
                .map(bundle -> (SectionDomain) bundle.getSerializable(Constants.SECTION_MODEL_NAME))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (SectionDomain) bundle.getSerializable(Constants.SECTION_MODEL_NAME))
                                .getOrNull()
                );

        if (topic == null || section == null) {
            goToActivity(this, MainActivity.class);
            showToast(this, "Internal Error");
        }

        binding.sectionCodesDetailsTextView2.setText(String.valueOf(topic.getName()));
        binding.sectionCodesDetailsTextView3.setText(String.valueOf(topic.getDescription()));

        viewModel = new ViewModelProvider(this).get(SectionCodesDetailsViewModel.class);

        initActions();
        initCodeView();
    }

    private void initActions() {
        binding.sectionCodesDetailsImageView1.setOnClickListener(v -> onBackPressed());
    }

    private void initCodeView() {
        LinearLayout linearLayout = binding.sectionCodesDetailsLinear2;
        TextView textView = binding.sectionCodesDetailsTextView4;
        WebView webView = binding.sectionCodesDetailsWebView1;

        getList(viewModel.getCodesOfSection(section.getId())).forEach(
                codeDomain -> {
                    linearLayout.addView(createTextView(textView, codeDomain));
                    linearLayout.addView(createWebView(webView, codeDomain));
                }
        );
    }

    private View createTextView(TextView templateTextView, CodeDomain codeDomain) {
        TextView textView = new TextView(this);
        if (templateTextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            textView.setLayoutParams((ViewGroup.MarginLayoutParams) templateTextView.getLayoutParams());
        }
        textView.setTextColor(templateTextView.getCurrentTextColor());
        textView.setTypeface(templateTextView.getTypeface());
        textView.setText(codeDomain.getName());
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }

    private View createWebView(WebView templateWebView, CodeDomain codeDomain) {
        WebView webView = new WebView(this);
        //webView.setBackgroundTintList(templateWebView.getBackgroundTintList());
        webView.setPadding(
                templateWebView.getPaddingLeft(),
                templateWebView.getPaddingTop(),
                templateWebView.getPaddingRight(),
                templateWebView.getPaddingBottom()
        );
        webView.loadDataWithBaseURL(
                null,
                "<html><body style=\"font-size: 14px;\">" + codeDomain.getContent() + "<br></body></html>",
                "text/html",
                "UTF-8",
                null
        );
        return webView;
    }

    private List<CodeDomain> getList(Resource<List<CodeDomain>> data) {
        if (data == null) return Collections.emptyList();
        if (data.isSuccess()) return data.getData();
        return Collections.emptyList();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.TOPIC_MODEL_NAME, topic);
        outState.putSerializable(Constants.SECTION_MODEL_NAME, section);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topic = (TopicDomain) savedInstanceState.getSerializable(Constants.TOPIC_MODEL_NAME);
        section = (SectionDomain) savedInstanceState.getSerializable(Constants.SECTION_MODEL_NAME);
    }
}