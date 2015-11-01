package pl.whiter.realmio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingItem;

/**
 * Created by whiter
 */
public class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.shopping_list_name)
    TextView name;

    @Bind(R.id.shopping_extra)
    TextView count;

    private ShoppingItemAdapter.ActionListener actionListener;

    public ShoppingItemViewHolder(View itemView, ShoppingItemAdapter.ActionListener actionListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.actionListener = actionListener;
    }

    public void attachShoppingItem(ShoppingItem shoppingItem) {
        name.setText(shoppingItem.getName());
        count.setText(String.valueOf(shoppingItem.getCount()));
    }

    @OnClick(R.id.plus)
    void onPlusClicked() {
        actionListener.onIncreaseClicked(getAdapterPosition());
    }

    @OnClick(R.id.minus)
    void onMinusClicked() {
        actionListener.onDecreaseClicked(getAdapterPosition());
    }

    @OnClick(R.id.delete)
    void onDeleteClicked() {
        actionListener.onDeleteClicked(getAdapterPosition());
    }
}
