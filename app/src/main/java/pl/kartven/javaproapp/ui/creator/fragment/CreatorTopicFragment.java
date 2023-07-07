package pl.kartven.javaproapp.ui.creator.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentCreatorTopicBinding;
import pl.kartven.javaproapp.ui.creator.CreatorActivity;
import pl.kartven.javaproapp.ui.creator.CreatorViewModel;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CreatorTopicFragment extends BaseFragment implements CreatorActivity.CreatorEventListener {
    private CreatorViewModel creatorViewModel;
    private FragmentCreatorTopicBinding binding;
    private CreatorTopicViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public CreatorTopicFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorViewModel = getActivityViewModel(CreatorViewModel.class);
        viewModel = initViewModel(CreatorTopicViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatorTopicBinding.inflate(inflater, container, false);
        initActions();
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initActions() {
        super.initActions();
        CheckBox creatorTopicChbContentCheck = binding.creatorTopicChbContentCheck;
        binding.creatorTopicChbContentCheckWrapper.setOnClickListener(v -> {
            boolean checkChecked = creatorTopicChbContentCheck.isChecked();
            creatorTopicChbContentCheck.setChecked(!checkChecked);
            creatorViewModel.setExtendedCreating(!checkChecked);
        });

        creatorTopicChbContentCheck.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            creatorViewModel.setExtendedCreating(isChecked);
        });
    }

    @Override
    protected void initContent() {
        super.initContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleSave(Mode saveMode) {
        String name = binding.creatorTopicEtName.getText().toString();
        String desc = binding.creatorTopicEtDesc.getText().toString();

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(desc)) {
            ActivityUtils.showToast(requireActivity(), getString(R.string.fields_are_required));
            creatorViewModel.setSaveStepCorrect(false);
            return;
        }

        switch (saveMode) {
            case NEW:
                executor.execute(() -> {
                    Resource<TopicDomain> domainResource = viewModel.postTopic(name, desc);
                    requireActivity().runOnUiThread(() -> {
                        if (domainResource.isSuccess()) {
                            ActivityUtils.showToast(requireActivity(), getString(R.string.resource_added_successfully));
                            creatorViewModel.setSaveStepCorrect(true);
                            creatorViewModel.setTopicDomain(domainResource.getData());
                            if (!creatorViewModel.isExtendedCreating() && !binding.creatorTopicChbContentCheck.isChecked()) {
                                Log.d(getClass().getSimpleName(), "isExtendedCreating exit: " + true);
                                ActivityUtils.goToActivity(requireActivity(), MainActivity.class);
                                requireActivity().finish();
                            }
                        } else {
                            ActivityUtils.showToast(requireActivity(), domainResource.getMessage());
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
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}