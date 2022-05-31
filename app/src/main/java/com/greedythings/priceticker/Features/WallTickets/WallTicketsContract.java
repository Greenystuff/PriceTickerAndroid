package com.greedythings.priceticker.Features.WallTickets;

import com.greedythings.priceticker.Base.BasePresenter;
import com.greedythings.priceticker.Base.BaseView;
import com.greedythings.priceticker.Models.ProductData;

import java.util.List;

public interface WallTicketsContract {

    interface View extends BaseView<Presenter> {
        void setProductsList(List<ProductData> productList);
    }

    interface Presenter extends BasePresenter {
        void listenMessagesFromServer();
        void sendMessageToServer(String TextToSend);
    }

}
