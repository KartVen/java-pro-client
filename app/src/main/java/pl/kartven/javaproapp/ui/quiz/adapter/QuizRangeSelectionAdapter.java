package pl.kartven.javaproapp.ui.quiz.adapter;

import androidx.annotation.NonNull;

import java.util.List;

import pl.kartven.javaproapp.ui.base.RangeSelectionAdapter;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;

public class QuizRangeSelectionAdapter extends RangeSelectionAdapter<LectureListItemDetails> {

    public QuizRangeSelectionAdapter(List<LectureListItemDetails> data) {
        super(data);
    }

    @NonNull
    @Override
    protected LectureListItemDetails setItemsContent(@NonNull ViewHolder holder, int position) {
        LectureListItemDetails item = getItem(position);
        holder.setTopic(item.getTopic());
        holder.setDescription(item.getDescription());
        return item;
    }
}

