package com.greedythings.priceticker;

import com.greedythings.priceticker.Base.BasePresenter;
import com.greedythings.priceticker.Base.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void ServerClosed(String txtClosedServer);
        void ConnexionStatus(boolean connexionSucced);
        void SetMainLayoutVisibility(boolean ShowMainPage);

    }
    interface Presenter extends BasePresenter {
        void RunServer(String AdresseIp, String Port);
        void stopClient();
    }
}
