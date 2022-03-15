package com.dubizzle.listings

import androidx.lifecycle.SavedStateHandle
import com.dubizzle.listings.core.interactors.GetListings
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.presentation.list.DListViewModel
import com.dubizzle.listings.util.CoroutinesTestRule
import com.dubizzle.listings.util.generateListings
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest

class DListViewModelTest : KoinTest {

    @MockK
    private lateinit var getListings: GetListings

    val listViewModel: DListViewModel by inject(DListViewModel::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun startKoinForTest() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        startKoin {
            modules(module {
                single { getListings }
                single { Interactors(get()) }
                single { SavedStateHandle() }
                viewModel { DListViewModel(get(), get()) }
            })
        }
    }

    @Test
    fun `load documents should update listings data`() {
        val listings = generateListings()
        coEvery { getListings.invoke() } returns listings
        assertEquals(0, listViewModel.listings.size)
        listViewModel.loadDocuments()
        assertEquals(listings.size,listViewModel.listings.size)
    }
}

