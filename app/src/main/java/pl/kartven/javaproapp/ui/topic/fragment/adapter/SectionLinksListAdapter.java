package pl.kartven.javaproapp.ui.topic.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.utils.creator.UIElementCreator;
import pl.kartven.javaproapp.utils.listener.RVItemClickListener;
import pl.kartven.javaproapp.utils.listener.RVItemExpandListener;

public class SectionLinksListAdapter extends RecyclerView.Adapter<SectionLinksListAdapter.ViewHolder> {

    private List<SectionDomainExpandable> data;
    protected RVItemExpandListener<SectionDomain, List<LinkDomain>> rvItemExpandListener;
    private Context context;
    protected RVItemClickListener<LinkDomain> rvNestedItemClick;
    protected SectionLinksNestedListAdapter nestedAdapter;

    public SectionLinksListAdapter(List<SectionDomain> data) {
        this.data = map(data);
        nestedAdapter = new SectionLinksNestedListAdapter(Collections.emptyList());
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
                .inflate(R.layout.topic_d_links_rv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SectionDomainExpandable item = data.get(position);
        holder.setId(item.getId());
        holder.setName(item.getName());
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.itemView.setOnClickListener(view -> {
            if (rvItemExpandListener == null) return;
            if (item.isExpandable) {
                holder.expandableArrowImageView.setImageResource(R.drawable.common_expand_more_src);
                holder.expandableLayout.setVisibility(View.GONE);
            } else {
                List<LinkDomain> linkDomains = rvItemExpandListener.onExpand(item, position);
                if (item.linksList == null || item.linksList.size() != linkDomains.size()) {
                    item.setLinksList(linkDomains);
                    nestedAdapter.updateList(linkDomains);
                    holder.nestedRecyclerView.setAdapter(nestedAdapter);
                }
                holder.expandableArrowImageView.setImageResource(R.drawable.common_expand_less_src);
                holder.expandableLayout.setVisibility(View.VISIBLE);
            }
            item.isExpandable = !item.isExpandable;
        });
    }

    private void createLinksElements(LinearLayout expandableParent, SectionDomainExpandable item) {
        expandableParent.removeAllViews();
        item.getLinksList().forEach(codeDomain -> {
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

    public void setItemExpandEvent(RVItemExpandListener<SectionDomain, List<LinkDomain>> rvItemExpandListener) {
        this.rvItemExpandListener = rvItemExpandListener;
    }

    public void setNestedItemClickEvent(RVItemClickListener<LinkDomain> rvItemClickListener) {
        nestedAdapter.setItemClicked(rvItemClickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Long id;
        TextView name;
        RelativeLayout expandableLayout;
        ImageView expandableArrowImageView;
        RecyclerView nestedRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.topic_d_links_rv_tv_name);
            expandableLayout = itemView.findViewById(R.id.topic_d_links_rv_expandable_layout);
            expandableArrowImageView = itemView.findViewById(R.id.topic_d_links_rv_expandable_arrow);
            nestedRecyclerView = itemView.findViewById(R.id.topic_d_links_rv_rv);
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
        private List<LinkDomain> linksList;

        public SectionDomainExpandable(Long id, String name, boolean isExpandable) {
            super(id, name);
            this.isExpandable = isExpandable;
            linksList = null;
        }

        public boolean isExpandable() {
            return isExpandable;
        }

        public void setExpandable(boolean expandable) {
            isExpandable = expandable;
        }

        public List<LinkDomain> getLinksList() {
            return linksList;
        }

        public void setLinksList(List<LinkDomain> linksList) {
            this.linksList = linksList;
        }
    }
}
