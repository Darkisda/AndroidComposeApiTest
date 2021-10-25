package com.example.composeapitest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapitest.ui.theme.ComposeApiTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeApiTestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Conversation(messages = SampleData().conversationSample())
                }
            }
        }
    }
}

data class Props(val firstName: String, val lastName: String)

class SampleData {
    fun conversationSample(): MutableList<Props> {
        val list: MutableList<Props> = mutableListOf()

        list.add(Props("Luan", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus leo in varius blandit."))
        list.add(Props("Luan 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit"))
        list.add(Props("Luan", "Quisque ac euismod nunc, et molestie nunc. Curabitur aliquet mi nulla, varius semper urna tempor et. Nullam sed elementum felis."))

        return list
    }
}

@Composable
fun MessageCard(props: Props) {
    Row {
        Image(
            painterResource(id = R.drawable.pp),
            "${props.firstName}",
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                "${props.firstName}",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.width(4.dp))

            androidx.compose.material.Surface(
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    "${props.lastName}",
                    modifier = Modifier.padding(all = 4.2.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Props>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(props = message)
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    ComposeApiTestTheme {
        MessageCard(Props("Luan", "de Souza"))
    }
}