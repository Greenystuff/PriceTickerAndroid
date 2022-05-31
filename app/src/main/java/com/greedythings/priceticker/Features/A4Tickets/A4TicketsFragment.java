package com.greedythings.priceticker.Features.A4Tickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.greedythings.priceticker.Features.WallTickets.WallTicketsPresenter;
import com.greedythings.priceticker.R;
import com.greedythings.priceticker.Utils.ProductsAdapter;
import com.greedythings.priceticker.Utils.SharedPrefsHelper;

import butterknife.ButterKnife;


public class A4TicketsFragment extends Fragment {

    public A4TicketsFragment() {
        // Required empty public constructor
    }

    public static A4TicketsFragment newInstance() {
        return new A4TicketsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a4_tickets, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}