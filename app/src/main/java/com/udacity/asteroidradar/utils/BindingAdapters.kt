package com.udacity.asteroidradar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.ui.main.adapter.AsteroidsAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, pictureOfDay: LiveData<PictureOfDay>) {
    Picasso.get().load(pictureOfDay.value?.url).into(imageView)
    imageView.contentDescription = pictureOfDay.value?.title
}

@BindingAdapter("bindAsteroidData")
fun bindAsteroidData(view: RecyclerView, asteroids: LiveData<List<Asteroid>>) {
    (view.adapter as AsteroidsAdapter).submitList(asteroids.value)
}

@BindingAdapter("bindHazardousImage")
fun bindHazardousImage(imageView: ImageView, isPotentiallyHazardous: Boolean) {
    when (isPotentiallyHazardous) {
        true -> {
            imageView.setImageResource(R.drawable.ic_smile_none)
            imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)  }
        else -> {
            imageView.setImageResource(R.drawable.ic_smile)
            imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)}
    }
}
