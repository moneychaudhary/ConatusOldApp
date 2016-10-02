package com.example.money.conatusapp.Animations;

import android.animation.ObjectAnimator;

import com.example.money.conatusapp.TeamMembers.currentTeam.CurrentTeamFragment;

/**
 * Created by #money on 9/30/2016.
 */

public class AnimationUtils {

    public static void animate(CurrentTeamFragment.MemberViewHolder holder, boolean goesDown) {
        ObjectAnimator animationTranslationY;
        if (goesDown) {
            animationTranslationY = ObjectAnimator.ofFloat(holder.itemView, "translationY", 300, 0);
        } else {
            animationTranslationY = ObjectAnimator.ofFloat(holder.itemView, "translationY", -300, 0);
        }
        animationTranslationY.setDuration(1000);
        animationTranslationY.start();
    }

}
