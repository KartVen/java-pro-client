package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentLinksBinding;
import pl.kartven.javaproapp.util.Constants;

public class LinksFragment extends Fragment {

    private FragmentLinksBinding binding;
    private static TopicDomain topic;

    public LinksFragment() {
        // Required empty public constructor
    }

    public static LinksFragment newInstance(TopicDomain topic) {
        LinksFragment fragment = new LinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TOPIC_MODEL_NAME, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Try.of(this::getArguments)
                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                .onSuccess(topicDomain -> topic = topicDomain);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}