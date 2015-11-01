package pl.whiter.realmio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmList;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingItem;

/**
 * Created by whiter
 */
public class ShoppingItemAdapter extends AbstractBaseRealmAdapter<ShoppingItem, ShoppingItemViewHolder> {

    private LayoutInflater inflater;

    public ShoppingItemAdapter(Context context, RealmList<ShoppingItem> shoppingList) {
        super(shoppingList);
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder holder, int position) {
        holder.attachShoppingItem(getItem(position));
    }
}
