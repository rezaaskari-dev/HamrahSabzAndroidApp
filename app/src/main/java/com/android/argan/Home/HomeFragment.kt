package com.android.argan.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.argan.Banner.ImageSliderAdapter
import com.android.argan.Detail.DetailFragment
import com.android.argan.R

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    private val localImages = listOf(R.drawable.c, R.drawable.b, R.drawable.a)

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        viewPager = view.findViewById(R.id.viewPagerTop)


        handler = Handler(Looper.getMainLooper())

        setupButtons(view)

        imageSliderAdapter = ImageSliderAdapter(localImages, this)
        viewPager.adapter = imageSliderAdapter


        checkAndLoadImages()


        return view
    }

    private fun openDetail(type: String) {
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply { putString("type", type) }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_main, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    @SuppressLint("MissingInflatedId")
    private fun setupButtons(view: View) {
        // چون در XML btn_1..btn_4 از نوع ConstraintLayout هستن، پس View بگیر نه Button
        val btn1: View = view.findViewById(R.id.btn_1)
        val btn2: View = view.findViewById(R.id.btn_2)
        val btn3: View = view.findViewById(R.id.btn_3)
        val btn4: View = view.findViewById(R.id.btn_4)

        btn1.setOnClickListener { openDetail("gas") }
        btn2.setOnClickListener { openDetail("electricity") }
        btn3.setOnClickListener { openDetail("tips") }
        btn4.setOnClickListener { openDetail("water") }
    }

    private fun checkAndLoadImages() {
        displayImages(localImages)
    }

    private fun displayImages(imageIds: List<Int>) {
        viewPager.adapter = ImageSliderAdapter(imageIds, this)
        viewPager.setCurrentItem(0, false)
        startAutoScroll()
    }

    private fun startAutoScroll() {
        runnable = Runnable {
            val currentPosition = viewPager.currentItem
            val count = viewPager.adapter?.itemCount ?: 0

            if (count == 0) return@Runnable

            if (currentPosition == count - 1) {
                viewPager.setCurrentItem(0, true)
            } else {
                viewPager.setCurrentItem(currentPosition + 1, true)
            }
            handler.postDelayed(runnable, 8000)
        }
        handler.removeCallbacks(runnable)
        handler.post(runnable)
    }

    override fun onPause() {
        super.onPause()
        if (::runnable.isInitialized) handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        if (::runnable.isInitialized) startAutoScroll()
    }
}
