package pl.kartven.javaproapp.ui.adapter;

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
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.util.RVItemClicked;

public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.ViewHolder> {

    private List<SectionDomain> data;
    protected RVItemClicked<SectionDomain> rvItemClicked;
    private Context context;

    public SectionListAdapter(List<SectionDomain> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.section_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SectionDomain item = data.get(position);
        holder.setId(item.getId());
        holder.setName(item.getName());
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
    public void updateList(final List<SectionDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setItemClicked(RVItemClicked<SectionDomain> rvItemClicked) {
        this.rvItemClicked = rvItemClicked;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Long id;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.section_item_text_view_1);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String title) {
            this.name.setText(title);
        }
    }
}
