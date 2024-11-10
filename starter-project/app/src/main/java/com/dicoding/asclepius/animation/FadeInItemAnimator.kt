package com.dicoding.asclepius.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class FadeInItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.alpha = 0f
        val animator = ObjectAnimator.ofFloat(holder?.itemView, View.ALPHA, 0f, 1f)
        animator.duration = 500
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
            }
        })
        animator.start()
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        val animator = ObjectAnimator.ofFloat(holder?.itemView, View.ALPHA, 1f, 0f)
        animator.duration = 500
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchRemoveFinished(holder)
            }
        })
        animator.start()
        return true
    }
}