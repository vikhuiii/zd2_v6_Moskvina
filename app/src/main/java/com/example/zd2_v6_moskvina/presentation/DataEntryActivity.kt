package com.example.zd2_v6_moskvina.presentation

import android.os.Bundle
import android.util.Log // <-- ДОБАВЛЕН ИМПОРТ ДЛЯ ЛОГОВ
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DataEntryScreen(onBack = { finish() }) }
    }
}

@Composable
fun DataEntryScreen(onBack: () -> Unit) {
    val ctx = LocalContext.current
    val dao = (ctx.applicationContext as RelaxApp).dao
    var mood by remember { mutableStateOf("") }
    var stress by remember { mutableStateOf("5") }
    var msg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0FA))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Дневник настроения", fontSize = 18.sp, color = Color(0xFF7B6FA3))
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = mood,
            onValueChange = { newValue -> mood = newValue; msg = "" },
            label = { Text("Настроение") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = stress,
            onValueChange = { newValue -> stress = newValue; msg = "" },
            label = { Text("Уровень стресса (1–10)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        if (msg.isNotEmpty()) {
            Text(msg, color = if (msg.contains("Заполните")) Color.Red else Color(0xFF4CAF50), fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (mood.isBlank()) {
                    msg = "Заполните настроение"
                } else {
                    val moodToSave = mood
                    val stressToSave = stress.toIntOrNull() ?: 5

                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insertEntry(
                            RelaxEntry(
                                mood = moodToSave,
                                stressLevel = stressToSave
                            )
                        )
                    }

                    msg = "Запись сохранена!"
                    mood = ""
                    stress = "5"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA8D5BA))
        ) { Text("Сохранить", color = Color.Black) }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Назад")
        }
    }
}