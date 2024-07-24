package com.example.tvappcompose.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.All.AllScreen
import com.example.tvappcompose.screens.All.AllScreenViewModel
import com.example.tvappcompose.screens.Dashboard.DashboardScreen
import com.example.tvappcompose.screens.detail.DetailScreen
import com.example.tvappcompose.screens.player.PlayerScreen
import com.example.tvappcompose.screens.recent.RecentScreen


@Preview
@Composable
fun App(){
  val flag=false;

  val showRowViewModel:AllScreenViewModel = hiltViewModel()

  if (flag)
   insertInitialdata(showRowViewModel)

  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "DashboardScreen") {
    composable(
      route = "DashboardScreen"
    ){
      DashboardScreen(
        openDetailScreen = {
          navController.navigate("DetailScreen/${it}"){
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }

    composable(
      route = "DetailScreen/{showId}",
      arguments = listOf(
        navArgument("showId") {
          type = NavType.IntType
        }
      )
    ){
      DetailScreen(
        openPlayer = {
          Log.d("MYAPP", "Details: OpenPlayer")
          navController.navigate("PlayerScreen/${it}"){
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }

    composable(
      route = "PlayerScreen/{showId}",
      arguments = listOf(
        navArgument("showId") {
          type = NavType.IntType
        }
      )
    ){
      PlayerScreen()
    }
  }
}

fun insertInitialdata(showRowViewModel:AllScreenViewModel) {
  //first row
  val listShow1 = listOf(
    Show(
      "Big Buck Bunny",
      "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.",
      "Romance",
      2020,
      "1h 30m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
      "Top Picks"
    ),
    Show(
      "Elephant Dream",
      "The first Blender Open Movie from 2006",
      "Romance",
      2021,
      "1h 35m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
      "Top Picks"
    ),
    Show(
      "For Bigger Blazes",
      "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For \$35.\\nLearn how to use Chromecast with HBO GO and more at google.com/chromecast.",
      "Action",
      2005,
      "1h 40m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
      "Top Picks"
    ),
    Show(
      "For Bigger Escape",
      "TIntroducing Chromecast. The easiest way to enjoy online video and music on your TV—for when Batman's escapes aren't quite big enough. For \$35. Learn how to use Chromecast with Google Play Movies and more at google.com/chromecast.",
      "Thriller",
      2023,
      "2h 30m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
      "Top Picks"
    ),
    Show(
      "For Bigger Fun",
      "Introducing Chromecast. The easiest way to enjoy online video and music on your TV. For \$35.  Find out more at google.com/chromecast",
      "Horror",
      2018,
      "1h 23m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
      "Top Picks"
    )
  )
  //second row
  val listShow2 = listOf(
    Show(
      "For Bigger Joyrides",
      "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for the times that call for bigger joyrides. For $35. Learn how to use Chromecast with YouTube and more at google.com/chromecast.",
      "Drama",
      2010,
      "1h 23m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
      "Trending"
    ),
    Show(
      "For Bigger Meltdowns",
      "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when you want to make Buster's big meltdowns even bigger. For $35. Learn how to use Chromecast with Netflix and more at google.com/chromecast.",
      "Sci-Fi",
      2014,
      "2h 35m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
      "Trending"
    ),
    Show(
      "Sintel",
      "Sintel is an independently produced short film, initiated by the Blender Foundation as a means to further improve and validate the free/open source 3D creation suite Blender. With initial funding provided by 1000s of donations via the internet community, it has again proven to be a viable development model for both open 3D technology as for independent animation film.\nThis 15 minute film has been realized in the studio of the Amsterdam Blender Institute, by an international team of artists and developers. In addition to that, several crucial technical and creative targets have been realized online, by developers and artists and teams all over the world.\nwww.sintel.org",
      "Thriller",
      2016,
      "1h 27m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
      "Trending"
    ),
    Show(
      "Subaru Outback On Street And Dirt",
      "Smoking Tire takes the all-new Subaru Outback to the highest point we can find in hopes our customer-appreciation Balloon Launch will get some free T-shirts into the hands of our viewers.",
      "Horror",
      2020,
      "1h 45m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg",
      "Trending"
    ),
    Show(
      "Tears of Steel",
      "Tears of Steel was realized with crowd-funding by users of the open source 3D creation tool Blender. Target was to improve and test a complete open and free pipeline for visual effects in film - and to make a compelling sci-fi film in Amsterdam, the Netherlands.  The film itself, and all raw material used for making it, have been released under the Creatieve Commons 3.0 Attribution license. Visit the tearsofsteel.org website to find out more about this, or to purchase the 4-DVD box with a lot of extras.  (CC) Blender Foundation - http://www.tearsofsteel.org",
      "Romance",
      2022,
      "2h 30m",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
      "Trending"

    )
  )

  for (show in listShow1){
    showRowViewModel.insert(show)
  }
  for (show in listShow2){
    showRowViewModel.insert(show)
  }

}