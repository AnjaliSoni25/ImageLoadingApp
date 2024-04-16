# ImageLoadingApp


**Key Features:**
1.Jetpack compose
2. MVVM
3. Retrofit
4. Hilt
5. Clean Architecture
6. Live Data



**Android Studio:** 
Android Studio Jellyfish | 2023.3.1 Canary 12
Build #AI-233.14475.28.2331.11514062, built on February 29, 2024
Runtime version: 17.0.10+0--11446219 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.

Kotlin version : 1.9.0



**Code:**

Image Grid View with pagination:

@Composable
fun ImageGrid(images: List<Bitmap>, isLoading: Boolean, onLoadMore: () -> Unit) {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(2)
    ) {
        items(images.size) { index ->
            val bitmap = images[index]

            // Check if the bitmap is not null and not in the loading state
            if (bitmap != null && !isLoading) {
                Image(
                    painter = rememberImagePainter(bitmap),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(1f)
                        .size(300.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Show a placeholder image or loader until the actual image is loaded
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(1f)
                        .size(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    }
                }
                }

            // Load more when reaching the end of the list
            if (index == images.size - 1) {
                onLoadMore()
            }
        }
    }
}


 ImageLodingAppTheme {
                val images by viewModel.images.observeAsState(initial = emptyList())
                 val isLoading by viewModel.isLoading.observeAsState(false)
                ImageGrid(
                    images = images,
                    isLoading = isLoading,
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }



**ScreenShot** :

![Screenshot_2024-04-16-11-24-05-840_com example imagelodingapp 1](https://github.com/AnjaliSoni25/ImageLoadingApp/assets/31882434/e26c1561-dc33-4fdd-a74b-7bd67d61d9ac)
