package pl.kartven.javaproapp.ui.creator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.databinding.FragmentCreatorLinkBinding;
import pl.kartven.javaproapp.ui.creator.CreatorActivity;
import pl.kartven.javaproapp.ui.creator.CreatorViewModel;
import pl.kartven.javaproapp.utils.adapter.OnItemSelectedAdapter;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CreatorLinkFragment extends BaseFragment implements CreatorActivity.CreatorEventListener {
    private CreatorViewModel creatorViewModel;
    private FragmentCreatorLinkBinding binding;
    private CreatorLinkViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public CreatorLinkFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorViewModel = getActivityViewModel(CreatorViewModel.class);
        viewModel = initViewModel(CreatorLinkViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatorLinkBinding.inflate(inflater, container, false);
        initActions();
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.creatorLinkRbtnOldSection.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            binding.creatorLinkSpinnerOldSection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            viewModel.setSelectSectionMode(isChecked ? 0 : viewModel.getSelectSectionMode());
        });

        binding.creatorLinkRbtnNewSection.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            binding.creatorLinkEtNewSection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            viewModel.setSelectSectionMode(isChecked ? 1 : viewModel.getSelectSectionMode());
        });

        binding.creatorLinkSpinnerOldSection.setOnItemSelectedListener(new OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setSectionDomain((SectionDomain) parent.getSelectedItem());
            }
        });
    }

    @Override
    protected void initContent() {
        super.initContent();
        viewModel.setSelectSectionMode(1);
        Spinner spinnerOldSection = binding.creatorLinkSpinnerOldSection;

        executor.execute(() -> {
            Resource<List<SectionDomain>> listResource = viewModel.getSectionsOfTopic(creatorViewModel.getTopicDomain().getId());
            requireActivity().runOnUiThread(() -> {
                if (!listResource.isSuccess()) {
                    ActivityUtils.showToast(requireActivity(), Constant.Expression.INTERNAL_ERROR);
                    return;
                }
                List<SectionDomain> sectionDomainList = listResource.getData();
                if (sectionDomainList.size() == 1)
                    viewModel.setSectionDomain(sectionDomainList.get(0));
                ArrayAdapter<SectionDomain> adapter = new ArrayAdapter<>(
                        requireActivity(), R.layout.fragment_creator__spinner_item, sectionDomainList
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOldSection.setAdapter(adapter);
            });
        });
        binding.creatorLinkRbtnNewSection.setChecked(true);
    }

    @Override
    public void handleSave(Mode saveMode) {
        String name = binding.creatorLinkEtName.getText().toString();
        String link = binding.creatorLinkEtLinkAddress.getText().toString();
        String section = binding.creatorLinkEtNewSection.getText().toString();

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(link) || (viewModel.getSelectSectionMode() == 1 && StringUtils.isEmpty(section))) {
            ActivityUtils.showToast(requireActivity(), getString(R.string.fields_are_required));
            creatorViewModel.setSaveStepCorrect(false);
            return;
        }

        switch (saveMode) {
            case NEW:
                executor.execute(() -> {
                    Resource<Void> voidResource = viewModel.postLinkOfTopic(creatorViewModel.getTopicDomain().getId(), name, link, section);
                    requireActivity().runOnUiThread(() -> {
                        if (voidResource.isSuccess()) {
                            ActivityUtils.showToast(requireActivity(), getString(R.string.resource_added_successfully));
                            creatorViewModel.setSaveStepCorrect(true);
                            creatorViewModel.setExtendedCreating(false);
                        } else {
                            ActivityUtils.showToast(requireActivity(), voidResource.getMessage());
                            creatorViewModel.setSaveStepCorrect(false);
                        }
                        ((CreatorActivity) requireActivity()).handleSave();
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