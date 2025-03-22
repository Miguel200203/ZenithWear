package com.example.zenithwear.ui.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardCategorias(id:Int,title:String, image:Int){
    Card (
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
            modifier = Modifier
                .background(Color.White)
                .width(135.dp)
                .height(155.dp),
            painter = painterResource(image),
            contentDescription = "",
            contentScale = ContentScale.Fit
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun CardMarca(id:Int,title:String, image:Int, icon: Int){
    Card (
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .background(Color.White)
                    .width(360.dp)
                    .height(290.dp),
                painter = painterResource(image),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically


                    )
            {

                Text(
                    text = title,
                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(13.dp)

                )

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(35.dp)


                )

            }
        }
    }


}
@Composable
fun CardProduct(id:Int, title:String, precio:Number,image: Int){
    Card (
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(290.dp)
            .padding(5.dp)
    ) {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .width(160.dp)
                .padding(5.dp)
        ){
            Image(
                modifier = Modifier
                    .background(Color.White)
                    .width(155.dp)
                    .height(165.dp),
                painter = painterResource(image),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Start)
            )
            Row (
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(
                    text = "$$precio",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 20.dp)
                )

                IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {}
                ) {
                    Icon(
                        Icons.Filled.ShoppingCart, contentDescription = "", tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )

                }
            }
        }
    }
}
