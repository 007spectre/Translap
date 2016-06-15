package com.translap.translatr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.util.List;

/**
 * Created by ccs7pc on 1/22/2016.
 */
public class ListingTestAdapter extends RecyclerView.Adapter<ListingTestAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the viaew under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private ImageLoader imageLoader;
    private Context context;
String catt;
    String td;


    //List of superHeroes
    List<TestDetail> job;

    public ListingTestAdapter(List<TestDetail> job, Context context){
        super();
        //Getting all the superheroes
        this.job = job;
        this.context = context;
    }

    @Override
    public ListingTestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listinglist,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listinglist,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TestDetail detail =   job.get(position);

        //     imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        //    imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //      holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        String k= String.valueOf(position+1);
        holder.b1.setText(k);
        holder.textViewName.setText(detail.getQuestion());
      //  holder.cat.setText(detail.getCategory());
        catt=detail.getCategory();
        td=detail. getQuestion();
        holder.textViewName1.setText(detail.getTest());
        holder.textView1.setText(detail.getCoin());






        holder.itemView.setTag(detail);
//        holder.textViewRank.setText(String.valueOf(superHero.getRank()));
        //       holder.textViewRealName.setText(superHero.getRealName());
        //     holder.textViewCreatedBy.setText(superHero.getCreatedBy());
        //   holder.textViewFirstAppearance.setText(superHero.getFirstAppearance());




    }

    @Override
    public int getItemCount() {
        return job.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public NetworkImageView imageView;

        Context contxt;
        Button b1;
        Button start;
        TextView textViewName;
        TextView cat;
                TextView textViewName1;
        TextView textView1;
        FloatingActionButton FAB;
        public ViewHolder(View itemView,int ViewType,Context c ) {
            super(itemView);
            contxt = c;
            //    imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.test);
            textViewName1 = (TextView) itemView.findViewById(R.id.test1);
            textView1 = (TextView) itemView.findViewById(R.id.test2);
  b1=(Button) itemView.findViewById(R.id.id);

         //    FAB = (FloatingActionButton)itemView.findViewById(R.id.fab);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            //   textViewRank= (TextView) itemView.findViewById(R.id.textViewRank);
            //   textViewRealName= (TextView) itemView.findViewById(R.id.textViewRealName);
            //   textViewCreatedBy= (TextView) itemView.findViewById(R.id.textViewCreatedBy);
            //   textViewFirstAppearance= (TextView) itemView.findViewById(R.id.textViewFirstAppearance);


        }


        @Override
        public void onClick(View v) {


            TestDetail person =(TestDetail)v.getTag();
            String strUrl= person.getCategory();
            String strUrl1= person.getQuestion();
            Intent i = new Intent(context, StartTest.class);
            i.putExtra("category", strUrl);
            i.putExtra("testdetail", strUrl1);
            context.startActivity(i);



            //   Intent intent=new Intent(context,IndpUser.class);
            //   SuperHeroes superHero = (SuperHeroes)v.getTag();
            //   String strUrl= superHero.getImageUrl();
            //   intent.putExtra("cname", strUrl);
            //   contxt.startActivity(intent);
        //    TestDetail detail =   job.get(position);
//            EnCategory superHero = (EnCategory)v.getTag();
  //          String strUrl= superHero.getCategory();
    //        Intent intent=new Intent(context,StartTest.class);
      //      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //    intent.putExtra("category", strUrl);
          //  context.startActivity(intent);


        }

    }
}
