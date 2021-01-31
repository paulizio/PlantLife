package com.example.plantlife

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_credits.*

class CreditsFragment : Fragment(R.layout.fragment_credits) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantIcon =
            "<html><body>  <a href=\"https://www.iconfinder.com/iconsets/spring-2-1\" >Spring-2 icon set</a> by <a href=\"https://www.iconfinder.com/atifarshad\"> Avatar smart/icons</a>\n" +
                    "        Changes made:\n" +
                    "        <ul>\n" +
                    "            <li> Changed color of background</li>\n" +
                    "            <li> Added circle around icon</li>\n" +
                    "        </ul>\n" +
                    "        Licensed under <a href=\"https://creativecommons.org/licenses/by/3.0/\">CC BY 3.0</a>\n" +
                    "    </string>\n" + "</body></html>"
        val weatherIcon =
            "<html><body>         <a href=\"https://www.iconfinder.com/iconsets/tiny-weather-1\" >The Tiny Weather set icon set</a> by <a href=\"https://www.iconfinder.com/Spot\"> Paolo Valzania</a>\n" +
                    "        <br></br>\n" +
                    "        Licensed under <a href=\"https://creativecommons.org/licenses/by/3.0/\">CC BY 3.0</a></body></html>"
        plantIconCredit.loadDataWithBaseURL(null, plantIcon, "text/html", "utf-8", null)
        weatherIconCredit.loadDataWithBaseURL(null, weatherIcon, "text/html", "utf-8", null)

    }


}
