package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchWeatherData("ranchi")
        searchCity()
    }

    private fun searchCity() {
        val searchview=binding.searchView
        searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(city:String) {
        val retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response=retrofit.getWeatherData(city,"4f3845254f91a2085ba3817543f1fadd", "metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody=response.body()
                if(response.isSuccessful && responseBody != null){
                    val temperature=responseBody.main.temp.toString()
                    val humidity=responseBody.main.humidity.toString()
                    val windSpeed=responseBody.wind.speed.toString()
                    val sunrise=responseBody.sys.sunrise.toLong()
                    val sunset=responseBody.sys.sunset.toLong()
                    val sea=responseBody.main.pressure.toString()
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxTemp=responseBody.main.temp_max.toString()
                    val mintemp=responseBody.main.temp_min.toString()

//                    Log.d("TAG", "onResponse: $temperature")
                    binding.temperature.text="$temperature °C"
                    binding.sea.text="$sea hPa"
                    binding.condition.text="$condition"
                    binding.weather.text="$condition"
                    binding.maxTemp.text="Max Temp: $maxTemp °C"
                    binding.minTemp.text="Min temp: $mintemp °C"
                    binding.sunset.text="${time(sunset)}"
                    binding.humidity.text="$humidity %"
                    binding.wind.text="$windSpeed m/s"
                    binding.sunrise.text="${time(sunrise)}"
                    binding.day.text=dayName(System.currentTimeMillis())
                    binding.date.text=date() 
                    binding.cityName.text="$city"

                    changeConditionImage(condition)

                }

            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun changeConditionImage(condition:String) {
        when(condition){
            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView6.setAnimation(R.raw.sun)
            }
            "Partly Clouds","Clouds","Overcast","Mist","Foggy","Haze"->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView6.setAnimation(R.raw.cloud)
            }
            "Light Rain","Drizzle","Moderate rain","Showers","Heavy Rain","Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView6.setAnimation(R.raw.rain)
            }
            "Light Snow","moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView6.setAnimation(R.raw.snow)
            }
            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView6.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView6.playAnimation()
    }

    private fun date(): String {
        val sdf=SimpleDateFormat("dd MMMM YYYY",Locale.getDefault())
        return sdf.format((Date()));
    }

    fun dayName(timeStamp: Long) : String{
        val sdf=SimpleDateFormat("EEEE",Locale.getDefault())
        return sdf.format((Date()));
    }
    fun time(timeStamp: Long) : String{
        val sdf=SimpleDateFormat("HH:mm",Locale.getDefault())
        return sdf.format((Date(timeStamp*1000)));
    }
}