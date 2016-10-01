package com.example.money.conatusapp.TeamMembers.currentTeam;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.Animations.AnimationUtils;
import com.example.money.conatusapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTeamFragment extends Fragment {
    private RecyclerView mMembersList;
    private DatabaseReference mDatabase;
    private int previousPosition = -1;


    public CurrentTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_team, container, false);
        mMembersList = (RecyclerView) view.findViewById(R.id.members_list);
        mMembersList.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("members");
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        previousPosition = -1;
        final FirebaseRecyclerAdapter<Member, MemberViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, MemberViewHolder>(Member.class,
                R.layout.member_list_layout,
                MemberViewHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(final MemberViewHolder viewHolder, Member model, int position) {
                viewHolder.memberNameField.setText(model.getName());
                viewHolder.memberBranch.setText(model.getBranch());
                viewHolder.memberYear.setText(model.getYear() + "rd Year");
                viewHolder.memberDomain.setText(model.getDomain());
                Picasso.with(getActivity()).load(model.getImage()).resize(130, 130).into(viewHolder.memberImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) viewHolder.memberImage.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        viewHolder.memberImage.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {

                    }
                });
                if (position > previousPosition) {
                    AnimationUtils.animate(viewHolder, true);
                } else {

                    AnimationUtils.animate(viewHolder, false);
                }
                previousPosition = position;
            }
        };
        mMembersList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView memberNameField;
        public TextView memberBranch;
        public TextView memberYear;
        public TextView memberDomain;
        public ImageView memberImage;

        public MemberViewHolder(View itemView) {
            super(itemView);
            memberNameField = (TextView) itemView.findViewById(R.id.member_name);
            memberBranch = (TextView) itemView.findViewById(R.id.member_branch);
            memberYear = (TextView) itemView.findViewById(R.id.member_year);
            memberDomain = (TextView) itemView.findViewById(R.id.member_domain);
            memberImage = (ImageView) itemView.findViewById(R.id.member_image);
        }
    }

}

