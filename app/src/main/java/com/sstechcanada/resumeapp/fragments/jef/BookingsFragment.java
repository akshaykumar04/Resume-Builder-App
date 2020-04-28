package com.sstechcanada.resumeapp.fragments.jef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookingsFragment extends Fragment {
    private FirebaseAuth mAuth;

    ListView listViewOrders;

    //a list to store all the artist from firebase database
    List<Order> orders;

    //our database reference object
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jef_bookings, container, false);
        mAuth = FirebaseAuth.getInstance();

        //getting the reference of artists node
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        //getting views
        listViewOrders = rootView.findViewById(R.id.listViewRates);

        //list to store artists
        orders = new ArrayList<>();


        //toast onClick on list view
        listViewOrders.setOnItemClickListener((parent, view, position, id) -> {

        });



        //attaching listener to listview------- onClick for list view
      /*  listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Artist artist = artists.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getEmail());
                intent.putExtra(ARTIST_NAME, artist.getName());

                //starting the activity with intent
                startActivity(intent);
            }
        }); */


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                orders.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting aorder
                    Order order = postSnapshot.getValue(Order.class);
                    //adding artist to the list
                    orders.add(order);
                }

                //creating adapter
                OrderListAdapter orderAdapter = new OrderListAdapter(getActivity(), orders);
                //attaching adapter to the listview
                listViewOrders.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
