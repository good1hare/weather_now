package com.shabu.weathernow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shabu.weathernow.R;
import com.shabu.weathernow.models.WeatherCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardsListAdapter extends BaseAdapter {

  @BindView(R.id.tvName)
  TextView itemName;
  @BindView(R.id.tvTemp)
  TextView itemExec;
  @BindView(R.id.tvTime)
  TextView itemDesc;
  private List mListCards;

  public CardsListAdapter(List list, Context context) {
    this.mListCards = list;
  }

  @Override
  public int getCount() {
    return mListCards.size();
  }

  @Override
  public Object getItem(int position) {
    return mListCards.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    WeatherCard card = (WeatherCard) mListCards.get(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_card, parent, false);
    }
    ButterKnife.bind(this, convertView);

    itemName.setText("Место: " + card.getLocation().getName());
    itemExec.setText("Температура: " + card.getCurrent().getTemperature());
    itemDesc.setText("Ветер: " + card.getCurrent().getWind_speed() + " м/с");
    return convertView;
  }
}
