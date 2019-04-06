package yildiz.com.mobil2019;


import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class myAdapter extends RecyclerView.Adapter{
    List<viewModel> mData;

    public static class viewModel{
        String Name;
        String Grade;
        public viewModel(String Name,String Grade){
            this.Name=Name;
            this.Grade=Grade;
        }
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView CourseN,Grade;

        public myViewHolder(View view){
            super(view);

            CourseN=(TextView)view.findViewById(R.id.CourseN);
            Grade=(TextView)view.findViewById(R.id.Grade);
        }

        public void bindData(viewModel model){
            CourseN.setText(model.Name);
            this.Grade.setText(model.Grade);
        }
    }

    public myAdapter(List<viewModel> data){
        mData=data;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ((myViewHolder)viewHolder).bindData(mData.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Courses.getAppContext(),DetailScreen.class);
                intent.putExtra("DersAdı",mData.get(position).Name);
                Courses.getAppContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return  R.layout.recycle_course;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return new myViewHolder(v);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}