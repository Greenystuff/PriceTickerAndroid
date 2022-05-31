package com.greedythings.priceticker.Features.RailTickets;

import android.os.AsyncTask;
import android.util.Log;

import com.greedythings.priceticker.Models.ProductData;
import com.greedythings.priceticker.TcpClient;
import com.greedythings.priceticker.Utils.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

public class RailTicketsPresenter implements RailTicketsContract.Presenter{

    private RailTicketsContract.View view;
    private SharedPrefsHelper prefsHelper;
    TcpClient clientTcp;
    List<ProductData> productList = new ArrayList<>();

    public RailTicketsPresenter(RailTicketsContract.View view, SharedPrefsHelper prefsHelper) {
        this.view = view;
        this.prefsHelper = prefsHelper;
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void listenMessagesFromServer() {
        new ConnectTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    @Override
    public void sendMessageToServer(String TextToSend) {
        try {
            clientTcp = TcpClient.getInstance();
            clientTcp.sendMessage(TextToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            try {
                clientTcp = TcpClient.getInstance();
                clientTcp.AttachListener(this::publishProgress);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            SetProductsListView(values);
        }

    }

    private void SetProductsListView(String[] Product) {
        if(!Product[0].equals("Salut petit client !")) {
            String[] datas = Product[0].split(";");
            if(datas[0].equals("Rail")) {
                Log.e("MESSAGE SERVER", datas[1]);
                Log.e("MESSAGE SERVER", datas[2]);
                Log.e("MESSAGE SERVER", datas[3]);
                ProductData productData = new ProductData();
                if (!datas[1].equals("")) {
                    if (datas[2].length() >= 30) {
                        int SpaceIndex = datas[1].indexOf(" ", 15);
                        String CorrectedLibelle = new StringBuilder(datas[2]).insert(SpaceIndex, "\n").toString();
                        productData.setLibelle(CorrectedLibelle);
                    } else {
                        productData.setLibelle(datas[2]);
                    }
                }
                productData.setId(datas[1]);
                productData.setPrix(datas[3]);

                productList.add(productData);

                view.setProductsList(productList);
            }
        }
    }

}
