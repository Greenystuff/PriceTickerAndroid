package com.greedythings.priceticker.Features.RailTickets;

import com.greedythings.priceticker.Base.BasePresenter;
import com.greedythings.priceticker.Base.BaseView;
import com.greedythings.priceticker.Models.ProductData;

import java.util.List;

public interface RailTicketsContract {

    interface View extends BaseView<RailTicketsContract.Presenter> {
        void setProductsList(List<ProductData> productList);
    }

    interface Presenter extends BasePresenter {
        void listenMessagesFromServer();
        void sendMessageToServer(String TextToSend);
    }

}
