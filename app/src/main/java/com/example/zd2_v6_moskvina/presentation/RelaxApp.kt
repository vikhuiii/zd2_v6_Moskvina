package com.example.zd2_v6_moskvina.presentation

import android.app.Application

class RelaxApp : Application() {
    val dao: RelaxDao by lazy { RelaxDatabase.getDatabase(this).relaxDao() }
}