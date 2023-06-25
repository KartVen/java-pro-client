package pl.kartven.javaproapp.ui.topic.link;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityLinkViewBinding;
import pl.kartven.javaproapp.utils.base.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;

@AndroidEntryPoint
public class LinkViewActivity extends BaseActivity {

    private ActivityLinkViewBinding binding;
    private LinkDomain linkDomain;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLinkViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        linkDomain = Option.of(savedInstanceState)
                .map(bundle -> (LinkDomain) bundle.getSerializable(Constant.Extra.LINK_MODEL))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (LinkDomain) bundle.getSerializable(Constant.Extra.LINK_MODEL))
                                .getOrNull()
                );

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.linkViewHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding.linkViewTvHeaderName.setText(String.valueOf(linkDomain.getName()));
        binding.linkViewTvHeaderDesc.setText(String.valueOf(linkDomain.getContent()));

        WebView linkViewWebView = binding.linkViewWebView;
        linkViewWebView.setWebViewClient(new WebViewClient());
        linkViewWebView.loadUrl(String.valueOf(linkDomain.getContent()));
    }
}