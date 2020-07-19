package com.shabu.weathernow.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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
        db = Room.databaseBuilder(mContext, AppDatabase.class, "weather_cards-database").build();
        mCardsList = new ArrayList<>();
        mListViewCards = (ListView) mViewCards.findViewById(R.id.lv_cards);
        mRelativeView= (RelativeLayout) mViewCards.findViewById(R.id.relative_container);
        cities = new ArrayList<>();
        //cites = db.getWeatherCardDao().getAllName();
        /*db.getWeatherCardDao().getAllName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    this.cities = cities;
                    if (cities.isEmpty()) {
                        cities.add("Казань");
                        cities.add("Набережные Челны");
                        cities.add("Елабуга");
                    }
                    getAllCards();
                }, throwable -> {
                    throwable.printStackTrace();
                    toastError();
                });*/
        cities.add("Казань");
        cities.add("Набережные Челны");
        cities.add("Елабуга");
        getAllCards();
    }

    @SuppressLint("CheckResult")
    private void getAllCards() {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.clearAllTables();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        int size = cities.size();
        for(int i = 0; i < size; i++){
            int finalI = i;
            mApiService
                    .get_current_weather(BuildConfig.ACCESS_KEY, cities.get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(card -> {
                        insertInBD(card,finalI == size - 1 ? true : false );
                        //db.getWeatherCardDao().insert(card);
                        //addCardInList(card);
                        //populateAdapter();
                    }, throwable -> {
                        throwable.printStackTrace();
                        toastNotInternet();
                    });
        }

        //populateAdapter();
    }

    @SuppressLint("CheckResult")
    public void insertInBD(WeatherCard card, boolean flag){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.getWeatherCardDao().insert(card);
                mCardsList = db.getWeatherCardDao().getAllCards();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(flag){
                            populateAdapter();
                        }
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    //формируем список
    public void addCardInList(WeatherCard card) {
        mCardsList.add(card);
    }

    //показываем список
    public void populateAdapter() {
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

    public void toastError(){
        Toast.makeText(mContext, "Ой, вы сделали что-то не так :(", Toast.LENGTH_SHORT).show();
    }

    public void toastNotInternet(){
        Toast.makeText(mContext, "Ой, вы забыли включить интернет", Toast.LENGTH_SHORT).show();
    }

}