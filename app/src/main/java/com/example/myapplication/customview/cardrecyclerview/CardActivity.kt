package com.example.myapplication.customview.cardrecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.R
import java.util.ArrayList

/**
 * @Desc:
 * @Author: weiyi1
 * @Date: 2019/8/28.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class CardActivity : AppCompatActivity() {
    private val list = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        initView()
        initData()
    }

    private fun initView() {
        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = MyAdapter()
        val cardCallback = ItemCallback(recyclerView.adapter as MyAdapter, list)
        cardCallback.setOnSwipedListener(object : OnSwipeListener<Int> {

            override fun onSwiping(viewHolder: RecyclerView.ViewHolder, ratio: Float, direction: Int) {
                val myHolder = viewHolder as MyAdapter.MyViewHolder
                viewHolder.itemView.alpha = 1 - Math.abs(ratio) * 0.2f
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.alpha = Math.abs(ratio)
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.alpha = Math.abs(ratio)
                } else {
                    myHolder.dislikeImageView.alpha = 0f
                    myHolder.likeImageView.alpha = 0f
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, o: Int, direction: Int) {
                val myHolder = viewHolder as MyAdapter.MyViewHolder
                viewHolder.itemView.alpha = 1f
                myHolder.dislikeImageView.alpha = 0f
                myHolder.likeImageView.alpha = 0f
                Toast.makeText(
                    this@CardActivity,
                    if (direction == CardConfig.SWIPED_LEFT) "swiped left" else "swiped right",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSwipedClear() {
                Toast.makeText(this@CardActivity, "data clear", Toast.LENGTH_SHORT).show()
                recyclerView.postDelayed({
                    initData()
                    recyclerView.adapter!!.notifyDataSetChanged()
                }, 2000L)
            }

        })
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recyclerView, touchHelper)
        recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initData() {
        list.add(R.drawable.img_avatar_01)
        list.add(R.drawable.img_avatar_02)
        list.add(R.drawable.img_avatar_03)
        list.add(R.drawable.img_avatar_04)
        list.add(R.drawable.img_avatar_05)
        list.add(R.drawable.img_avatar_06)
        list.add(R.drawable.img_avatar_07)
    }

    private inner class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val avatarImageView = (holder as MyViewHolder).avatarImageView
            avatarImageView.setImageResource(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var avatarImageView: ImageView
            var likeImageView: ImageView
            var dislikeImageView: ImageView

            init {
                avatarImageView = itemView.findViewById<View>(R.id.iv_avatar) as ImageView
                likeImageView = itemView.findViewById<View>(R.id.iv_like) as ImageView
                dislikeImageView = itemView.findViewById<View>(R.id.iv_dislike) as ImageView
            }

        }
    }
}