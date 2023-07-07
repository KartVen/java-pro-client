package pl.kartven.javaproapp.ui.topic.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.utils.creator.UIElementCreator;
import pl.kartven.javaproapp.utils.listener.RVItemExpandListener;

public class SectionCodesListAdapter extends RecyclerView.Adapter<SectionCodesListAdapter.ViewHolder> {

    private final ExecutorService executor;
    private final Handler handler;
    private List<SectionDomainExpandable> data;
    protected RVItemExpandListener<SectionDomain, List<CodeDomain>> rvItemExpandListener;
    private Context context;

    public SectionCodesListAdapter(List<SectionDomain> data, ExecutorService executor) {
        this.data = map(data);
        this.executor = executor;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    private List<SectionDomainExpandable> map(List<SectionDomain> data) {
        return data.stream()
                .map(sectionDomain ->
                        new SectionDomainExpandable(sectionDomain.getId(), sectionDomain.getName(), false)
                )
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater
                .from(context)
                .inflate(R.layout.fragment_codes_rv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SectionDomainExpandable item = data.get(position);
        holder.setId(item.getId());
        holder.setName(item.getName());
        holder.itemView.setOnClickListener(view -> {
            if (rvItemExpandListener == null) return;
            if (item.isExpandable) {
                holder.expandableArrowImageView.setImageResource(R.drawable.common_expand_more_src);
                holder.expandableLayout.setVisibility(View.GONE);
            } else {
                executor.execute(() -> {
                    List<CodeDomain> codeDomains = rvItemExpandListener.onExpand(item, position);
                    handler.post(() -> {
                        if (item.codesList == null || item.codesList.size() != codeDomains.size()) {
                            item.setCodesList(codeDomains);
                            createCodesElements(holder.expandableLinear, item);
                        }
                        holder.expandableArrowImageView.setImageResource(R.drawable.common_expand_less_src);
                        holder.expandableLayout.setVisibility(View.VISIBLE);
                    });
                });
            }
            item.isExpandable = !item.isExpandable;
        });
    }

    private void createCodesElements(LinearLayout expandableParent, SectionDomainExpandable item) {
        expandableParent.removeAllViews();
        item.getCodesList().forEach(codeDomain -> {
            expandableParent.addView(UIElementCreator.createTextView(context, codeDomain.getName()));
            expandableParent.addView(UIElementCreator.createWebView(context, codeDomain.getContent()));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(final List<SectionDomain> list) {
        this.data.clear();
        this.data = new ArrayList<>(map(list));
        notifyDataSetChanged();
    }

    public void setItemExpandEvent(RVItemExpandListener<SectionDomain, List<CodeDomain>> rvItemExpandListener) {
        this.rvItemExpandListener = rvItemExpandListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Long id;
        TextView name;
        RelativeLayout expandableLayout;
        ImageView expandableArrowImageView;
        LinearLayout expandableLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.topic_d_codes_rv_tv_name);
            expandableLayout = itemView.findViewById(R.id.topic_d_codes_rv_expandable_layout);
            expandableArrowImageView = itemView.findViewById(R.id.topic_d_codes_rv_expandable_arrow);
            expandableLinear = itemView.findViewById(R.id.topic_d_codes_rv_linear);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String title) {
            this.name.setText(title);
        }
    }

    private static class SectionDomainExpandable extends SectionDomain {
        private boolean isExpandable;
        private List<CodeDomain> codesList;

        public SectionDomainExpandable(Long id, String name, boolean isExpandable) {
            super(id, name);
            this.isExpandable = isExpandable;
            codesList = null;
        }

        public boolean isExpandable() {
            return isExpandable;
        }

        public void setExpandable(boolean expandable) {
            isExpandable = expandable;
        }

        public List<CodeDomain> getCodesList() {
            return codesList;
        }

        public void setCodesList(List<CodeDomain> codesList) {
            this.codesList = codesList;
        }
    }
}
