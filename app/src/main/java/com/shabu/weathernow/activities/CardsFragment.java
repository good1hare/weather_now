package com.shabu.weathernow.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.shabu.weathernow.BuildConfig;
import com.shabu.weathernow.R;
import com.shabu.weathernow.adapters.CardsListAdapter;
import com.shabu.weathernow.models.WeatherCard;
import com.shabu.weathernow.rest.ApiService;
import com.shabu.weathernow.sql.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CardsFragment extends Fragment {

    private RelativeLayout mRelativeView;
    TextView tvNoCards;
    private List<WeatherCard> mCardsList;
    private Context mContext;
    private View mViewCards;
    private ListView mListViewCards;
    List<String> cities;
    ApiService mApiService = new ApiService();
    AppDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        mViewCards = inflater.inflate(R.layout.fragment_cards, container, false);
        tvNoCards = mViewCards.findViewById(R.id.text_no_cards);
        initComponents();
        return mViewCards;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @SuppressLint("CheckResult")
    public void initComponents() {
        db = Room.databaseBuilder(mContext, AppDatabase.class, "weather_cards-database").allowMainThreadQueries().build();
        mCardsList = new ArrayList<>();
        mListViewCards = (ListView) mViewCards.findViewById(R.id.lv_cards);
        mRelativeView= (RelativeLayout) mViewCards.findViewById(R.id.relative_container);
        cities = new ArrayList<>();
        cities = db.getWeatherCardDao().getAllName();
        if (cities.isEmpty()) {
            cities.add("Казань");
            cities.add("Набережные Челны");
            cities.add("Елабуга");
        }
        getAllCards();
    }

    @SuppressLint("CheckResult")
    private void getAllCards() {
        if(hasConnection(mContext)){
            db.clearAllTables();
        }

        int size = cities.size();
        for(int i = 0; i < size; i++){
            int finalI = i;
            mApiService
                    .get_current_weather(BuildConfig.ACCESS_KEY, cities.get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(card -> {
                        insertInBD(card,finalI == size - 1 ? true : false, cities.get(finalI));
                    }, throwable -> {
                        throwable.printStackTrace();
                        toastNotInternet();
                        showOfDB();
                        return;
                    });
        }
    }

    @SuppressLint("CheckResult")
    public void insertInBD(WeatherCard card, boolean flagLastStage, String city) throws InterruptedException {
        db.getWeatherCardDao().insert(card);
        mCardsList = db.getWeatherCardDao().getAllCards();
        if(flagLastStage){
            populateAdapter();
        }

    }

    @SuppressLint("CheckResult")
    public void showOfDB() throws InterruptedException {
        mCardsList = db.getWeatherCardDao().getAllCards();
        populateAdapter();

    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    //показываем список
    public void populateAdapter() throws InterruptedException {
        //Thread.sleep(1000);
        if (getActivity() != null) {
            if (mContext != null) {
                tvNoCards.setVisibility(View.GONE);
                CardsListAdapter cardsListAdapter = new CardsListAdapter(mCardsList, getActivity());
                mListViewCards.setAdapter(cardsListAdapter);
                mListViewCards.requestLayout();
            }
        }
    }

    public void showAlertForAddCity() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        final EditText editText = new EditText(mContext);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setTitle("Введите название города");

        alert.setView(editText);

        alert.setPositiveButton("Добавить", (dialog, whichButton) -> {
            if (editText.getText().toString().isEmpty()){
                toastError();
                dialog.dismiss();
            }else{
                //делаем что надо
                cities.add(editText.getText().toString());
                getAllCards();
            }
        });

        alert.setNegativeButton("Отмена", (dialog, whichButton) -> {
            dialog.dismiss();
        });

        alert.show();
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public void toastError(){
        Toast.makeText(mContext, "Ой, вы сделали что-то не так :(", Toast.LENGTH_SHORT).show();
    }

    public void toastNotInternet(){
        Toast.makeText(mContext, "Ой, вы забыли включить интернет", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllCards();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}