package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Models.Item;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;

import java.util.List;

public class MarketAdapter extends BaseAdapter  {

    private Context context;
    private List<Item> items;
    private Typeface font;
    private SQLiteDatabase db;
    private Item item;
    private int taskMinLevel;
    private Player player;
    private int playerLevel;
    OnDataChangeListenerMarket mOnDataChangeListener;
    private Activity activity;
    private ListView listView;
    private ProgressBarAsyncTask asyncTask;
    public static boolean running=false;
    private boolean success;

    public MarketAdapter(Context context, List<Item> items, Typeface font, Player player, Activity activity,SQLiteDatabase db) {
        this.context = context;
        this.items=items;
        this.font=font;
        this.activity=activity;
        this.player=player;
        this.db=db;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        item = items.get(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.item_market,null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_viewObject);
            viewHolder.itemName=(TextView)convertView.findViewById(R.id.object_name);
            viewHolder.itemDescription=(TextView)convertView.findViewById(R.id.item_description);
            viewHolder.itemPriceMoney =(TextView)convertView.findViewById(R.id.itemPrice);
            viewHolder.itemUpgradeEfficacy =(TextView)convertView.findViewById(R.id.upgradeEfficacy);
            viewHolder.itemUpgradeEnergy =(TextView)convertView.findViewById(R.id.upgradeEnergy);
            viewHolder.imageView =(ImageView) convertView.findViewById(R.id.imageMarket);
            if(item.getItemUpgradeEfficacy()==0) {
                if(item.getItemUpgradeEnergy()!=0){
                    viewHolder.itemUpgradeEnergy.setVisibility(View.VISIBLE);
                    viewHolder.imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.energy,null));
                }

            }else{
                viewHolder.imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bullseye,null));
                viewHolder.itemUpgradeEfficacy.setVisibility(View.VISIBLE);

            }
            if(item.getItemUpgradeEfficacy()==0&&item.getItemUpgradeEnergy()==0)
                viewHolder.imageView.setVisibility(View.GONE);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemName.setTypeface(font);
        viewHolder.itemDescription.setTypeface(font);
        viewHolder.itemPriceMoney.setTypeface(font);
        viewHolder.itemUpgradeEfficacy.setTypeface(font);
        viewHolder.itemUpgradeEnergy.setTypeface(font);
        //viewHolder.progressBar.setVisibility(View.GONE);



        viewHolder.itemName.setText(item.getItemName());
        viewHolder.itemDescription.setText(item.getItemDescription());
        viewHolder.itemPriceMoney.setText(item.getItemPriceMoney()+"$");
        viewHolder.itemUpgradeEfficacy.setText("+"+item.getItemUpgradeEfficacy()+" Eficacia");
        viewHolder.itemUpgradeEnergy.setText("+"+item.getItemUpgradeEnergy()+" Energia");
        //viewHolder.cuadroFoto.setImageResource(cuadros.get(position).getPainting());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               item=items.get(position);
               if(player.getPlayerMoney()-item.getItemPriceMoney()>0){ //Can buy
                   YoYo.with(Techniques.Pulse)
                           .duration(200)
                           .playOn(viewHolder.cardView);
                   player.setPlayerEfficacy(player.getPlayerEfficacy()+item.getItemUpgradeEfficacy());
                   player.setPlayerEnergy(player.getPlayerEnergy()+item.getItemUpgradeEnergy());
                   player.setPlayerMoney(player.getPlayerMoney()-item.getItemPriceMoney());
                   if(mOnDataChangeListener!=null){
                       mOnDataChangeListener.onDataChanged();
                   }
                   item.setItemPriceMoney(item.getItemPriceMoney()+5);
                   ContentValues contentValues = new ContentValues();
                   contentValues.put("priceMoney",item.getItemPriceMoney());
                   contentValues.put("adquired",1);
                   db.update("items",contentValues, "id="+item.getItemId(),null);
                   notifyDataSetChanged();

               }else{//Not enough money
                   YoYo.with(Techniques.Shake)
                           .duration(600)
                           .playOn(viewHolder.cardView);
                   if(mOnDataChangeListener!=null){
                       mOnDataChangeListener.onDataChanged();
                   }
               }


            }
        });
        return convertView;
    }

    static class ViewHolder{
        private CardView cardView;
        private TextView itemName;
        private TextView itemDescription;
        private TextView itemPriceMoney;
        private TextView itemUpgradeEnergy;
        private TextView itemUpgradeEfficacy;
        private ImageView imageView;
    }


    public void setOnDataChangeListener(OnDataChangeListenerMarket onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListenerMarket{
        void onDataChanged();
    }


}

