package com.example.login_page1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel, UserAdapter.myViewHolder> {
    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int i, @NonNull UserModel uModel) {
        holder.userid.setText(uModel.getUserid());
        holder.password.setText(uModel.getPassword());
        holder.email.setText(uModel.getEmail());

        Glide.with(holder.img.getContext()).load(uModel.getImgUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView userid, email, imgUrl, password;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            userid = (TextView) itemView.findViewById(R.id.useridText);
            email = (TextView) itemView.findViewById(R.id.emailText);
            password=(TextView) itemView.findViewById(R.id.password);
        }
    }
}
