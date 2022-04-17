package com.merveylcu.n11app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.database.UserDatabase
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.module.roomTestModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * N11AppInstrumentedTest is a KoinTest with AndroidJUnit4 runner
 *
 * KoinTest help inject Koin components from actual runtime
 */
@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : KoinTest {

    /*
     * Inject needed components from Koin
     */
    private val userDatabase: UserDatabase by inject()
    private val userDao: UserDao by inject()

    /**
     * Override default Koin configuration to use Room in-memory database
     */
    @Before()
    fun before() {
        loadKoinModules(roomTestModule)
    }

    /**
     * Close resources
     */
    @After
    fun after() {
        userDatabase.close()
        stopKoin()
    }

    @Test
    fun test() {
        val users = arrayListOf<User>()
        val user = User(
            8466495,
            "merveylcu",
            "MDQ6VXNlcjg0NjY0OTU=",
            "https://avatars.githubusercontent.com/u/8466495?v=4",
            "",
            "https://api.github.com/users/merveylcu",
            "https://github.com/merveylcu",
            "https://api.github.com/users/merveylcu/followers",
            "https://api.github.com/users/merveylcu/following{/other_user}",
            "https://api.github.com/users/merveylcu/gists{/gist_id}",
            "https://api.github.com/users/merveylcu/starred{/owner}{/repo}",
            "https://api.github.com/users/merveylcu/subscriptions",
            "https://api.github.com/users/merveylcu/orgs",
            "https://api.github.com/users/merveylcu/repos",
            "https://api.github.com/users/merveylcu/events{/privacy}",
            "https://api.github.com/users/merveylcu/received_events",
            "User",
            false,
            1,
            true
        )
        users.add(user)
        userDao.addUsers(users)

        val isFavorite = userDao.getUserFavorite("merveylcu")

        // Compare result
        Assert.assertEquals(isFavorite, true)
    }
}