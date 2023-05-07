package pl.kartven.javaproapp.ui.lecture.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.ui.base.RangeSelectionAdapter;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.util.RVItemClicked;

public class LectureRangeSelectionAdapter extends RangeSelectionAdapter<LectureListItemDetails> {

    public LectureRangeSelectionAdapter(List<LectureListItemDetails> data) {
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

