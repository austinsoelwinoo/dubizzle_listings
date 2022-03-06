package com.dubizzle.listings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dubizzle.core.data.ListingsRemoteRepository
import com.dubizzle.core.domain.Listing
import com.dubizzle.core.interactors.GetListings
import com.dubizzle.listings.framework.Interactors
import com.dubizzle.listings.presentation.list.DListViewModel
import com.dubizzle.listings.util.observeForTesting
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn


class DListViewModelTest {
    private lateinit var listViewModel: DListViewModel
    @MockK
    private lateinit var getListings: GetListings

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxUnitFun = true)
        listViewModel = DListViewModel(Interactors(getListings = getListings))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load documents should update live data`() = runTest {
        val moviesList = listOf(Listing("", "","Bike","", arrayListOf(), arrayListOf(), arrayListOf()), Listing("", "","Notebook","", arrayListOf(), arrayListOf(), arrayListOf()))
        coEvery { getListings.invoke() } returns moviesList
        listViewModel.loadDocuments()
        listViewModel.listings.observeForTesting {
            assertEquals(null, listViewModel.listings.value?.size )
            yield()
            assertEquals(2, listViewModel.listings.value?.size )
        }
    }
}

