package pl.kartven.javaproapp.ui.lecture.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.FragmentLectureSlideViewItemBinding;
import pl.kartven.javaproapp.util.Extra;

public class LectureSlideViewItemFragment extends Fragment {

    private FragmentLectureSlideViewItemBinding binding;
    private Long id;
    private byte[] content;

    public LectureSlideViewItemFragment() {
        // Required empty public constructor
    }

    public static LectureSlideViewItemFragment newInstance(Long id, byte[] content) {
        LectureSlideViewItemFragment fragment = new LectureSlideViewItemFragment();
        Bundle args = new Bundle();
        args.putLong(Extra.SLIDE_ID, id);
        args.putByteArray(Extra.IMAGE_BYTE, content);
        args.putString("label", "Slide id: " + id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLectureSlideViewItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.label.setText(getString(R.string.app_name));

        String position = Option.of(getArguments())
                .map(args -> args.getString("label"))
                .getOrElse("");
        binding.label.setText(position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Option.of(getArguments())
                .map(args -> {
                    id = args.getLong(Extra.SLIDE_ID);
                    content = args.getByteArray(Extra.IMAGE_BYTE);
                    return null;
                });
    }
}