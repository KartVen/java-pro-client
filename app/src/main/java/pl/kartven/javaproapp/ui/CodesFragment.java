package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.util.Constants;

public class CodesFragment extends Fragment {
    
    private FragmentCodesBinding binding;
    private TopicDomain topic;

    public CodesFragment() {
        // Required empty public constructor
    }

    public static CodesFragment newInstance(TopicDomain topic) {
        CodesFragment fragment = new CodesFragment();
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
        binding = FragmentCodesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}