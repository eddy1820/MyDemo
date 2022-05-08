package com.example.mydemo.extension

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.showIf(isShow: Boolean = true) {
    isVisible = isShow
}

fun View.hideIf(isHide: Boolean = true) {
    isVisible = !isHide
}

fun View.showToggle() = showIf(!isVisible)
fun View.selectToggle() {
    isSelected = !isSelected
}

fun View.selected() {
    isSelected = true
}

fun View.unselected() {
    isSelected = false
}

fun View.displayIf(isDisplay: Boolean = true) {
    if (isDisplay) show() else inVisible()
}

inline fun View.clickThrottleFirst(crossinline callback: () -> Unit): Disposable =
    this.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe({
        callback()
    }, {
        Log.e("View.clickThrottleFirst", "clickThrottleFirst: ", it)
    })

inline var View.topMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0
    set(value) {
        layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            topMargin = value
        }
    }

inline var View.bottomMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
    set(value) {
        layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            bottomMargin = value
        }
    }

/*
 detect the view is completely visible
but not mean it is on the front on the screen
*/
val View?.isCompletelyVisible: Boolean
    get() {
        if (this?.isShown != true) return false
        val actualPosition = Rect().also { getGlobalVisibleRect(it) }
        val screen = Resources.getSystem().displayMetrics.run {
            Rect(0, 0, widthPixels, heightPixels)
        }
        return actualPosition.intersect(screen)
    }

fun View.getDecorView(): ViewGroup? {
    var view = this
    while (view.parent is View) {
        view = view.parent as View
        if (view.layoutParams is WindowManager.LayoutParams) {
            return view as? ViewGroup
        }
    }
    return null
}

fun View.rotate(degree: Float = 360f, duration: Long = 1000L, repeatCount: Int = 1) {
    RotateAnimation(
        0f,
        degree,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        this.duration = duration
        this.repeatCount = repeatCount
    }.let(this::startAnimation)
}

fun View.rotateToDegree(fromDegrees: Float = 0f, toDegrees: Float = 360f, duration: Long = 1000L) {
    RotateAnimation(
        fromDegrees,
        toDegrees,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        this.duration = duration
        this.fillAfter = true
    }.let(this::startAnimation)
}

fun View.screenshot(): Bitmap {
    val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    draw(c)
    return b
}

fun View.screenshotSelected(): Bitmap {
    val selected = isSelected
    isSelected = true
    // todo use the new extension in the future but now we consider the compatible
    // val bitmap = drawToBitmap()
    val bitmap = screenshot()
    isSelected = selected
    return bitmap
}

fun View.smoothMoveToTargetView(
    activity: Activity,
    destView: View,
    duration: Long = 500L,
    animatorListener: Animator.AnimatorListener? = null,
    statusBarHeight: Int = 0,
    startDelay: Long = 0L
) {
    smoothMoveToTargetView(
        activity.window.decorView,
        destView,
        duration,
        animatorListener,
        statusBarHeight,
        startDelay
    )
}

fun View.smoothMoveToTargetView(
    decorView: View,
    destView: View,
    duration: Long = 500L,
    animatorListener: Animator.AnimatorListener? = null,
    statusBarHeight: Int = 0,
    startDelay: Long = 0L
) {
    (decorView as ViewGroup).also { decorView ->
        val rect = Rect()
        getGlobalVisibleRect(rect)
        val fromX = rect.left.toFloat()
        var fromY = rect.top.toFloat()
        post {
            val destRect = Rect()
            destView.getGlobalVisibleRect(destRect)
            val destX = destRect.left - (width - destView.width) / 2f
            val destY = destRect.top - (height - destView.height) / 2f
            val animationImageView = screenshotSelected()
                .let {
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(it.width, it.height)
                        setImageBitmap(it)
                        isVisible = false
                    }
                }
            fromY += statusBarHeight
            decorView.addView(animationImageView)

            val scaleFactor = 1f
            val path = Path().apply {
                moveTo(fromX, fromY)
            }
            val path2 = Path().apply {
                moveTo(fromX, fromY)
                quadTo((fromX + destX) / 2, fromY - 200f, destX, destY)
            }
            val moveAnimator =
                ObjectAnimator.ofFloat(animationImageView, View.X, View.Y, path).apply {
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) = Unit

                        override fun onAnimationEnd(animation: Animator?) = Unit

                        override fun onAnimationCancel(animation: Animator?) =
                            decorView.removeView(animationImageView)

                        override fun onAnimationStart(animation: Animator) = Unit
                    })
                    setDuration(10L)
                }
            val moveAnimator2 = ObjectAnimator.ofFloat(animationImageView, View.X, View.Y, path2)
            val disappearAnimatorY: Animator =
                ObjectAnimator.ofFloat(animationImageView, View.SCALE_Y, scaleFactor, 0f)
            val disappearAnimatorX: Animator =
                ObjectAnimator.ofFloat(animationImageView, View.SCALE_X, scaleFactor, 0f)


            AnimatorSet().apply {
                setDuration(duration)
                playTogether(
                    moveAnimator2,
                    disappearAnimatorY,
                    disappearAnimatorX
                )
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) = Unit

                    override fun onAnimationEnd(animation: Animator?) {
                        decorView.removeView(animationImageView)
                        (destView as? ViewEffects)?.blink()
                    }

                    override fun onAnimationCancel(animation: Animator?) =
                        decorView.removeView(animationImageView)

                    override fun onAnimationStart(animation: Animator) {
                        animationImageView.apply {
                            show()
                            setLayerType(LAYER_TYPE_HARDWARE, null)
                        }
                    }
                })
                startDelay.takeIf { it > 0 }?.let {
                    this.startDelay = startDelay
                }

                animatorListener?.let(this::addListener)
            }.let {
                AnimatorSet().run {
                    play(moveAnimator).before(it)
                    start()
                }
            }
        }
    }
}

fun Button.setTextSafeColor(color: Int) {
    context.getSafeColor(color)?.let(::setTextColor)
}

fun Button.toDisable(color: Int) {
    isEnabled = false
    setTextSafeColor(color)
}

fun Button.toEnable(color: Int) {
    isEnabled = true
    setTextSafeColor(color)
}


inline fun View.fadeOut(duration: Long, crossinline onEnd: () -> Unit = {}) {
    ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f).let {
        it.doOnEnd { onEnd.invoke() }
        it.duration = duration
        it.start()
    }
}

inline fun View.fadeIn(duration: Long, crossinline onEnd: () -> Unit = {}) {
    ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f).let {
        it.doOnEnd { onEnd.invoke() }
        it.duration = duration
        it.start()
    }
}

inline fun View.fadeIn(duration: Long, startDelay: Long, crossinline onEnd: () -> Unit = {}) {
    ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f).let {
        it.doOnEnd { onEnd.invoke() }
        it.duration = duration
        it.startDelay = startDelay
        it.start()
    }
}

fun View.translationAnimX(from: Int, to: Int, duration: Long) {
    ObjectAnimator.ofFloat(this, "translationX", from.toFloat(), to.toFloat()).let {
        it.duration = duration
        it.start()
    }
}

fun View.translationAnimY(from: Int, to: Int, duration: Long) {
    ObjectAnimator.ofFloat(this, "translationY", from.toFloat(), to.toFloat()).let {
        it.duration = duration
        it.start()
    }
}

fun ViewGroup.doOnSelfAndDescendant(callback: (view: View) -> Unit) {
    callback.invoke(this)
    doOnDescendant(callback)
}

fun ViewGroup.doOnDescendant(callback: (view: View) -> Unit) = forEach {
    (it as? ViewGroup)?.doOnSelfAndDescendant(callback) ?: callback.invoke(it)
}

interface ViewEffects {
    fun blink()
}

fun HorizontalScrollView.smoothScrollToTarget(target: View, screenWidth: Int, padding: Int) {
    val x = target.left - (screenWidth / 2) + (target.width / 2) - padding
    smoothScrollTo(x, 0)
}