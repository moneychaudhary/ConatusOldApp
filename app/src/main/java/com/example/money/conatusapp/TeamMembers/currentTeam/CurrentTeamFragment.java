package com.example.money.conatusapp.TeamMembers.currentTeam;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.money.conatusapp.Animations.AnimationUtils;
import com.example.money.conatusapp.ImageDownloadActivty;
import com.example.money.conatusapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTeamFragment extends Fragment {
    private RecyclerView mMembersList;
    private DatabaseReference mDatabase;
    private int previousPosition = -1;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;


    public CurrentTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_team, container, false);
        mMembersList = (RecyclerView) view.findViewById(R.id.members_list);
        sContext = getActivity();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mMembersList.setLayoutManager(mLinearLayoutManager);
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
                viewHolder.imageUrl = model.getImage();
                viewHolder.memberDomain.setText(model.getDomain());
                Picasso.with(getActivity()).load(model.getImage()).noFade().resize(120, 120).into(viewHolder.memberImage);
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
        private TextView memberNameField;
        private TextView memberBranch;
        private TextView memberYear;
        private TextView memberDomain;
        private CircleImageView memberImage;
        private String imageUrl;

        public MemberViewHolder(View itemView) {
            super(itemView);
            memberNameField = (TextView) itemView.findViewById(R.id.member_name);
            memberBranch = (TextView) itemView.findViewById(R.id.member_branch);
            memberYear = (TextView) itemView.findViewById(R.id.member_year);
            memberDomain = (TextView) itemView.findViewById(R.id.member_domain);
            memberImage = (CircleImageView) itemView.findViewById(R.id.member_image);
            memberImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ImageDownloadActivty.getImageDownloadIntent(sContext, imageUrl);
                    sContext.startActivity(intent);
                }
            });
        }
    }

}

