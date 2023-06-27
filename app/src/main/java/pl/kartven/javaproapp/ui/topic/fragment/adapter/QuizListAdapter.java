package pl.kartven.javaproapp.ui.topic.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.utils.listener.RVItemClickListener;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder> {

    private List<QuizDomain> data;
    protected RVItemClickListener<QuizDomain> rvItemClickListener;
    private Context context;

    public QuizListAdapter(List<QuizDomain> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.topic_d_quizzes_rv_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizDomain item = data.get(position);
        holder.setId(item.getId());
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
    public void updateList(final List<QuizDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setItemClicked(RVItemClickListener<QuizDomain> rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Long id;
        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_d_quizzes_rv_tv_title);
            description = itemView.findViewById(R.id.topic_d_quizzes_rv_tv_desc);
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
