package pl.kartven.javaproapp.ui.creator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.FragmentCreatorQuizBinding;
import pl.kartven.javaproapp.ui.creator.CreatorActivity;
import pl.kartven.javaproapp.ui.creator.CreatorViewModel;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CreatorQuizFragment extends BaseFragment implements CreatorActivity.CreatorEventListener {
    private CreatorViewModel creatorViewModel;
    private FragmentCreatorQuizBinding binding;
    private CreatorQuizViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public CreatorQuizFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorViewModel = getActivityViewModel(CreatorViewModel.class);
        viewModel = initViewModel(CreatorQuizViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatorQuizBinding.inflate(inflater, container, false);
        initActions();
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initActions() {
        super.initActions();
    }

    @Override
    protected void initContent() {
        super.initContent();
    }

    @Override
    public void handleSave(Mode saveMode) {
        String name = binding.creatorQuizEtName.getText().toString();
        String desc = binding.creatorQuizEtDesc.getText().toString();

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(desc)) {
            ActivityUtils.showToast(requireActivity(), getString(R.string.fields_are_required));
            creatorViewModel.setSaveStepCorrect(false);
            return;
        }

        creatorViewModel.setSaveStepCorrect(true);

        switch (saveMode) {
            case NEW:
                executor.execute(() -> {
                    Resource<Void> voidResource = viewModel.postQuizOfTopic(creatorViewModel.getTopicDomain().getId(), name, desc);
                    requireActivity().runOnUiThread(() -> {
                        if (voidResource.isSuccess()) {
                            ActivityUtils.showToast(requireActivity(), getString(R.string.resource_added_successfully));
                            creatorViewModel.setSaveStepCorrect(true);
                        } else {
                            ActivityUtils.showToast(requireActivity(), voidResource.getMessage());
                            creatorViewModel.setSaveStepCorrect(false);
                        }
                    });
                });
                break;
            case EDIT:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}