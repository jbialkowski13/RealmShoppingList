package pl.whiter.realmio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingItem;

/**
 * Created by whiter
 */
public class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.shopping_list_name)
    TextView name;

    public ShoppingItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void attachShoppingItem(ShoppingItem shoppingItem) {
        name.setText(shoppingItem.getName());
    }
}
