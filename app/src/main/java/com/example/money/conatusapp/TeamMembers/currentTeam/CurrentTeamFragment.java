package com.example.money.conatusapp.TeamMembers.currentTeam;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devspark.progressfragment.ProgressFragment;
import com.example.money.conatusapp.Animations.AnimationUtils;
import com.example.money.conatusapp.Database.CurrentTeamMembersDatabase;
import com.example.money.conatusapp.ImageDownloadActivty;
import com.example.money.conatusapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTeamFragment extends ProgressFragment {
    private RecyclerView mMembersList;
    private DatabaseReference mDatabase;
    private CurrentTeamMembersDatabase currentTeamMembersDatabase;
    private static int previousPosition = -1;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;
    private static List<Member> memberList = new ArrayList<>();
    private MemberAdapter mMemberAdapter;
    private int count = 0;
    private View view;


    public CurrentTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(view);
        setContentShown(false);
        if (!memberList.isEmpty()) {
            setContentShown(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        currentTeamMembersDatabase = new CurrentTeamMembersDatabase(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("members");
        sContext = getActivity();

        mMemberAdapter = new MemberAdapter();
        memberList = currentTeamMembersDatabase.getData();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                memberList.removeAll(memberList);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Member member = ds.getValue(Member.class);
                    memberList.add(member);

                }
                setContentShown(true);
                currentTeamMembersDatabase.insertData(memberList);
                mMemberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_team, container, false);
        mMembersList = (RecyclerView) view.findViewById(R.id.members_list);


        if (count == 0) {
            mMembersList.setLayoutManager(mLinearLayoutManager);
            mMembersList.setAdapter(mMemberAdapter);
            count++;
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    private static class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {
        public MemberAdapter() {
        }

        @Override
        public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(sContext);
            View view = inflater.inflate(R.layout.member_list_layout, parent, false);
            return new MemberViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MemberViewHolder viewHolder, final int position) {

            viewHolder.memberNameField.setText(memberList.get(position).getName());
            viewHolder.memberBranch.setText(memberList.get(position).getBranch());
            viewHolder.memberYear.setText(memberList.get(position).getYear());
            viewHolder.imageUrl = memberList.get(position).getImage();
            viewHolder.memberDomain.setText(memberList.get(position).getDomain());
            Picasso.with(sContext).load(Uri.parse(memberList.get(position).getImage())).noFade().networkPolicy(NetworkPolicy.OFFLINE).noFade().resize(150, 150).into(viewHolder.memberImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(Uri.parse(memberList.get(position).getImage())).noFade().into(viewHolder.memberImage);
                }
            });
            if (position > previousPosition)
                AnimationUtils.animate(viewHolder, true);
            else
                AnimationUtils.animate(viewHolder, false);


        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

