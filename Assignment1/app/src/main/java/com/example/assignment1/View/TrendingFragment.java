package com.example.assignment1.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.R;
import com.example.assignment1.TrendingContract;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class TrendingFragment extends Fragment implements TrendingContract.View {

    private TrendingContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    public TrendingFragment() {
        // Requires empty public constructor
    }

    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stars:
                break;
            case R.id.menu_name:
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setPresenter(TrendingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showRepos(List<Repo> repos) {


    }

    @Override
    public void showLoadingReposError() {

    }

    @Override
    public void showLoadingProgress() {

    }

    public interface RepoItemListener {
        void onRepoClick(Repo clickedTask);
    }

    private class TrendingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Repo mRepo;

        private int ViewType;

        private TextView author;
        private TextView name;
        private ImageView avatar;


        public TrendingHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            ViewType = viewType;
        }

        public TrendingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_selected, parent, false));
            itemView.setOnClickListener(this);
            author = itemView.findViewById(R.id.crime_title);
            name = itemView.findViewById(R.id.crime_date);
        }

        public void bind(Repo repo) {
            mRepo = repo;

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mRepo.getTitle()+" clicked!",Toast.LENGTH_SHORT).show();
        }
    }

    private class TrendingAdapter extends RecyclerView.Adapter {

        private List<Repo> mRepos;
        private int opened = -1;

        public TrendingAdapter(List<Repo> crimes) {
            mRepos = crimes;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            if ( viewType == 2 )
                viewHolder = new SelectedHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false));
            else if ( viewType == 1 )
                viewHolder = new UnselectedHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unselected, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mRepos.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        class UnselectedHolder extends RecyclerView.ViewHolder {
            private TextView author;
            private TextView name;
            private ImageView avatar;

            public UnselectedHolder(@NonNull View itemView) {
                super(itemView);
                author = itemView.findViewById(R.id.tv_author_selected);
                name = itemView.findViewById(R.id.tv_name_selected);
                avatar = itemView.findViewById(R.id.civ_avatar_selected);
            }
        }

        class SelectedHolder extends RecyclerView.ViewHolder {
            private TextView author;
            private TextView name;
            private TextView detail;
            private ImageView avatar;
            private TextView lanuage;
            private TextView stars;
            private TextView forks;
            private ImageView lanuageColor;

            public SelectedHolder(@NonNull View itemView) {
                super(itemView);
                author = itemView.findViewById(R.id.tv_author_selected);
                name = itemView.findViewById(R.id.tv_name_selected);
                detail = itemView.findViewById(R.id.tv_detail_selected);
                lanuage = itemView.findViewById(R.id.tv_language_selected);
                stars = itemView.findViewById(R.id.tv_stars_selected);
                forks = itemView.findViewById(R.id.tv_forks_selected);
                avatar = itemView.findViewById(R.id.civ_avatar_selected);
                lanuageColor = itemView.findViewById(R.id.iv_language_selected);
            }
        }

    }


    public static class KeepOneH <VH extends RecyclerView.ViewHolder> {
        //    opened为-1表示所有item是关闭状态，open为pos值的表示pos位置的item为展开的状态
        private int _opened = -1;
        public void bind(VH holder, int pos) {
            if (pos == _opened) {
                holder.get
            } else {

            }
        }

        @SuppressWarnings("unchecked")
//        响应点击事件的方法
        public void toggle(VH holder) {
//            如果点击的就是开着的item，就关闭该item并把opened置-1
            if (_opened == holder.getPosition()) {
                _opened = -1;
//                关闭expandView 有动画
            }
//            如果点击其他本来关闭着的item，则把opened值换成当前pos，把之前开的item给关掉
            else {
                int previous = _opened;
                _opened = holder.getPosition();

//                用动画关闭之前的item
                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);
            }
        }
    }


}
