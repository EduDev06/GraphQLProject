package com.example.graphqlproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.graphqlproject.domain.model.User
import com.example.graphqlproject.ui.theme.GraphQlProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()
        setContent {
            GraphQlProjectTheme {
                val userState by viewModel.userState.collectAsStateWithLifecycle()
                Content(
                    userState = userState,
                    fetchUser = viewModel::fetchUser
                )
            }
        }
    }
}

@Composable
fun Content(
    userState: UserState,
    fetchUser: (String) -> Unit
) {
    var idText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = idText,
            onValueChange = { idText = it },
            trailingIcon = {
                IconButton(onClick = { fetchUser(idText) } ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(Modifier.height(10.dp))

        if (userState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        else
            userState.user?.let { user ->
                UserCard(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    user = user
                )
            }
    }
}

@Composable
fun UserCard(
    modifier: Modifier,
    user: User
) {
    Card(
        modifier = modifier.height(120.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.bannerImage)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(6.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar.medium)
                        .build(),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.align(Alignment.Bottom),
                    text = user.name,
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}



