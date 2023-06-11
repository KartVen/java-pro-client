package pl.kartven.javaproapp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.util.RVItemClicked;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private List<TopicDomain> data;
    protected RVItemClicked<TopicDomain> rvItemClicked;
    private Context context;

    public TopicListAdapter(List<TopicDomain> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.topic_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopicDomain item = data.get(position);
        holder.setId(item.getId());
        holder.setName(item.getName());
        holder.setDescription(item.getDescription());
        holder.itemView.setOnClickListener(view -> {
            if (rvItemClicked != null) {
                rvItemClicked.onClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(final List<TopicDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setItemClicked(RVItemClicked<TopicDomain> rvItemClicked) {
        this.rvItemClicked = rvItemClicked;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Long id;
        TextView name, description;
        ImageView logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.topic_item_text_view_1);
            description = itemView.findViewById(R.id.topic_item_text_view_2);
            logo = itemView.findViewById(R.id.topic_item_image_view_1);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String title) {
            this.name.setText(title);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }
    }
}
