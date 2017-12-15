package com.example.maojianhui.fragmentbestpractice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by maojianhui on 2017/12/14.
 */

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }
    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout=view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText=(TextView)view.findViewById(R.id.news_title);
        TextView newsTitleContent=(TextView)view.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);//刷新新闻标题
        newsTitleContent.setText(newsContent);//刷新新闻的内容
    }
}
