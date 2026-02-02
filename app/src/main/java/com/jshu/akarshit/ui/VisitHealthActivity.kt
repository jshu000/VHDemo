@file:OptIn(ExperimentalGlideComposeApi::class)

package com.jshu.akarshit.ui

import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jshu.akarshit.R
import com.jshu.akarshit.ui.theme.AkarshitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VisitHealthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AkarshitTheme {
                Scaffold { contentPadding ->
                    AppNavGraph(
                        modifier = Modifier.padding(contentPadding)
                    )
                }
            }
        }
    }
}
@Composable
//@Preview(showBackground = true)
fun ImageTopEndToCenter(
    animate: Boolean = false,
    onAnimate: () -> Unit = {},
    nextPageImage: Int?,
    pageIndex: Int,
    currentImage: Int,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val imageSizeStart = 40.dp
    val imageSizeEnd = screenWidth

    var showNextImage by remember { mutableStateOf(false) }


    var animate by remember(pageIndex) { mutableStateOf(false) }

    val imageSize by animateDpAsState(
        targetValue = if (animate) imageSizeEnd else imageSizeStart,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "imageSize"
    )

    LaunchedEffect(pageIndex) {
        animate = false
        delay(50) // tiny delay so Compose sees a state change
        animate = true
    }
    LaunchedEffect(nextPageImage) {
        showNextImage = false
        if (nextPageImage != null) {
            delay(100)
            showNextImage = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        currentImage?.let { imageRes ->
            Image(
                painter = painterResource(currentImage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(imageSize)
            )
        }
        if (showNextImage && nextPageImage != null) {
            Image(
                painter = painterResource(nextPageImage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(40.dp)
            )
        }

    }
}






@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING,
        modifier = modifier
    ) {

        composable(Routes.ONBOARDING) {
            StepAthonOnboarding(
                modifier = Modifier.fillMaxSize(),
                onFinish = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("Home Screen 🚀")
    }
}
object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
}

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String,
    val gradientColors: List<Color>
)

val PurpleGradient = listOf(Color(0xFF5335D3), Color(0xFF3A2CA0))
val BlueGradient = listOf(Color(0xFF42DEC5), Color(0xFF3484DE))
val RedGradient = listOf(Color(0xFFFF754C), Color(0xFFE31E71))

val onboardingPages = listOf(
    OnboardingPage(
        image = R.drawable.group_48097011__1_,
        title = "Step Up and Score",
        description = "Join the Race, Lace Up, and Embrace Health!",
        gradientColors = PurpleGradient
    ),
    OnboardingPage(
        image = R.drawable.group_48097014__1_,
        title = "Claim the Throne",
        description = "Compete with your colleagues for Top Ranks",
        gradientColors = BlueGradient
    ),
    OnboardingPage(
        image = R.drawable.group_48097013__2_,
        title = "Score Big!!",
        description = "Victory Unlocks Spectacular Vouchers, Cashbacks, and Beyond",
        gradientColors = RedGradient
    )
)



@Composable
fun AnimatedPagerIndicator(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            val width by animateDpAsState(
                targetValue = if (index == currentPage) 24.dp else 8.dp,
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .height(8.dp)
                    .width(width)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (index == currentPage)
                            Color.White
                        else
                            Color.White.copy(alpha = 0.4f)
                    )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun StepAthonOnboarding(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit ={}
) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    BackHandler(enabled = currentPage > 0) {
        scope.launch {
            pagerState.animateScrollToPage(currentPage - 1)
        }
    }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    var playNextImageAnim by remember { mutableStateOf(false) }

    val scaleAnim = remember { Animatable(0.3f) }
    val offsetXAnim = remember { Animatable(0f) }
    val offsetYAnim = remember { Animatable(0f) }
    val alphaAnim = remember { Animatable(1f) }
    var nextIconOffset by remember { mutableStateOf(Offset.Zero) }
    var rootSize by remember { mutableStateOf(IntSize.Zero) }
    var targetImageOffset by remember { mutableStateOf(Offset.Zero) }
    var animate by remember { mutableStateOf(false) }




    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        // 🔝 Status bar (fixed height)

        Box(
            modifier = modifier
                .weight(1f)           // 🔥 THIS FIXES IT
                .fillMaxWidth()
                .onSizeChanged { rootSize = it }
                .background(
                    brush = Brush.linearGradient(
                        colors = onboardingPages[currentPage].gradientColors,
                        start = Offset.Zero, // top-left corner
                        end = Offset(
                            x = boxSize.width / 2f,
                            y = boxSize.height / 2f
                        )
                    )
                )
        ) {






            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatusBar(
                    showBack = currentPage > 0,
                    onBackClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage - 1)
                        }
                    }
                )

                Spacer(Modifier.height(24.dp))
                CurvedTitleText(
                    text = "Step-a-thon",
                    modifier = Modifier.padding(top = 16.dp)
                )



                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    val page = onboardingPages[page]
                    val currentPage = currentPage
                    val nextPageImage =
                        if (currentPage < onboardingPages.lastIndex)
                            onboardingPages[currentPage + 1].image
                        else
                            null



                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Box(modifier = Modifier.height(350.dp)
                            .fillMaxWidth()
                        ){

                            ImageTopEndToCenter(
                                animate,
                                nextPageImage = nextPageImage,
                                pageIndex = currentPage,
                                currentImage = onboardingPages[currentPage].image
                            )
                        }


                        

                        GlideImage(
                            model = R.drawable.star_gif,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(53.dp)
                        )
                        GlideImage(
                            model = R.drawable.bg_for_top_3__1_,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 150.dp)
                                .size(380.dp)

                        )

                        // ⭐ Right star
                        GlideImage(
                            model = R.drawable.star_gif,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 16.dp, bottom = 150.dp)
                                .size(53.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Spacer(
                                modifier = Modifier.weight(0.9f)
                            )

                            Text(
                                text = page.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600),
                                color = Color.White,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Center,
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = page.description,
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight(500),
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                            Spacer(Modifier.height(16.dp))

                        }
                    }

                }

                AnimatedPagerIndicator(
                    pageCount = onboardingPages.size,
                    currentPage = currentPage
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        animate = true
                        scope.launch {
                            if (currentPage == onboardingPages.lastIndex) {
                                onFinish()
                                return@launch
                            }
                            delay(400)
                            pagerState.scrollToPage(currentPage + 1)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .height(56.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = if (currentPage ==
                            onboardingPages.lastIndex) "Let’s Go" else "Next",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun CurvedTitleText(
    text: String,
    modifier: Modifier = Modifier,
    radius: Float = 420f,
    textSize: Float = 120f
) {
    Canvas (
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.WHITE
            this.textSize = textSize
            this.letterSpacing =0.1f
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD
            )
            textAlign = Paint.Align.CENTER
            setShadowLayer(
                /* radius = */ 20f,
                /* dx     = */ 0f,
                /* dy     = */ 4f,
                /* color  = */ android.graphics.Color.parseColor("#FFFFFF99")
            )
        }

        val path = Path().apply {
            addArc(
                /* left   = */ size.width / 2 - radius,
                /* top    = */ 0f,
                /* right  = */ size.width / 2 + radius,
                /* bottom = */ radius * 2,
                /* startAngle = */ 180f,
                /* sweepAngle = */ 180f
            )
        }

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawTextOnPath(
                text,
                path,
                0f,
                0f,
                paint
            )
        }
    }
}
@Composable
fun StatusBar(
    showBack: Boolean,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(56.dp)              // 👈 standard app bar height
            ,
        contentAlignment = Alignment.CenterStart
    ) {
        if (showBack) {
            IconButton(onClick = onBackClick,
                modifier = Modifier) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}