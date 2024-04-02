package com.example.coroutinesfirebase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.coroutinesfirebase.ui.theme.CoroutinesFIrebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    var card by mutableStateOf<Card>(
        Card(
            power = 4,
            res = 6,
            cost = "4+U+U",
            name = "Dragón del archivo",
            type = "Criatura",
            subtype = "Hechicero dragón",
            description = "Vuela.\n\nRebatir *>2*\n\n(Siempre que esta criatura sea objetivo de un hechizo o habilidad que controla un oponente, contrarréstalo a menos que ese jugador pague *>2*.)\n\nCuando el Dragón del archivo entre al campo de batalla, adivina 2.",
            flavor = "\"Fascinante. Aquí dice que los dragones no saben leer.\"",
            color = "U"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cardsCollection = Firebase.firestore
            .collection("cards")

        val sarkhanCard = cardsCollection.document("CCH001")
        val gliderCard = cardsCollection.document("RE001")
        val prodigyCard = cardsCollection.document("CHH")
        val trenchCard = cardsCollection.document("E001")

        setContent {
            CoroutinesFIrebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                card = card
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                // Retrieve both cards with coroutines
                                lifecycleScope.launch {
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Retrieving Sarkhan...")
                                    val newCard = sarkhanCard.get().await().toObject(Card::class.java)
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Sarkhan retrieved!")
                                    delay(5000L)
                                    withContext(Dispatchers.Main) {
                                        card = newCard!!.format()
                                    }
                                }

                                lifecycleScope.launch {
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Retrieving Glider...")
                                    val newCard = gliderCard.get().await().toObject(Card::class.java)
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Glider retrieved!")
                                    delay(10000L)
                                    withContext(Dispatchers.Main) {
                                        card = newCard!!.format()
                                    }
                                }

                                lifecycleScope.launch {
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Retrieving Prodigy...")
                                    val newCard = prodigyCard.get().await().toObject(Card::class.java)
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Prodigy retrieved!")
                                    delay(15000L)
                                    withContext(Dispatchers.Main) {
                                        card = newCard!!.format()
                                    }
                                }
                                lifecycleScope.launch {
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Retrieving Trench...")
                                    val newCard = trenchCard.get().await().toObject(Card::class.java)
                                    Log.d("MainActivity", "[${Thread.currentThread().name}] Trench retrieved!")
                                    delay(20000L)
                                    withContext(Dispatchers.Main) {
                                        card = newCard!!.format()
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = "Retrieve cards from Database"
                            )
                        }
                    }
                }
            }
        }
    }
}
