package com.datechnologies.androidtest.animation

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.databinding.ActivityAnimationBinding

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */
class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!
        binding.buttonFade.setOnClickListener { fadeOutImage() }
        binding.constraintLayout.setOnDragListener(dragListener)

        binding.datechnologyImage.setOnClickListener { wiggleAnim(it) }
        binding.datechnologyImage.setOnLongClickListener {
            dragThisView(it)
        }
    }
    fun wiggleAnim(view: View) {
        val wiggleAnim = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        view.startAnimation(wiggleAnim)
    }
    private fun dragThisView(it: View): Boolean {
        val dropToast = "Click Me!"
        val itemDrag = ClipData.Item(dropToast)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val dragData = ClipData(dropToast, mimeTypes, itemDrag)

        val dragBuilder = View.DragShadowBuilder(it)
        it.startDragAndDrop(dragData, dragBuilder, it, 0)

        it.visibility = View.INVISIBLE
        return true
    }

    private val dragListener = View.OnDragListener{ view, event ->
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> true
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> true
            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Toast.makeText(this, dragData , Toast.LENGTH_SHORT).show()

                view.invalidate()

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as ConstraintLayout
                destination.addView(v)
                v.visibility = View.VISIBLE
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }
    private fun fadeOutImage() {
        binding.apply {
            datechnologyImage.animate().alpha(0F).duration = 1000
            buttonFade.setText(R.string.fade_in)
            buttonFade.setOnClickListener { fadeInImage() }
        }
    }
    private fun fadeInImage() {
        binding.apply {
            datechnologyImage.animate().alpha(1F).duration = 1000
            buttonFade.setText(R.string.fade_out)
            buttonFade.setOnClickListener { fadeOutImage() }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        //==============================================================================================
        // Class Properties
        //==============================================================================================
        //==============================================================================================
        // Static Class Methods
        //==============================================================================================
        fun start(context: Context) {
            val starter = Intent(context, AnimationActivity::class.java)
            context.startActivity(starter)
        }
    }
}