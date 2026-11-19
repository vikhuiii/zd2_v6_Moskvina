/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.zd2_v6_moskvina.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen(onLogin = {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            })
        }
    }
}

@Composable
fun WelcomeScreen(onLogin: () -> Unit) {
    val ctx = LocalContext.current
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var err by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val json = ctx.getSharedPreferences("user_prefs", Context.MODE_PRIVATE).getString("user", null)
        if (json != null) onLogin()
    }

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Развитие и релакс", fontSize = 20.sp, color = Color(0xFF7B6FA3))
            Spacer(Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = name,
                onValueChange = { newValue -> name = newValue; err = "" },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = age,
                onValueChange = { newValue -> age = newValue; err = "" },
                label = { Text("Возраст") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            if (err.isNotEmpty()) {
                Text(err, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.height(16.dp))
        }
        item {
            Button(
                onClick = {
                    if (name.isBlank() || age.isBlank() ) {
                        err = "Заполните все поля!"
                    } else {
                        val user = User(name, age.toIntOrNull() ?: 0)
                        val json = Gson().toJson(user)
                        ctx.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            .edit().putString("user", json).apply()
                        onLogin()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C8FBF))
            ) { Text("Войти", color = Color.White) }
        }
    }
}