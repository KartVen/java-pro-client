package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import dagger.hilt.android.HiltAndroidApp;
import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentQuizzesBinding;
import pl.kartven.javaproapp.util.Constants;

public class QuizzesFragment extends Fragment {

    private FragmentQuizzesBinding binding;
    private static TopicDomain topic;

    public QuizzesFragment() {
        // Required empty public constructor
    }

    public static QuizzesFragment newInstance(TopicDomain topic) {
        QuizzesFragment fragment = new QuizzesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}