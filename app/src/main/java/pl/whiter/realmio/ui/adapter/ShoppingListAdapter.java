package pl.whiter.realmio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingList;

/**
 * Created by whiter
 */
public class ShoppingListAdapter extends AbstractRealmAdapter<ShoppingList, ShoppingListViewHolder> {

    private LayoutInflater inflater;

    public ShoppingListAdapter(Context context, Realm realm) {
        super(realm);
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    protected RealmResults<ShoppingList> loadData(Realm realm) {
        return realm.where(ShoppingList.class).findAll();
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        holder.attachShoppingList(getItem(position));
    }
}
