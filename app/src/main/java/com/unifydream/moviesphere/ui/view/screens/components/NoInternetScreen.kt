package com.unifydream.moviesphere.ui.view.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.unifydream.moviesphere.R
import com.unifydream.moviesphere.utlis.requestFocus

@Composable
 fun NoInternetScreen() {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .zIndex(1f)
                .requestFocus(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.no_intrenet),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),

                )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Whoops!!",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "No Internet connection was found.\nCheck your connection or try again.",
                fontSize = 35.sp,
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}



