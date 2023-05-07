package pl.kartven.javaproapp.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.util.RVItemClicked;

public abstract class RangeSelectionAdapter<ITEM> extends RecyclerView.Adapter<RangeSelectionAdapter.ViewHolder> {

    private List<ITEM> data;
    protected pl.kartven.javaproapp.util.RVItemClicked<ITEM> RVItemClicked;

    public RangeSelectionAdapter(List<ITEM> data) {
        this.data = data;
    }

    protected final ITEM getItem(int id) {
        return data.get(id);
    }

    @NonNull
    @Override
    public RangeSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.part_range_selection_item, parent, false);
        return new RangeSelectionAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RangeSelectionAdapter.ViewHolder holder, int position) {
        ITEM lecture = setItemsContent(holder, position);
        holder.itemView.setOnClickListener(view -> {
            if (RVItemClicked != null) {
                RVItemClicked.onClick(lecture, position);
            }
        });
    }

    @NonNull
    protected abstract ITEM setItemsContent(@NonNull ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItemClicked(RVItemClicked<ITEM> l) {
        this.RVItemClicked = l;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(final List<ITEM> list) {
        this.data.clear();
        this.data = list;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView topic;
        private final TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.lecture_item_topic);
            description = itemView.findViewById(R.id.lecture_item_desc);
        }

        public void setTopic(String value) {
            topic.setText(value);
        }

        public void setDescription(String value) {
            description.setText(value);
        }

    }

    public interface Builder<ITEM> {
        default void initRecyclerView(){
            initRecyclerView(null);
        }
        <T> void initRecyclerView(Class<T> targetActivityClass);
        RangeSelectionAdapter<ITEM> initAdapterWithData();
    }
}
