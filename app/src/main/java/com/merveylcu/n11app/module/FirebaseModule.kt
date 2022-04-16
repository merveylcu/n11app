package com.merveylcu.n11app.module

import com.google.firebase.database.FirebaseDatabase
import com.merveylcu.n11app.core.Constants
import org.koin.dsl.module

val firebaseDatabase = module {
    single { FirebaseDatabase.getInstance() }
    single { get<FirebaseDatabase>().getReference(Constants.Database.userTable) }
}