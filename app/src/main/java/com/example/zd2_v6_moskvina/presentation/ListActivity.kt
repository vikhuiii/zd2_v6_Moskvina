package com.example.zd2_v6_moskvina.presentation

import android.os.Bundle
import android.util.Log // <-- ДОБАВЛЕН ИМПОРТ
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ListScreen(onBack = { finish() }) }
    }
}

@Composable
fun ListScreen(onBack: () -> Unit) {
    val ctx = LocalContext.current
    Log.d("ROOM_DEBUG", "📱 ListScreen запущен. Ищу DAO...")
    val dao = (ctx.applicationContext as RelaxApp).dao
    Log.d("ROOM_DEBUG", "✅ DAO найден. Начинаю чтение из базы...")

    val entries by dao.getAllEntries().collectAsState(initial = emptyList())

    // Этот блок сработает каждый раз, когда список записей изменится
    LaunchedEffect(entries) {
        Log.d("ROOM_DEBUG", "📥 ПОЛУЧЕНО ЗАПИСЕЙ ИЗ БАЗЫ: ${entries.size}")
        entries.forEach { entry ->
            Log.d("ROOM_DEBUG", "   -> Запись: Настроение='${entry.mood}', Стресс=${entry.stressLevel}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0FA))
            .padding(16.dp)
    ) {
        Text("Мои записи", fontSize = 18.sp, color = Color(0xFF7B6FA3))
        Spacer(Modifier.height(16.dp))

        if (entries.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Нет записей. Добавьте первую!", color = Color.Gray)
            }
        } else {
            ScalingLazyColumn(modifier = Modifier.weight(1f)) {
                items(entries.size) { index ->
                    val entry = entries[index]
                    EntryCard(entry) {
                        CoroutineScope(Dispatchers.IO).launch {
                            dao.deleteEntry(entry)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Назад")
        }
    }
}

@Composable
fun EntryCard(entry: RelaxEntry, onDelete: () -> Unit) {
    val dateStr = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(entry.date))

    Card(
        onClick = onDelete,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Настроение: ${entry.mood}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Стресс: ${entry.stressLevel}/10", fontSize = 10.sp)
            Text(dateStr, fontSize = 9.sp, color = Color.LightGray)
        }
    }
}