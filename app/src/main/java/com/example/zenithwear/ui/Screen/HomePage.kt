package com.example.zenithwear.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zenithwear.R
import com.example.zenithwear.data.Model.MenuModel
import com.example.zenithwear.data.Model.ModelProduct
import com.example.zenithwear.data.Model.PostCardModel
import com.example.zenithwear.data.Model.PostCardModel2
import com.example.zenithwear.data.Model.Talla
import com.example.zenithwear.ui.Component.CardCategorias
import com.example.zenithwear.ui.Component.CardMarca
import com.example.zenithwear.ui.Component.CardProduct
import com.example.zenithwear.ui.Component.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePage (navHostController: NavHostController, cartViewModel: CartViewModel) {
    var option by rememberSaveable { mutableStateOf("Buttons") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expandedItemId by remember { mutableStateOf<Int?>(null) }
    var showSearchBar by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val MenuOptions = arrayOf(
        MenuModel(1, "Home", "HomePage", "",ImageVector.vectorResource(id = R.drawable.icono2)),
        MenuModel(2, "IA", "IA", "", Icons.Filled.ArrowDropDown, listOf(
        MenuModel(3, " Dress for the occasion", "IA", "", Icons.Filled.ArrowForward),
        MenuModel(4, "Clothing according to the weather", "IA", "", Icons.Filled.ArrowForward),
        MenuModel(5, "Other", "IA", "", Icons.Filled.ArrowForward) ) ),
        MenuModel(6, "Products", "Products", "", Icons.Filled.ArrowDropDown, listOf(
        MenuModel(7, "Men's", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(8, "Women's", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(9, "kids", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(10, "Collections", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(11, "Footwear", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(12, "Accessories", "Products", "", Icons.Filled.KeyboardArrowRight),
        MenuModel(13, "Sportwear", "Products", "", Icons.Filled.KeyboardArrowRight)) ),
        MenuModel(14, "Brands", "Products", "", Icons.Filled.Add, listOf(
        MenuModel(15, "Adidas", "Products", "", ImageVector.vectorResource(id = R.drawable.adidass)),
        MenuModel(16, "Nike", "Products", "", ImageVector.vectorResource(id = R.drawable.nike2)),
        MenuModel(17, "Under Armour", "Products","", ImageVector.vectorResource(id = R.drawable.under_armour_logo_10)))),


    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                LazyColumn {
                    items(MenuOptions) { item ->
                        // Opción principal
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp))
                            },
                            label = { Text(item.title) },
                            selected = false,
                            onClick = {
                                if (item.subItems.isNotEmpty()) {
                                    // Si la opción ya está expandida, la contraemos
                                    if (expandedItemId == item.id) {
                                        expandedItemId = null
                                    } else {
                                        // Expandimos la opción seleccionada y cerramos las demás
                                        expandedItemId = item.id
                                    }
                                } else {
                                    // Si no tiene subopciones, navega a la ruta
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navHostController.navigate(item.option)
                                }
                            }
                        )
                        // Subopciones (si la opción está expandida)
                        if (expandedItemId == item.id) {
                            item.subItems.forEach { subItem ->
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            imageVector = subItem.icon,
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp))
                                    },
                                    label = { Text(subItem.title, modifier = Modifier.padding(start = 32.dp)) },
                                    selected = false,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        navHostController.navigate(subItem.option)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = { Bars(navHostController) },
            bottomBar = { Bars2(navHostController = navHostController, cartViewModel = cartViewModel) }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Text(
                        text = "Shop by category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(15.dp)
                    )
                }
                item { categorias() }
                item {
                    Text(
                        text = "Brands",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(15.dp)
                    )
                }
                item { marca() }

            item {
                Text(
                    text = "New Arrivals",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(15.dp)
                )
            }
                item{NewArrivals()}
            }
        }
    }
}
@Composable
fun categorias() {
    val arraydefault = arrayOf(
        PostCardModel(1, "Men's", R.drawable.ropah),
        PostCardModel(2, "Women's", R.drawable.ropam),
        PostCardModel(3, "Kids", R.drawable.ropak),
        PostCardModel(4, "Footwear", R.drawable.footwear),
        PostCardModel(5, "Accessories", R.drawable.accessories),
        PostCardModel(6, "Sportwear", R.drawable.sportwear),
        PostCardModel(7, "Collections", R.drawable.collections)
    )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(arraydefault) { item ->
            CardCategorias(item.id, item.title, item.image)
        }
    }
}

@Composable
fun marca() {
    val arraydefault = arrayOf(
        PostCardModel2(1, "Nike", R.drawable.nike, R.drawable.nike2),
        PostCardModel2(2, "Adidas", R.drawable.adidas, R.drawable.adidass),
        PostCardModel2(3, "Under Armour", R.drawable.under, R.drawable.under_armour_logo_10)
    )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(arraydefault) { item ->
            CardMarca(item.id, item.title, item.image, item.icon)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bars(navHostController: NavHostController) {
    var showSearchBar by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.secondary
            ),
            title = {
                if (showSearchBar) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        placeholder = { Text("Buscar...") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                navHostController.navigate("Search")
                                showSearchBar = false
                            }
                        )
                    )
                } else {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.icono),
                            contentDescription = "Logo de la app",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 25.dp)
                        )
                        Text("ZenithWear", fontSize = 18.sp)
                    }
                }
            },
            actions = {
                if (showSearchBar) {

                    IconButton(onClick = { showSearchBar = false }) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar búsqueda", tint = Color.White)
                    }
                } else {

                    IconButton(onClick = { showSearchBar = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = Color.White)
                    }
                }
            }
        )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bars2(navHostController: NavHostController, cartViewModel: CartViewModel) {
    val cartItemCount = cartViewModel.cartProducts.size // Obtén la cantidad de productos en el carrito

    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Gray
    ) {
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { navHostController.navigate("HomePage") }
        ) {
            Icon(Icons.Filled.Home, contentDescription = "", tint = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { navHostController.navigate("Favorite") }
        ) {
            Icon(Icons.Filled.FavoriteBorder, contentDescription = "", tint = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { navHostController.navigate("Cart") }
        ) {
            BadgedBox(
                badge = {
                    if (cartItemCount > 0) {
                        Badge {
                            Text(text = cartItemCount.toString())
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "",
                    tint = Color.Gray
                )
            }
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { navHostController.navigate("Notification") }
        ) {
            Icon(Icons.Filled.Notifications, contentDescription = "", tint = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { navHostController.navigate("Profile") }
        ) {
            Icon(Icons.Filled.Person, contentDescription = "", tint = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}
@Composable
fun NewArrivals(){
    val arraydefault = arrayOf(
        ModelProduct(1, "Samba OG W",2299, R.drawable.samba,"","","", Talla.M, 1),
        ModelProduct(2, "Women's",2199, R.drawable.chamarrash,"","","", Talla.S, 2),
        ModelProduct(3, "Nike Blazer",2499, R.drawable.blazer,"","","", Talla.M, 3),
        ModelProduct(4, "Mochila Nike",1299, R.drawable.mochila,"","","", Talla.M, 4),
        ModelProduct(5, "Curry 12 PSCS",3999, R.drawable.curry,"","","", Talla.M, 5),
        ModelProduct(6, "Sudadera con capucha",1099, R.drawable.sudadera,"","","",Talla.M,5),
        )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(arraydefault) { item ->
            CardProduct(item.id, item.title, item.precio,item.image)
        }
    }

}