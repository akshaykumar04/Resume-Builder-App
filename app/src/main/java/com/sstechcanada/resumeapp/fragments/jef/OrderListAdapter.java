package com.sstechcanada.resumeapp.fragments.jef;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.models.Order;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private TextView name, email, orderId, timestamp;
    private Activity context;
    List<Order> orders;

    public OrderListAdapter(Activity context, List<Order> orders) {
        super(context, R.layout.layout_orders_list, orders);
        this.context = context;
        this.orders = orders;
    }

    public OrderListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_orders_list, null, true);

        name = listViewItem.findViewById(R.id.orderCardName);
        email = listViewItem.findViewById(R.id.orderCardEmail);
        orderId = listViewItem.findViewById(R.id.orderCardID);
        timestamp = listViewItem.findViewById(R.id.orderCardTime);


        Order order = orders.get(position);
        name.setText(order.getName());
        email.setText(order.getEmail());
        orderId.setText(order.getOrderID());
        timestamp.setText(order.getDate());


        return listViewItem;
    }
}
