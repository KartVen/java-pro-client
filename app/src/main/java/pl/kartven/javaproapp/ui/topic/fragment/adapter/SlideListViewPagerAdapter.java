package pl.kartven.javaproapp.ui.topic.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Objects;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;

public class SlideListViewPagerAdapter extends PagerAdapter {
    public static final int ITEMS_PER_PAGE = 4;
    private Integer page = 0;
    private final List<SlideDomain> data;
    private final LayoutInflater mLayoutInflater;

    public SlideListViewPagerAdapter(Context context, List<SlideDomain> data) {
        this.data = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public Integer getPage() {
        return ++page;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addNewItems(final List<SlideDomain> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.topic_d_slides_vp_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.topic_d_slides_rv_image_view);
        //imageView.setImageResource(data.get(position));
        SlideDomain slide = data.get(position);
        Option.of(slide.getImageBytes())
                .map(bytes -> BitmapFactory.decodeByteArray(bytes, 0, bytes.length))
                .peek(imageView::setImageBitmap);
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    public static abstract class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        private boolean isFull = false;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            System.out.println(position);
            SlideListViewPagerAdapter adapter = getAdapter();
            if (!isFull && adapter.getCount() >= 1 && position >= adapter.getCount() - 1) {
                List<SlideDomain> moreItems = loadMoreItems();
                if (moreItems.isEmpty()) {
                    isFull = true;
                    return;
                }
                adapter.addNewItems(moreItems);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @NonNull
        public abstract SlideListViewPagerAdapter getAdapter();

        @NonNull
        public abstract List<SlideDomain> loadMoreItems();
    }
}
