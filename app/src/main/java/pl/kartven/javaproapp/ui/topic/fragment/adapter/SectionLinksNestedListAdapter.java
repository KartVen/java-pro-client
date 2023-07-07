package pl.kartven.javaproapp.ui.topic.fragment.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.utils.listener.RVItemClickListener;

public class SectionLinksNestedListAdapter extends RecyclerView.Adapter<SectionLinksNestedListAdapter.ViewHolder> {

    private List<LinkDomain> data;
    protected RVItemClickListener<LinkDomain> rvItemClickListener;

    public SectionLinksNestedListAdapter(List<LinkDomain> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_links_rv_rv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinkDomain item = data.get(position);
        holder.setId(item.getId());
        holder.setTitle(item.getName());
        holder.itemView.setOnClickListener(view -> {
            if (rvItemClickListener != null) {
                rvItemClickListener.onClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(final List<LinkDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setItemClicked(RVItemClickListener<LinkDomain> rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Long id;
        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_d_quizzes_rv_rv_tv_title);
            description = itemView.findViewById(R.id.topic_d_quizzes_rv_rv_tv_desc);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }
    }
}
