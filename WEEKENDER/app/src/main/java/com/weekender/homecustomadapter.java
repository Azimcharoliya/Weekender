package com.weekender;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
        import java.util.List;


public class homecustomadapter extends ArrayAdapter<homelistrow> {

    private Context mContext;
    private List<homelistrow> List = new ArrayList<>();

    public homecustomadapter(@NonNull Context mContext,List<homelistrow> List) {
        super(mContext,0,List);
        this.mContext = mContext;
        this.List = List;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public homelistrow getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.homelistview, parent, false);

        homelistrow currentlist = List.get(position);
        final String[] imageid = currentlist.getImageid();
        ImageView imageView1 = listItem.findViewById(R.id.image1);
        Picasso.with(mContext).load(imageid[0]).fit().into(imageView1);

        ImageView imageView2 = listItem.findViewById(R.id.image2);
        Picasso.with(mContext).load(imageid[1]).fit().into(imageView2);

        ImageView imageView3 = listItem.findViewById(R.id.image3);
        Picasso.with(mContext).load(imageid[2]).fit().into(imageView3);

        ImageView imageView4 = listItem.findViewById(R.id.image4);
        Picasso.with(mContext).load(imageid[3]).fit().into(imageView4);

        ImageView imageView5 = listItem.findViewById(R.id.image5);
        Picasso.with(mContext).load(imageid[4]).fit().into(imageView5);

        ImageView imageView6 = listItem.findViewById(R.id.image6);
        Picasso.with(mContext).load(imageid[5]).fit().into(imageView6);

        Button title = listItem.findViewById(R.id.category);
        title.setText(currentlist.getTitle());

        title.setTag(new Integer(position));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,whole.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                mContext.startActivity(i);
            }
        });

        Button more = listItem.findViewById(R.id.more);
        more.setTag(new Integer(position));
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,whole.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                mContext.startActivity(i);
            }
        });


        imageView1.setTag(new Integer(position));
        imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[0]);
                mContext.startActivity(i);
                    }
        });

        imageView2.setTag(new Integer(position));
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[1]);
                mContext.startActivity(i);

            }
        });

        imageView3.setTag(new Integer(position));
        imageView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[2]);
                mContext.startActivity(i);

            }
        });

        imageView4.setTag(new Integer(position));
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[3]);
                mContext.startActivity(i);
            }
        });

        imageView5.setTag(new Integer(position));
        imageView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[4]);
                mContext.startActivity(i);
            }
        });

        imageView6.setTag(new Integer(position));
        imageView6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specific.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[5]);
                mContext.startActivity(i);
            }
        });


        return listItem;
    }

}