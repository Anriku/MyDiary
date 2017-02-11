package com.anrikuwen.mydiary.diaryfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryProjectFragment extends Fragment {

    private List<DiaryData> diaryDatas;
    private View view;
    private DiaryProjectRecAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.diary_project_fragment,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diaryDatas = DataSupport.findAll(DiaryData.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.diary_project_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DiaryProjectRecAdapter(diaryDatas,view.getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
