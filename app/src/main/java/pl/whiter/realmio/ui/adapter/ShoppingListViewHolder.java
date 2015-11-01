package pl.whiter.realmio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingList;

/**
 * Created by whiter
 */
public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());


    @Bind(R.id.shopping_list_name)
    TextView shoppingListName;

    @Bind(R.id.shopping_extra)
    TextView date;


    public ShoppingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void attachShoppingList(ShoppingList shoppingList) {
        shoppingListName.setText(shoppingList.getDetails().getName());
        String formattedDate = dateFormat.format(shoppingList.getDetails().getDate());
        date.setText(formattedDate);
    }
}
