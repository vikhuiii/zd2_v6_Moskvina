package com.example.zd2_v6_moskvina.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.google.gson.Gson

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MenuScreen() }
    }
}

@Composable
fun MenuScreen() {
    val ctx = LocalContext.current
    val prefs = ctx.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userName = prefs.getString("user", null)?.let {
        Gson().fromJson(it, User::class.java).name
    } ?: "Гость"

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Привет, $userName! ", fontSize = 18.sp, color = Color(0xFF7B6FA3))
            Spacer(Modifier.height(24.dp))
        }
        item {
            MenuButton("", "Дневник") {
                ctx.startActivity(Intent(ctx, DataEntryActivity::class.java))
            }
        }
        item {
            MenuButton("", "Мои записи") {
                ctx.startActivity(Intent(ctx, ListActivity::class.java))
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    prefs.edit().clear().apply()
                    ctx.startActivity(Intent(ctx, MainActivity::class.java))
                    (ctx as? Activity)?.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Выйти") }
        }
    }
}

@Composable
fun MenuButton(icon: String, label: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 28.sp)
            Spacer(Modifier.width(16.dp))
            Text(label, fontSize = 16.sp)
        }
    }
}