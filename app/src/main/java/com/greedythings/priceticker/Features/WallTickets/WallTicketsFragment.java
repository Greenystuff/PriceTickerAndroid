package com.greedythings.priceticker.Features.WallTickets;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greedythings.priceticker.Models.ProductData;
import com.greedythings.priceticker.R;
import com.greedythings.priceticker.TcpClient;
import com.greedythings.priceticker.Utils.ProductsAdapter;
import com.greedythings.priceticker.Utils.SharedPrefsHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WallTicketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WallTicketsFragment extends Fragment implements WallTicketsContract.View{

    @BindView(R.id.txtIdJaja)
    EditText txtIdJaja;
    @BindView(R.id.btnValiderId)
    Button btnValiderId;
    @BindView(R.id.layoutItems)
    ConstraintLayout layoutItems;
    @BindView(R.id.viewProductsDetails)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProductsAdapter productsAdapter;

    public WallTicketsFragment() {
        // Required empty public constructor
    }

    private WallTicketsContract.Presenter presenter;
    List<ProductData> productsList = new ArrayList<>();

    public static WallTicketsFragment newInstance() {
        return new WallTicketsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall_tickets, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new WallTicketsPresenter(this, new SharedPrefsHelper(getActivity())));

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
                presenter.sendMessageToServer("Murale;" + txtIdJaja.getText().toString().trim());
            }else {
                CharSequence text = "Veuillez renseigner un ID Jaja";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(this.getContext(), text, duration);
                toast.show();
            }
        });

    }

    public void setPresenter(WallTicketsContract.Presenter presenter) {
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