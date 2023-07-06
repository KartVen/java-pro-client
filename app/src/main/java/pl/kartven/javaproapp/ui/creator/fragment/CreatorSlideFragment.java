package pl.kartven.javaproapp.ui.creator.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.FragmentCreatorSlideBinding;
import pl.kartven.javaproapp.ui.creator.CreatorActivity;
import pl.kartven.javaproapp.ui.creator.CreatorViewModel;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CreatorSlideFragment extends BaseFragment implements CreatorActivity.CreatorEventListener {
    private CreatorViewModel creatorViewModel;
    private FragmentCreatorSlideBinding binding;
    private CreatorSlideViewModel viewModel;
    private final ExecutorService executor;
    private ActivityResultLauncher<Intent> launcher;
    private int previewIndex = 0;
    List<Uri> tempImageUri = new ArrayList<>();

    @Inject
    public CreatorSlideFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorViewModel = getActivityViewModel(CreatorViewModel.class);
        viewModel = initViewModel(CreatorSlideViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatorSlideBinding.inflate(inflater, container, false);

        initActions();
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.creatorSlideAppcbtnSelectImg.setOnClickListener(v -> handleImagePicker());

        AppCompatButton creatorSlideAppcbtnPrev = binding.creatorSlideAppcbtnPrev;
        AppCompatButton creatorSlideAppcbtnNext = binding.creatorSlideAppcbtnNext;

        creatorSlideAppcbtnPrev.setOnClickListener(v -> {
            if (previewIndex > 0) {
                previewIndex--;
                binding.creatorSlideImageswitcherSlide.setImageURI(viewModel.getImageUris().get(previewIndex));
                creatorSlideAppcbtnPrev.setClickable(true);
            } else {
                creatorSlideAppcbtnPrev.setClickable(false);
            }
        });
        creatorSlideAppcbtnNext.setOnClickListener(v -> {
            if (previewIndex < viewModel.getImageUris().size() - 1) {
                previewIndex++;
                binding.creatorSlideImageswitcherSlide.setImageURI(viewModel.getImageUris().get(previewIndex));
                creatorSlideAppcbtnPrev.setClickable(true);
            } else {
                creatorSlideAppcbtnNext.setClickable(false);
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        if (result.getData().getClipData() != null) {
                            ClipData clipData = result.getData().getClipData();
                            Log.i(getClass().getSimpleName(), String.valueOf(clipData.getItemCount()));
                            tempImageUri.clear();
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                tempImageUri.add(clipData.getItemAt(i).getUri());
                            }
                            viewModel.setImageUris(tempImageUri);
                            binding.creatorSlideImageswitcherSlide.setImageURI(viewModel.getImageUris().get(0));
                        } else if (result.getData().getData() != null) {
                            viewModel.setImageUris(new ArrayList<>(
                                    Collections.singletonList(result.getData().getData())
                            ));
                            binding.creatorSlideImageswitcherSlide.setImageURI(viewModel.getImageUris().get(previewIndex));
                            creatorSlideAppcbtnPrev.setClickable(false);
                            creatorSlideAppcbtnNext.setClickable(false);
                        }
                    }
                });
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding.creatorSlideImageswitcherSlide.setFactory(() -> new ImageView(requireContext()));
    }

    private void handleImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, getString(R.string.label_select_images)));
    }

    @Override
    public void handleSave(Mode saveMode) {
        if (Objects.isNull(viewModel.getImageUris()) || viewModel.getImageUris().isEmpty()) {
            ActivityUtils.showToast(requireActivity(), getString(R.string.slides_are_required));
            //creatorViewModel.setSaveStepCorrect(false);
            return;
        }

        switch (saveMode) {
            case NEW:
                executor.execute(() -> {
                    Resource<Void> voidResource = viewModel.postSlidesOfTopic(
                            creatorViewModel.getTopicDomain().getId(), tempImageUri
                    );
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