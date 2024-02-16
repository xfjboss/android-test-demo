package com.example.virtuesaccumulator.activity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.activity.adapter.StoreRVAdapter;
import com.example.virtuesaccumulator.model.VAItem;

import java.util.ArrayList;

public class StoreFragment extends Fragment {

    public static String FRAGMENT_DATA = "fragment_data";
    private RecyclerView recyclerView;
    private StoreRVAdapter adapter;

    public static StoreFragment getFragment(ArrayList<? extends VAItem> data) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FRAGMENT_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.virtues_store_fragment, container, false);
        assert getArguments() != null;
        ArrayList<? extends VAItem> data = getArguments().getParcelableArrayList(FRAGMENT_DATA);
        recyclerView = view.findViewById(R.id.store_recyclerView);
        adapter = new StoreRVAdapter();
        adapter.setData(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
