package com.example.assignment1.View;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.R;
import com.example.assignment1.TrendingContract;
import com.example.assignment1.utils.ScrollChildSwipeRefreshLayout;
import com.example.assignment1.utils.SetImageViewByURL;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class TrendingFragment extends Fragment implements TrendingContract.View {

    private TrendingContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private View mFailureView;
    private LinearLayout mTrendingView;
    private Button mRetryButton;
    private TrendingAdapter mAdapter;

    public TrendingFragment() {
        // Requires empty public constructor
    }

    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TrendingAdapter(new ArrayList<Repo>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(TrendingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //Set up repositories view
        mRecyclerView = root.findViewById(R.id.rv_repositories);
        mRecyclerView.setAdapter(mAdapter);
        mTrendingView = root.findViewById(R.id.ll_trending);

        //Set up onFaliure view
        mFailureView = root.findViewById(R.id.v_failure);
        mRetryButton = root.findViewById(R.id.btn_retry);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadRepos(false);
            }
        });

        //Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                 root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadRepos(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stars:
                mPresenter.sortByStar();
                break;
            case R.id.menu_name:
                mPresenter.sortByName();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }

        final SwipeRefreshLayout srl = getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showRepos(List<Repo> repos) {
        mAdapter.replaceData(repos);

        mFailureView.setVisibility(View.GONE);
        mTrendingView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showNoInternetConnection() {
        mTrendingView.setVisibility(View.GONE);
        mFailureView.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    private class TrendingHolder extends RecyclerView.ViewHolder {

        private Repo mRepo;
        private int opened = -1;

        private int ViewType;

        private TextView author;
        private TextView name;
        private ImageView avatar;

        private TextView detail;
        private TextView lanuage;
        private TextView starsNum;
        private TextView forksNum;
        private ImageView stars;
        private ImageView forks;
        private ImageView lanuageColor;


        public TrendingHolder(@NonNull View itemView) {
            super(itemView);
        }

        public TrendingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_selected, parent, false));
        }

        public void bind(TrendingHolder holder, int pos) {
            if ( opened == pos ) {
                holder.lanuage.setVisibility(View.VISIBLE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.stars.setVisibility(View.VISIBLE);
                holder.forks.setVisibility(View.VISIBLE);
                holder.lanuageColor.setVisibility(View.VISIBLE);
                holder.starsNum.setVisibility(View.VISIBLE);
                holder.forksNum.setVisibility(View.VISIBLE);
            } else {
                holder.lanuage.setVisibility(View.GONE);
                holder.detail.setVisibility(View.GONE);
                holder.stars.setVisibility(View.GONE);
                holder.forks.setVisibility(View.GONE);
                holder.lanuageColor.setVisibility(View.GONE);
                holder.starsNum.setVisibility(View.GONE);
                holder.forksNum.setVisibility(View.GONE);
            }
        }

    }


    private class TrendingAdapter extends RecyclerView.Adapter<TrendingHolder> implements TrendingItemListener{

        private List<Repo> mRepos;

        public TrendingAdapter(List<Repo> crimes) {
            mRepos = crimes;
        }

        public void replaceData(List<Repo> repos) {
            setList(repos);
            notifyDataSetChanged();
        }

        private void setList(List<Repo> repos) {
            mRepos = checkNotNull(repos);
        }


        @Override
        public TrendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TrendingHolder viewHolder;
            viewHolder = new TrendingHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(TrendingHolder holder, int position) {
            Repo repo = mRepos.get(position);
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.circle_lanuage);
            int BankColor = Color.parseColor(repo.getLanguageColor());
            drawable.setColor(BankColor);
            holder.lanuageColor.setImageDrawable(drawable);
            holder.forks.setImageResource(R.drawable.fork_black);
            holder.stars.setImageResource(R.drawable.star_yellow);

            holder.forksNum.setText(repo.getForks());
            holder.starsNum.setText(repo.getStars());
            holder.name.setText(repo.getName());
            holder.author.setText(repo.getAuthor());
            SetImageViewByURL.setImageToImageView(holder.avatar, repo.getAvatar());
            holder.detail.setText(repo.getDescription());
            holder.lanuage.setText(repo.getLanguage());
        }

        @Override
        public int getItemCount() {
            return mRepos.size();
        }

        @Override
        public void onRepoItemClick(TrendingHolder holder) {

            if ( holder.opened == holder.getPosition() ) {
                holder.opened = -1;
                holder.lanuage.setVisibility(View.GONE);
                holder.detail.setVisibility(View.GONE);
                holder.stars.setVisibility(View.GONE);
                holder.forks.setVisibility(View.GONE);
                holder.lanuageColor.setVisibility(View.GONE);
                holder.starsNum.setVisibility(View.GONE);
                holder.forksNum.setVisibility(View.GONE);
            }
            else {
                int previous = holder.opened;
                holder.opened = holder.getPosition();

                final TrendingHolder oldHolder = (TrendingHolder) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);

                oldHolder.lanuage.setVisibility(View.GONE);
                oldHolder.detail.setVisibility(View.GONE);
                oldHolder.stars.setVisibility(View.GONE);
                oldHolder.forks.setVisibility(View.GONE);
                oldHolder.lanuageColor.setVisibility(View.GONE);
                oldHolder.starsNum.setVisibility(View.GONE);
                oldHolder.forksNum.setVisibility(View.GONE);

                holder.lanuage.setVisibility(View.VISIBLE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.stars.setVisibility(View.VISIBLE);
                holder.forks.setVisibility(View.VISIBLE);
                holder.lanuageColor.setVisibility(View.VISIBLE);
                holder.starsNum.setVisibility(View.VISIBLE);
                holder.forksNum.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface TrendingItemListener {

        void onRepoItemClick(TrendingHolder holder);

    }
}
