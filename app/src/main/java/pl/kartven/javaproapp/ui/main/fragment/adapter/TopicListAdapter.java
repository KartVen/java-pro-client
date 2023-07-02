package pl.kartven.javaproapp.ui.main.fragment.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.listener.RVItemClickListener;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private List<TopicDomain> data;
    protected RVItemClickListener<TopicDomain> rvItemClickListener;

    public TopicListAdapter(List<TopicDomain> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_home_rv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopicDomain item = data.get(position);
        holder.setId(item.getId());
        holder.setNo(String.format(Locale.ENGLISH, "Wykład %d", item.getId()));
        holder.setTitle(item.getName());
        holder.setDescription(item.getDescription());
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
    public void updateList(final List<TopicDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setItemClicked(RVItemClickListener<TopicDomain> rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Long id;
        TextView no, title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.main_rv_tv_lec);
            title = itemView.findViewById(R.id.main_rv_tv_title);
            description = itemView.findViewById(R.id.main_rv_tv_desc);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setNo(String no) {
            this.no.setText(no);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }
    }
}
