package com.example.android.navigationadvancedsample.listscreen;

import static androidx.core.os.BundleKt.bundleOf;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.BundleKt;
import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.navigationadvancedsample.R;

import org.jetbrains.annotations.NotNull;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;

public final class Leaderboard extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        byte count = 10;
        String[] persons = new String[count];

        for(int i = 0; i < count; ++i) {
            String var13 = "Person " + (i + 1);
            persons[i] = var13;
        }

        MyAdapter personAdapter = new MyAdapter(persons);

        RecyclerView rvPersons = view.findViewById(R.id.leaderboard_list);
        rvPersons.setHasFixedSize(true);
        rvPersons.setAdapter(personAdapter);
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final String[] myDataset;
        @NotNull
        public static final String USERNAME_KEY = "userName";

        public MyAdapter(@NotNull String[] myDataset) {
            super();
            this.myDataset = myDataset;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final View item;
            public final View getItem() {
                return this.item;
            }

            public ViewHolder(View item) {
                super(item);
                this.item = item;
            }
        }


        // Create new views (invoked by the layout manager)
        public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_view_item, parent, false);
            return new ViewHolder(itemView);
        }


        // Replace the contents of a view (invoked by the layout manager)
        public void onBindViewHolder( ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            ((TextView)holder.item.findViewById(R.id.user_name_text)).setText(myDataset[position]);

            ((ImageView)holder.item.findViewById(R.id.user_avatar_image))
                    .setImageResource(listOfAvatars[position % listOfAvatars.length]);

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = BundleKt.bundleOf(TuplesKt.to("userName",MyAdapter.this.myDataset[position]));
                    ViewKt.findNavController(holder.getItem()).navigate(R.id.action_leaderboard_to_userProfile, bundle);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        public int getItemCount() {
            return this.myDataset.length;
        }

    }

    private int[] listOfAvatars = new int[]{
            R.drawable.avatar_1_raster,
            R.drawable.avatar_2_raster,
            R.drawable.avatar_3_raster,
            R.drawable.avatar_4_raster,
            R.drawable.avatar_5_raster,
            R.drawable.avatar_6_raster
    };
}
