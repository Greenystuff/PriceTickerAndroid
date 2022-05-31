package com.greedythings.priceticker.Features.RailTickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greedythings.priceticker.Models.ProductData;
import com.greedythings.priceticker.R;
import com.greedythings.priceticker.Utils.ProductsAdapter;
import com.greedythings.priceticker.Utils.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RailTicketsFragment extends Fragment implements RailTicketsContract.View{

    private RailTicketsContract.Presenter presenter;

    @BindView(R.id.txtIdJajaRail)
    EditText txtIdJaja;
    @BindView(R.id.btnValiderIdRail)
    Button btnValiderId;
    @BindView(R.id.layoutItemsRail)
    ConstraintLayout layoutItems;
    @BindView(R.id.viewProductsDetails)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProductsAdapter productsAdapter = null;
    List<ProductData> productsList = new ArrayList<>();

    public RailTicketsFragment() {
        // Required empty public constructor
    }


    public static RailTicketsFragment newInstance() {
        return new RailTicketsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rail_tickets, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new RailTicketsPresenter(this, new SharedPrefsHelper(getActivity())));


        productsAdapter = new ProductsAdapter(productsList);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(productsAdapter);


        btnValiderId.setOnClickListener(view1 -> {

            if(!txtIdJaja.getText().toString().isEmpty()) {
                presenter.listenMessagesFromServer();
                presenter.sendMessageToServer("Rail;" + txtIdJaja.getText().toString().trim());
            }else {
                CharSequence text = "Veuillez renseigner un ID Jaja";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(this.getContext(), text, duration);
                toast.show();
            }
        });

    }

    public void setPresenter(RailTicketsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setProductsList(List<ProductData> productList) {
        productsAdapter = new ProductsAdapter(productList);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(productsAdapter);

        Log.e("Liste de produits", String.valueOf(productList.size()));
    }

}