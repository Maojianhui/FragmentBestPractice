package com.example.maojianhui.fragmentbestpractice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by maojianhui on 2017/12/14.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView newsTitleRecycleView=(RecyclerView)view.findViewById
                (R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        newsTitleRecycleView.setLayoutManager(layoutManager);
        NewsAdapter adapter=new NewsAdapter(getNews());
        newsTitleRecycleView.setAdapter(adapter);
        return view;
    }
    private List<News>getNews(){
        List<News>newsList=new ArrayList<>();
        for(int i=1;i<=50;i++){
            News news=new News();
            news.setTitle("This is new title "+i);
            news.setContent(getRandomLengthContent("This is new content"+i+"."));
            newsList.add(news);
        }
        return newsList;
    }
    private String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout)!=null){
            isTwoPane=true;//可以找到news_content_layout布局时，为双页模式
        }else{
            isTwoPane=false;//找不到news_content_layout布局时，为单页模式
        }
    }
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<News> mNewsList;
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            public ViewHolder(View view){
                super(view);
                newsTitleText=(TextView)view.findViewById(R.id.news_title);
            }
        }
        public NewsAdapter(List<News> newsList){
            mNewsList=newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news=mNewsList.get(holder.getAdapterPosition());
                    if(isTwoPane){
                        //如果是双页模式，则刷新newsContentFragment中的内容
                        NewsContentFragment newsContentFragment=
                                (NewsContentFragment)getFragmentManager()
                                .findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else{
                        //如果是单页模式，则直接启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(),
                                news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news=mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }//对RecycleView子项的数据进行赋值，通过posotion参数获得当前项的news实例，
        // 然后再将数据设置到ViewHolder的TextView中


        @Override
        public int getItemCount() {
            return mNewsList.size();//告诉我们RecycleView有多少子项，直接返回数据源的长度
        }
    }
}
