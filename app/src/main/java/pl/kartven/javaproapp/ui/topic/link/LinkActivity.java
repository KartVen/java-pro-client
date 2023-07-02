package pl.kartven.javaproapp.ui.topic.link;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import java.util.Objects;

import io.vavr.control.Option;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.databinding.ActivityLinkBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;

public class LinkActivity extends BaseActivity {

    private ActivityLinkBinding binding;
    private LinkDomain linkDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBundleVariable(savedInstanceState);
        initActions();
        initContent();
    }

    @Override
    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
        super.initBundleVariable(savedInstanceState);
        linkDomain = getVariableFromBundle(savedInstanceState, bundle ->
                (LinkDomain) bundle.getSerializable(Constant.Extra.LINK_MODEL)
        );

        if (Objects.isNull(linkDomain)) {
            handleError(this::onBackPressed);
        }
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.linkToolbar);
        Option.of(getSupportActionBar()).peek(bar -> bar.setDisplayHomeAsUpEnabled(true));
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding.linkTvHeaderName.setText(String.valueOf(linkDomain.getName()));
        binding.linkTvHeaderDesc.setText(String.valueOf(linkDomain.getContent()));

        WebView linkViewWebView = binding.linkWebView;
        linkViewWebView.setWebViewClient(new WebViewClient());
        linkViewWebView.loadUrl(String.valueOf(linkDomain.getContent()));
    }
}