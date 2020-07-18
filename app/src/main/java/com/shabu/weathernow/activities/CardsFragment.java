package com.shabu.weathernow.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shabu.weathernow.BuildConfig;
import com.shabu.weathernow.R;
import com.shabu.weathernow.adapters.CardsListAdapter;
import com.shabu.weathernow.models.WeatherCard;
import com.shabu.weathernow.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardsFragment extends Fragment {

    private RelativeLayout mRelativeView;
    TextView tvNoCards;
    private List<WeatherCard> mCardsList;
    private Context mContext;
    private View mViewCards;
    private ListView mListViewCards;
    private FloatingActionButton fabPlus;
    ArrayList<String> cites;
    ApiService mApiService = new ApiService();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        mViewCards = inflater.inflate(R.layout.fragment_cards, container, false);
        tvNoCards = mViewCards.findViewById(R.id.text_no_cards);
        fabPlus = inflater.inflate(R.layout.activity_main, container, false).findViewById(R.id.fab);
        initComponents();
        return mViewCards;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void initComponents() {
        mCardsList = new ArrayList<>();
        cites = new ArrayList<>();
        cites.add("Казань");
        cites.add("Набережные Челны");
        cites.add("Елабуга");
        mListViewCards = (ListView) mViewCards.findViewById(R.id.lv_cards);
        mRelativeView= (RelativeLayout) mViewCards.findViewById(R.id.relative_container);

        getAllCards();
    }

    @SuppressLint("CheckResult")
    private void getAllCards() {
        /*mApiService.get_tasks(PreferenceManager.getUserName(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    populateAdapter(tasks);
                    mSwipeView.postDelayed(() -> mSwipeView.setRefreshing(false), 500);
                }, throwable ->
                {
                    throwable.printStackTrace();
                });*/
        for(int i = 0; i < cites.size(); i++){
            mApiService
                    .get_current_weather(BuildConfig.ACCESS_KEY, cites.get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(card -> {
                        addCardInList(card);
                        populateAdapter();
                    }, throwable -> {
                        throwable.printStackTrace();
                        toastError();
                    });
        }
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
                mListViewCards.setAdapter(null);
                CardsListAdapter cardsListAdapter = new CardsListAdapter(mCardsList, getActivity());
                mListViewCards.setAdapter(cardsListAdapter);
                mListViewCards.requestLayout();
            }
        }
    }

    public void toastError(){

    }

}