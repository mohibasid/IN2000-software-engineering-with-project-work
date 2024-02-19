package com.example.mohibas_oblig2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.mohibas_oblig2.ui.AlpacaScreen
import com.example.mohibas_oblig2.ui.theme.Mohibas_oblig2Theme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mohibas_oblig2Theme {
                    AlpacaScreen()
                }
            }
        }
    }

