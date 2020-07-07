package br.com.guilhermesantana.canvasexemples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import br.com.guilhermesantana.canvasexemples.samples.SquaredProgressView
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        squaredProgressView.max = 100f
        squaredProgressView.setOnProgressChangeListener(object : SquaredProgressView.OnProgressChangeListener{
            override fun onProgressChange(progress: Float) {
                progressText.setText(String.format("%.0f", progress)+"%")
            }
        })
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar) {
                squaredProgressView.animateToProgress(p0.progress.toFloat(), 400)
            }

        })
        squaredProgressView.animateToProgress(seekBar.progress.toFloat(), 400)
    }
}
