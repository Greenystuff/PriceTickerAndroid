package com.greedythings.priceticker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.greedythings.priceticker.Utils.SharedPrefsHelper;
import com.greedythings.priceticker.Utils.ViewPagerAdapter;
import com.greedythings.priceticker.Features.RailTickets.RailTicketsFragment;
import com.greedythings.priceticker.Features.WallTickets.WallTicketsFragment;


public class MainActivity extends AppCompatActivity implements MainContract.View{

    LinearLayout connexionLayout;
    TextView txtExplications;
    TextView IpTextBox;
    TextView PortTextBox;
    TextView btnValidateConnexion;
    Button btnDeconnexion;


    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private WallTicketsFragment wallTicketsFragment;
    private RailTicketsFragment railTicketsFragment;

    private MainContract.Presenter presenter;
    private String IpAdress = "";
    private String Port = "";

    private SharedPrefsHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {

        try {
            setPresenter(TcpClient.Init(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefsHelper = new SharedPrefsHelper(this);

        connexionLayout = findViewById(R.id.layoutConnexion);
        txtExplications = findViewById(R.id.txtExplications);
        IpTextBox = findViewById(R.id.txtIpAdress);
        PortTextBox = findViewById(R.id.txtPort);
        btnValidateConnexion = findViewById(R.id.btnValiderParamsReseau);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);

        if(!prefsHelper.getIpServer().isEmpty()) {
            IpTextBox.setText(prefsHelper.getIpServer());
        }
        if(!prefsHelper.getPortServer().isEmpty()) {
            PortTextBox.setText(prefsHelper.getPortServer());
        }

        btnValidateConnexion.setOnClickListener(view -> {

            if(!IpTextBox.getText().toString().equals("") && !PortTextBox.getText().toString().equals("")) {
                IpAdress = IpTextBox.getText().toString();
                Port = PortTextBox.getText().toString();
                txtExplications.setText("Connexion au server en cours...");
                btnValidateConnexion.setEnabled(false);
                prefsHelper.saveIpServer(IpTextBox.getText().toString());
                prefsHelper.savePortServer(PortTextBox.getText().toString());
                presenter.RunServer(IpAdress, Port);

            }else {
                Log.e("Champ incorrect","Veuillez renseigner l'adresse IP et le port");
                CharSequence text = "Veuillez renseigner l'adresse IP et le port";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }

        });

        btnDeconnexion.setOnClickListener(view -> {
            presenter.stopClient();
        });
        appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = findViewById(R.id.tab_layout);

        wallTicketsFragment = new WallTicketsFragment();
        railTicketsFragment = new RailTicketsFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(wallTicketsFragment, "Mural");
        viewPagerAdapter.addFragment(railTicketsFragment, "Rails");
        viewPager.setAdapter(viewPagerAdapter);

        SetMainLayoutVisibility(false);
    }

    @Override
    public void SetMainLayoutVisibility(boolean ShowMainPage) {

        if (ShowMainPage) {
            connexionLayout.setVisibility(View.GONE);
            appBarLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
        }else {
            connexionLayout.setVisibility(View.VISIBLE);
            appBarLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void ServerClosed(String txtClosedServer) {

        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                txtExplications.setText(txtClosedServer);
                SetMainLayoutVisibility(false);
                btnValidateConnexion.setEnabled(true);
            }
        });

    }

    @Override
    public void ConnexionStatus(boolean connexionSucced) {
        if (connexionSucced) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    SetMainLayoutVisibility(true);
                    btnValidateConnexion.setEnabled(true);
                }
            });
        }else {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    txtExplications.setText("Impossible de se connecter...\nVeuillez vérifier l'adresse IP, le port,\nou que PriceTicker est bien lancé sur votre PC.");
                    connexionLayout.setVisibility(View.VISIBLE);
                    btnValidateConnexion.setEnabled(true);
                    SetMainLayoutVisibility(false);
                }
            });

        }

    }
}